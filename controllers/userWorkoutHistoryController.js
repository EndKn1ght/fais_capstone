const UserWorkoutHistory = require("../models/userWorkoutHistory");
const User = require("../models/user");
const Auth = require("../models/auth");

const getUserWorkoutHistoryByUserId = async (request, h) => {
  try {
    const { token } = request.params;

    const auth = await Auth.findByPk(token);

    if (!auth) {
      return h.response({ message: "Not authorized" }).code(401);
    }

    const user = await User.findByPk(auth.user_id);

    if (!user) {
      return h.response({ message: "User not found" }).code(404);
    }

    const workoutHistory = await UserWorkoutHistory.findAll({
      where: { user_id: auth.user_id },
    });

    return workoutHistory;
  } catch (error) {
    console.log(error);
    return h.response({ message: "An error occurred" }).code(500);
  }
};

const getUserWorkoutHistoryById = async (request, h) => {
  try {
    const { token, historyId } = request.params;

    const auth = await Auth.findByPk(token);

    if (!auth) {
      return h.response({ message: "Not authorized" }).code(401);
    }

    const workoutHistory = await UserWorkoutHistory.findByPk(historyId, {
      include: User,
    });

    if (!workoutHistory) {
      return h.response({ message: "Workout history not found" }).code(404);
    }

    if (workoutHistory.user_id !== auth.user_id) {
      return h.response({ message: "Not authorized" }).code(401);
    }

    return workoutHistory;
  } catch (error) {
    console.log(error);
    return h.response({ message: "An error occurred" }).code(500);
  }
};

const createUserWorkoutHistory = async (request, h) => {
  try {
    const { token } = request.params;
    const { workout_id, workout_date, duration_minutes, calories_burned } =
      request.payload;

    const auth = await Auth.findByPk(token);

    if (!auth) {
      return h.response({ message: "Not authorized" }).code(401);
    }

    const user = await User.findByPk(auth.user_id);

    if (!user) {
      return h.response({ message: "User not found" }).code(404);
    }

    const workoutHistory = await UserWorkoutHistory.create({
      user_id: auth.user_id,
      workout_id,
      workout_date,
      duration_minutes,
      calories_burned,
    });

    return workoutHistory;
  } catch (error) {
    console.log(error);
    return h.response({ message: "An error occurred" }).code(500);
  }
};

const updateUserWorkoutHistory = async (request, h) => {
  try {
    const { token, historyId } = request.params;
    const { workout_id, workout_date, duration_minutes, calories_burned } =
      request.payload;

    const auth = await Auth.findByPk(token);
    const user = await User.findByPk(auth.user_id);

    if (!auth || auth.user_id !== user.id) {
      return h.response({ message: "Not authorized" }).code(401);
    }

    const workoutHistory = await UserWorkoutHistory.findByPk(historyId);

    if (!workoutHistory) {
      return h.response({ message: "Workout history not found" }).code(404);
    }

    workoutHistory.workout_id = workout_id;
    workoutHistory.workout_date = workout_date;
    workoutHistory.duration_minutes = duration_minutes;
    workoutHistory.calories_burned = calories_burned;

    await workoutHistory.save();

    return workoutHistory;
  } catch (error) {
    console.log(error);
    return h.response({ message: "An error occurred" }).code(500);
  }
};

const deleteUserWorkoutHistory = async (request, h) => {
  try {
    const { token, historyId } = request.params;

    const auth = await Auth.findByPk(token);

    if (!auth) {
      return h.response({ message: "Not authorized" }).code(401);
    }

    const workoutHistory = await UserWorkoutHistory.findByPk(historyId);

    if (!workoutHistory) {
      return h.response({ message: "Workout history not found" }).code(404);
    }

    if (workoutHistory.user_id !== auth.user_id) {
      return h.response({ message: "Not authorized" }).code(401);
    }

    await workoutHistory.destroy();

    return h
      .response({ message: "Workout history successfully deleted" })
      .code(202);
  } catch (error) {
    console.log(error);
    return h.response({ message: "An error occurred" }).code(500);
  }
};

module.exports = {
  getUserWorkoutHistoryByUserId,
  createUserWorkoutHistory,
  updateUserWorkoutHistory,
  deleteUserWorkoutHistory,
  getUserWorkoutHistoryById,
};
