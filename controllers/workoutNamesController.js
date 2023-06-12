const WorkoutName = require("../models/workoutNames");

const getWorkoutNames = async (request, h) => {
  try {
    const workoutNames = await WorkoutName.findAll();
    return workoutNames;
  } catch (error) {
    console.log(error);
    return h.response({ message: "An error occurred" }).code(500);
  }
};

const getWorkoutNameById = async (request, h) => {
  try {
    const { id } = request.params;
    const workoutName = await WorkoutName.findByPk(id);

    if (!workoutName) {
      return h.response({ message: "Workout name not found" }).code(404);
    }

    return workoutName;
  } catch (error) {
    console.log(error);
    return h.response({ message: "An error occurred" }).code(500);
  }
};

const createWorkoutName = async (request, h) => {
  try {
    const { name, description, type, body_part, equipment, level } =
      request.payload;
    const workoutName = await WorkoutName.create({
      workout_name: name,
      description,
      type,
      body_part,
      equipment,
      level,
    });
    return workoutName;
  } catch (error) {
    console.log(error);
    return h.response({ message: "Error creating workout name" }).code(500);
  }
};

const updateWorkoutName = async (request, h) => {
  try {
    const { id } = request.params;
    const { name, description } = request.payload;
    const workoutName = await WorkoutName.findByPk(id);
    if (!workoutName) {
      return h.response({ message: "Workout name not found" }).code(404);
    }
    workoutName.workout_name = name;
    workoutName.description = description;
    await workoutName.save();
    return workoutName;
  } catch (error) {
    console.log(error);
    return h.response({ message: "An error occurred" }).code(500);
  }
};

const deleteWorkoutName = async (request, h) => {
  try {
    const { id } = request.params;
    const workoutName = await WorkoutName.findByPk(id);
    if (!workoutName) {
      return h.response({ message: "Workout name not found" }).code(404);
    }
    await workoutName.destroy();
    return h
      .response({ message: "Workout name successfully deleted" })
      .code(202);
  } catch (error) {
    console.log(error);
    return h.response({ message: "An error occurred" }).code(500);
  }
};

module.exports = {
  getWorkoutNames,
  createWorkoutName,
  updateWorkoutName,
  deleteWorkoutName,
  getWorkoutNameById,
};
