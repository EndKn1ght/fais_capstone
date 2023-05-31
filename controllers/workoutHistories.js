const db = require("../config/db");

// Create a new workout history
const createWorkoutHistory = async (request, h) => {
  try {
    const {
      user_id,
      workout_id,
      workout_date,
      duration_minutes,
      calories_burned,
    } = request.payload;

    // Insert the new workout history into the UserWorkoutHistory table
    const result = await db.query(
      "INSERT INTO UserWorkoutHistory (user_id, workout_id, workout_date, duration_minutes, calories_burned) VALUES (?, ?, ?, ?, ?)",
      [user_id, workout_id, workout_date, duration_minutes, calories_burned]
    );

    return h
      .response({ message: "Workout history created successfully" })
      .code(201);
  } catch (error) {
    console.error("Error creating workout history:", error);
    return h
      .response({ message: "Failed to create workout history" })
      .code(500);
  }
};

// Update a workout history
const updateWorkoutHistory = async (request, h) => {
  try {
    const historyId = request.params.historyId;
    const {
      user_id,
      workout_id,
      workout_date,
      duration_minutes,
      calories_burned,
    } = request.payload;

    // Update the workout history in the UserWorkoutHistory table
    const result = await db.query(
      "UPDATE UserWorkoutHistory SET user_id = ?, workout_id = ?, workout_date = ?, duration_minutes = ?, calories_burned = ? WHERE history_id = ?",
      [
        user_id,
        workout_id,
        workout_date,
        duration_minutes,
        calories_burned,
        historyId,
      ]
    );

    if (result.affectedRows === 0) {
      return h.response({ message: "Workout history not found" }).code(404);
    }

    return h.response({ message: "Workout history updated successfully" });
  } catch (error) {
    console.error("Error updating workout history:", error);
    return h
      .response({ message: "Failed to update workout history" })
      .code(500);
  }
};

// Delete a workout history
const deleteWorkoutHistory = async (request, h) => {
  try {
    const historyId = request.params.historyId;

    // Delete the workout history from the UserWorkoutHistory table
    const result = await db.query(
      "DELETE FROM UserWorkoutHistory WHERE history_id = ?",
      [historyId]
    );

    if (result.affectedRows === 0) {
      return h.response({ message: "Workout history not found" }).code(404);
    }

    return h.response({ message: "Workout history deleted successfully" });
  } catch (error) {
    console.error("Error deleting workout history:", error);
    return h
      .response({ message: "Failed to delete workout history" })
      .code(500);
  }
};

// Get all workout histories
const getAllWorkoutHistories = async (request, h) => {
  try {
    // Retrieve all workout histories from the UserWorkoutHistory table
    const [rows] = await db.query("SELECT * FROM UserWorkoutHistory");

    return h.response(rows);
  } catch (error) {
    console.error("Error retrieving workout histories:", error);
    return h
      .response({ message: "Failed to retrieve workout histories" })
      .code(500);
  }
};

// Get all workout histories for a specific user
const getAllWorkoutHistoriesByUser = async (request, h) => {
  try {
    const userId = request.params.userId;

    // Retrieve all workout histories for the specified user from the UserWorkoutHistory table
    const [rows] = await db.query(
      "SELECT * FROM UserWorkoutHistory WHERE user_id = ?",
      [userId]
    );

    return h.response(rows);
  } catch (error) {
    console.error("Error retrieving workout histories for user:", error);
    return h
      .response({ message: "Failed to retrieve workout histories for user" })
      .code(500);
  }
};

// Get a specific workout history
const getWorkoutHistoryById = async (request, h) => {
  try {
    const historyId = request.params.historyId;

    // Retrieve the workout history from the UserWorkoutHistory table by history ID
    const [rows] = await db.query(
      "SELECT * FROM UserWorkoutHistory WHERE history_id = ?",
      [historyId]
    );

    if (rows.length === 0) {
      return h.response({ message: "Workout history not found" }).code(404);
    }

    return h.response(rows[0]);
  } catch (error) {
    console.error("Error retrieving workout history:", error);
    return h
      .response({ message: "Failed to retrieve workout history" })
      .code(500);
  }
};

module.exports = {
  createWorkoutHistory,
  updateWorkoutHistory,
  deleteWorkoutHistory,
  getAllWorkoutHistories,
  getAllWorkoutHistoriesByUser,
  getWorkoutHistoryById,
};
