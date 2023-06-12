const userWorkoutHistoryController = require("../controllers/userWorkoutHistoryController");

const userWorkoutHistoryRoutes = [
  {
    method: "GET",
    path: "/users/workoutHistory/{token}",
    handler: userWorkoutHistoryController.getUserWorkoutHistoryByUserId,
  },
  {
    method: "GET",
    path: "/users/workoutHistory/{token}/{historyId}",
    handler: userWorkoutHistoryController.getUserWorkoutHistoryById,
  },
  {
    method: "POST",
    path: "/users/workoutHistory/{token}",
    handler: userWorkoutHistoryController.createUserWorkoutHistory,
  },
  {
    method: "PUT",
    path: "/workoutHistory/{token}/{historyId}",
    handler: userWorkoutHistoryController.updateUserWorkoutHistory,
  },
  {
    method: "DELETE",
    path: "/workoutHistory/{token}/{historyId}",
    handler: userWorkoutHistoryController.deleteUserWorkoutHistory,
  },
];

module.exports = userWorkoutHistoryRoutes;
