const db = require("../config/db");

// Create a new workout
const createWorkout = async (request, h) => {
  try {
    const { workout_name } = request.payload;

    // Insert the new workout into the WorkoutNames table
    const result = await db.query(
      "INSERT INTO WorkoutNames (workout_name) VALUES (?)",
      [workout_name]
    );

    return h.response({ message: "Workout created successfully" }).code(201);
  } catch (error) {
    console.error("Error creating workout:", error);
    return h.response({ message: "Failed to create workout" }).code(500);
  }
};

// Update a workout
const updateWorkout = async (request, h) => {
  try {
    const workoutId = request.params.workoutId;
    const { workout_name } = request.payload;

    // Update the workout in the WorkoutNames table
    const result = await db.query(
      "UPDATE WorkoutNames SET workout_name = ? WHERE workout_id = ?",
      [workout_name, workoutId]
    );

    if (result.affectedRows === 0) {
      return h.response({ message: "Workout not found" }).code(404);
    }

    return h.response({ message: "Workout updated successfully" });
  } catch (error) {
    console.error("Error updating workout:", error);
    return h.response({ message: "Failed to update workout" }).code(500);
  }
};

// Delete a workout
const deleteWorkout = async (request, h) => {
  try {
    const workoutId = request.params.workoutId;

    // Delete the workout from the WorkoutNames table
    const result = await db.query(
      "DELETE FROM WorkoutNames WHERE workout_id = ?",
      [workoutId]
    );

    if (result.affectedRows === 0) {
      return h.response({ message: "Workout not found" }).code(404);
    }

    return h.response({ message: "Workout deleted successfully" });
  } catch (error) {
    console.error("Error deleting workout:", error);
    return h.response({ message: "Failed to delete workout" }).code(500);
  }
};

// Get all workouts
const getAllWorkouts = async (request, h) => {
  try {
    // Retrieve all workouts from the WorkoutNames table
    const [rows] = await db.query("SELECT * FROM WorkoutNames");

    return h.response(rows);
  } catch (error) {
    console.error("Error retrieving workouts:", error);
    return h.response({ message: "Failed to retrieve workouts" }).code(500);
  }
};

// Get a workout by ID
const getWorkoutById = async (request, h) => {
  try {
    const workoutId = request.params.workoutId;

    // Retrieve the workout from the WorkoutNames table by workout ID
    const [rows] = await db.query(
      "SELECT * FROM WorkoutNames WHERE workout_id = ?",
      [workoutId]
    );

    if (rows.length === 0) {
      return h.response({ message: "Workout not found" }).code(404);
    }

    return h.response(rows[0]);
  } catch (error) {
    console.error("Error retrieving workout:", error);
    return h.response({ message: "Failed to retrieve workout" }).code(500);
  }
};

module.exports = {
  createWorkout,
  updateWorkout,
  deleteWorkout,
  getAllWorkouts,
  getWorkoutById,
};
