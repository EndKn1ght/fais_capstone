# Workout Assistant
The workout Assistant for FAIZ app. The system will predict user's pose in doing squats and push-ups. It will alert the user if their pose is incorrect and count repetitions. 

To start predicting on user's pose, our model undergoes training using workout/exercise images. We employ pre trained MoveNet to extract body keypoints data from each image. The labeled data, along with the keypoints data and respective labels, are saved in a CSV file. Next, TensorFlow is utilized to create a dense model with dropouts. The model is converted to TFLite for deployment on Android or other devices.
### The workout assistant in our app follows these steps:
1. Resize each frame to match MoveNet's input dimensions.
2. Obtain keypoints data from the output of the MoveNet model.
3. Convert the keypoints data from a (1, 1, 17, 3) tensor to a float array.
4. Utilize the previously trained model (trained on the mentioned csv data) to predict workout types by inputting the keypoints float array.
5. Apply the model's predictions to calculate specific angles, count repetitions, and assess the user's workout forms

![The model's architecture](https://i.ibb.co/5RXpR74/Whats-App-Image-2023-06-16-at-17-55-09.jpg "Model architecture")

## Dataset
The [Kaggle exercise image dataset](https://www.kaggle.com/datasets/hasyimabdillah/workoutexercises-images) is processed by the MoveNet model to extract body keypoints data. This data is then saved to the keypoints_data.csv file and serves as the training dataset for the pose classification model.

## Model
This model utilizes transfer learning with the MoveNet model for predicting workout videos. MoveNet is a pre-trained deep learning model specifically created for real-time human pose estimation. It excels at detecting and tracking essential body keypoints in video streams. To ensure fast execution and minimal resource usage, we utilized [MoveNet Lightning]((https://tfhub.dev/google/movenet/singlepose/lightning/4)) for our system.

## Notebooks
Two notebooks are available:

 1. `pose_model.ipynb`; this notebook is used for training the pose detection model.
 2. `body_keypoints_calculator.ipynb`; this notebook is used to test angle calculations, repetition counting, and form checking.

## Deployment
For deploying this workout assistant model,  we have built Kotlin packages to deploy in Android application. There are two packages available:
1. `Pose_Classifier.kt`; this package is used to predict poses using the saved TFLite model.
2. `keypoints_calc.kt`; this file is responsible for calculating angles and other measurements within the Android app.
