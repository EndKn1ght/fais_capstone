const WorkoutNamesController = require("../controllers/workoutNamesController");

const workoutNamesRoutes = [
  {
    method: "GET",
    path: "/workouts",
    handler: WorkoutNamesController.getWorkoutNames,
  },
  {
    method: "GET",
    path: "/workout/{id}",
    handler: WorkoutNamesController.getWorkoutNameById,
  },
  {
    method: "POST",
    path: "/workout",
    handler: WorkoutNamesController.createWorkoutName,
  },
  {
    method: "PUT",
    path: "/workout/{id}",
    handler: WorkoutNamesController.updateWorkoutName,
  },
  {
    method: "DELETE",
    path: "/workout/{id}",
    handler: WorkoutNamesController.deleteWorkoutName,
  },
];

module.exports = workoutNamesRoutes;
