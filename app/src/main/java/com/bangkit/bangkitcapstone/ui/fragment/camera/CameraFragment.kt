package com.bangkit.bangkitcapstone.ui.fragment.camera

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.bangkit.bangkitcapstone.databinding.FragmentCameraBinding
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.graphics.*
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.bangkit.bangkitcapstone.helper.Helper.KEYPOINT_DICT
import com.bangkit.bangkitcapstone.ml.LiteModelMovenetSingleposeLightningTfliteFloat164
import com.bangkit.bangkitcapstone.model.data.ml.PoseClassifier
//import com.bangkit.bangkitcapstone.model.data.ml.KEYPOINT_DICT
//import com.bangkit.bangkitcapstone.model.data.ml.getKeypoints
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2

class CameraFragment : Fragment() {

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!
    private lateinit var textureView: TextureView
    private lateinit var cameraManager: CameraManager
    private lateinit var handler: Handler
    private lateinit var handlerThread: HandlerThread
    private lateinit var bitmap: Bitmap
    private lateinit var cameraImageView: ImageView
    private lateinit var model: LiteModelMovenetSingleposeLightningTfliteFloat164
    private lateinit var imageProcessor: ImageProcessor
    private val paint = Paint()
    private var isSurfaceTextureAvailable = false
    private var poseModel = "pose_model.tflite"
    private lateinit var classifier: PoseClassifier
    private lateinit var assetManager: AssetManager
    private var isUpSquat = false
    private var isDownSquat = false
    private var isUpPushup = false
    private var isDownPushup = false
    private var squatReps = 0
    private var pushupReps = 0
    private var pushupForm = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraImageView = binding.cameraImageView
        textureView = binding.texttureView
        cameraManager = ContextCompat.getSystemService(
            requireContext(),
            CameraManager::class.java
        ) as CameraManager
        handlerThread = HandlerThread("videoThread")
        handlerThread.start()
        handler = Handler(handlerThread.looper)

        model = LiteModelMovenetSingleposeLightningTfliteFloat164.newInstance(requireContext())
        imageProcessor =
            ImageProcessor.Builder().add(ResizeOp(192, 192, ResizeOp.ResizeMethod.BILINEAR)).build()
        paint.color = Color.GREEN

        getPermission()
        textureListener()

        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    @Deprecated("Deprecated in Java")
    @Suppress("DEPRECATION")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            openCamera()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        assetManager = context.assets
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        model.close()
    }

    @Suppress("DEPRECATION")
    private fun getPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
        } else {
            openCamera()
        }
    }

    @Suppress("DEPRECATION")
    @SuppressLint("MissingPermission")
    private fun openCamera() {
        if (isSurfaceTextureAvailable) {
            cameraManager.openCamera(
                cameraManager.cameraIdList[0],
                object : CameraDevice.StateCallback() {
                    override fun onOpened(camera: CameraDevice) {
                        val captureRequest =
                            camera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
                        val surface = Surface(textureView.surfaceTexture)
                        captureRequest.addTarget(surface)

                        camera.createCaptureSession(
                            listOf(surface),
                            object : CameraCaptureSession.StateCallback() {
                                override fun onConfigured(session: CameraCaptureSession) {
                                    session.setRepeatingRequest(captureRequest.build(), null, null)
                                }

                                override fun onConfigureFailed(session: CameraCaptureSession) {
                                }
                            },
                            handler
                        )
                    }

                    override fun onDisconnected(camera: CameraDevice) {
                    }

                    override fun onError(camera: CameraDevice, error: Int) {
                    }
                },
                handler
            )
        }
    }

    private fun textureListener() {
        textureView.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(
                surface: SurfaceTexture,
                width: Int,
                height: Int
            ) {
                isSurfaceTextureAvailable = true
                getPermission()
            }

            override fun onSurfaceTextureSizeChanged(
                surface: SurfaceTexture,
                width: Int,
                height: Int
            ) {
            }

            override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
                isSurfaceTextureAvailable = false
                return false
            }

            override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
                initClassifier()

                bitmap = textureView.bitmap!!
                var tensorImage = TensorImage(DataType.UINT8)
                tensorImage.load(bitmap)
                tensorImage = imageProcessor.process(tensorImage)

                val inputFeature0 =
                    TensorBuffer.createFixedSize(intArrayOf(1, 192, 192, 3), DataType.UINT8)
                inputFeature0.loadBuffer(tensorImage.buffer)

                val outputs = model.process(inputFeature0)
                val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray


                val keypointsArray = getKeypoints(outputFeature0)

                // Predict pose from the 1D array
                val result = classifier.predictPose(keypointsArray)

                // Calculate keypoints angles for squat
                if (result <= 1.0 && result > 0.95) {
                    val kneeAngle = kneeAngle(keypointsArray)

                    squatReps = upSquat(kneeAngle, squatReps)
                    downSquat(kneeAngle)
                }

                // Calculate keypoints angles for pushup
                else if (result in 0.0..5.0) {
                    val elbowAngle = armAngle(keypointsArray)
                    val backAngle = backAngle(keypointsArray)

                    // Check user in up position or down position and add reps if user is going up and down
                    pushupReps = upPushup(elbowAngle, pushupReps)
                    downPushup(elbowAngle)

                    // Check user's pushup form (0 if back position is bad, 1 if back position is good)
                    pushupForm = checkPushup(backAngle)
                }

                Log.e("result", "$result")
                Log.e("pushupReps", "$pushupReps")
                Log.e("pushupForm", "$pushupForm")

                val mutable = bitmap.copy(Bitmap.Config.ARGB_8888, true)
                val canvas = Canvas(mutable)
                val h = bitmap.height
                val w = bitmap.width
                var x = 0

                while (x <= 49) {
                    if (outputFeature0[x + 2] > 0.45) {
                        canvas.drawCircle(
                            outputFeature0[x + 1] * w,
                            outputFeature0[x] * h,
                            12f,
                            paint
                        )
                    }
                    x += 3
                }
                cameraImageView.setImageBitmap(mutable)
            }
        }
    }

    private fun initClassifier() {
        classifier = PoseClassifier(assetManager, poseModel)
    }

    private fun getKeypoints(keypointsArray: FloatArray): List<Float> {
        val keyList = mutableListOf<Float>()
        for (i in 0 until 50 step 3) {
            val x = keypointsArray[i + 1]
            val y = keypointsArray[i]
            keyList.add(x)
            keyList.add(y)
        }
        return keyList
    }

    private fun armAngle(keypoints: List<Float>): Float {
        // Input berupa list yang sudah dikonvert dengan fungsi getKeypoints()
        // Output berupa sudut dari sikut
        val leftWristIndex = KEYPOINT_DICT["left_wrist"]
        val leftShoulderIndex = KEYPOINT_DICT["left_shoulder"]
        val leftElbowIndex = KEYPOINT_DICT["left_elbow"]

        val at1 = atan2(
            keypoints[leftWristIndex!!.second] - keypoints[leftElbowIndex!!.second],
            keypoints[leftWristIndex.first] - keypoints[leftElbowIndex.first]
        )
        val at2 = atan2(
            keypoints[leftShoulderIndex!!.second] - keypoints[leftElbowIndex.second],
            keypoints[leftShoulderIndex.first] - keypoints[leftElbowIndex.first]
        )
        return (at1 - at2) * (180 / PI.toFloat())
    }

    // Fungsi buat ngitung kelurusan punggung (ngitung sudut pinggang) saat pushup
    private fun backAngle(keypoints: List<Float>): Float {
        // Input berupa list yang sudah dikonvert dengan fungsi getKeypoints()
        // Output berupa sudut dari pinggang
        val leftHipIndex = KEYPOINT_DICT["left_hip"]
        val leftShoulderIndex = KEYPOINT_DICT["left_shoulder"]
        val leftKneeIndex = KEYPOINT_DICT["left_knee"]

        val at1 = atan2(
            keypoints[leftKneeIndex!!.second] - keypoints[leftHipIndex!!.second],
            keypoints[leftKneeIndex.first] - keypoints[leftHipIndex.first]
        )
        val at2 = atan2(
            keypoints[leftShoulderIndex!!.second] - keypoints[leftHipIndex.second],
            keypoints[leftShoulderIndex.first] - keypoints[leftHipIndex.first]
        )
        return (at1 - at2) * (180 / PI.toFloat())
    }

// ------------------------------ Fungsi Cek Sudut Squat

    // Fungsi ini buat ngitung sudut lutut (squat)
    private fun kneeAngle(keypoints: List<Float>): Float {
        // Input berupa list yang sudah dikonvert dengan fungsi getKeypoints()
        // Output berupa sudut dari tekukan lutut
        val leftHipIndex = KEYPOINT_DICT["left_hip"]
        val leftAnkleIndex = KEYPOINT_DICT["left_ankle"]
        val leftKneeIndex = KEYPOINT_DICT["left_knee"]

        val at1 = atan2(
            keypoints[leftAnkleIndex!!.second] - keypoints[leftKneeIndex!!.second],
            keypoints[leftAnkleIndex.first] - keypoints[leftKneeIndex.first]
        )
        val at2 = atan2(
            keypoints[leftHipIndex!!.second] - keypoints[leftKneeIndex.second],
            keypoints[leftHipIndex.first] - keypoints[leftKneeIndex.first]
        )
        return (at1 - at2) * (180 / PI.toFloat())
    }

    // Fungsi ini buat ngukur sudut pinggang saat squat
    private fun hipAngle(keypoints: List<Float>): Float {
        // Input berupa list yang sudah dikonvert dengan fungsi getKeypoints()
        // Output berupa sudut dari pinggang
        val leftHipIndex = KEYPOINT_DICT["left_hip"]
        val leftShoulderIndex = KEYPOINT_DICT["left_shoulder"]
        val leftKneeIndex = KEYPOINT_DICT["left_knee"]

        val at1 = atan2(
            keypoints[leftKneeIndex!!.second] - keypoints[leftHipIndex!!.second],
            keypoints[leftKneeIndex.first] - keypoints[leftHipIndex.first]
        )
        val at2 = atan2(
            keypoints[leftShoulderIndex!!.second] - keypoints[leftHipIndex.second],
            keypoints[leftShoulderIndex.first] - keypoints[leftHipIndex.first]
        )
        return (at1 - at2) * (180 / PI.toFloat())
    }

// ------------------------------ Fungsi Cek Repetisi Pushup

    // Fungsi untuk mengecek apakah user pada posisi naik pushup
    private fun upPushup(elbowAngle: Float, repsVar: Int): Int {
        // Input berupa hasil hitungan dari armAngle()
        var reps = repsVar
        if (!((abs(elbowAngle) > 170) && (abs(elbowAngle) < 200))) {
            return reps
        }

        if (isDownPushup) {
            println("up")
            reps++
        }

        isUpPushup = true
        isDownPushup = false

        return reps
    }

    // Fungsi untuk mengecek apakah user sedang pada posisi bawah pushup
    private fun downPushup(elbowAngle: Float) {
        // Input berupa hasil hitungan dari fungsi armAngle()
        if (!((abs(elbowAngle) > 50) && (abs(elbowAngle) < 90))) {
            // exit function
            return
        }

        if (isUpPushup) {
            println("down")
        }

        isUpPushup = false
        isDownPushup = true
    }

    private fun checkPushup(backAngle: Float): Int {
        // Input berupa hasil hitungan dari backAngle()
        // measure appropriate back angle and returns warning code (1 OK, 0 BAD)
        if (abs(backAngle) <= 160) {
            return 0
        }
        return 1
    }

// ------------------------------ Fungsi Cek Repetisi Squat

    private fun checkSquat(hipAngle: Float): Int {
        // Input dari hasil fungsi hipAngle()
        // measure appropriate hip angle and returns warning code (1 OK, 0 BAD)
        if (!((abs(hipAngle) > 60) && (abs(hipAngle) < 80))) {
            return 0
        }
        return 1
    }

    // Fungsi untuk ngecek apakah user di posisi squat berdiri
    private fun upSquat(kneeAngle: Float, repsVar: Int): Int {
        var reps = repsVar
        if (!((abs(kneeAngle) > 170) && (abs(kneeAngle) < 195))) {
            return reps
        }

        if (isDownSquat) {
            println("up")
            reps++
        }

        isUpSquat = true
        isDownSquat = false

        return reps
    }

    // Fungsi untuk ngecek posisi user sedang jongkok saat squat
    private fun downSquat(kneeAngle: Float) {
        if (!((abs(kneeAngle) > 255) && (abs(kneeAngle) < 280))) {
            // exit function
            return
        }

        if (isUpSquat) {
            println("down")
        }

        isUpSquat = false
        isDownSquat = true
    }

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 101
    }

}