const {
  createUser,
  updateUser,
  deleteUser,
  getAllUsers,
  getUserById,
} = require("./controllers/users");
const {
  createWorkout,
  updateWorkout,
  deleteWorkout,
  getAllWorkouts,
  getWorkoutById,
} = require("./controllers/workouts");
const {
  createWorkoutHistory,
  updateWorkoutHistory,
  deleteWorkoutHistory,
  getAllWorkoutHistories,
  getAllWorkoutHistoriesByUser,
  getWorkoutHistoryById,
} = require("./controllers/workoutHistories");
// const { createCalorieIntake, updateCalorieIntake, deleteCalorieIntake, getAllCalorieIntakes, getUserCalorieIntakes, getCalorieIntakeById } = require('./controllers/calorieIntakes');
// const { login, deleteToken, validateToken } = require('./controllers/auth');

const routes = [
  // Users routes
  { method: "POST", path: "/users", handler: createUser },
  { method: "PUT", path: "/users/{userId}", handler: updateUser },
  { method: "DELETE", path: "/users/{userId}", handler: deleteUser },
  { method: "GET", path: "/users", handler: getAllUsers },
  { method: "GET", path: "/users/{userId}", handler: getUserById },

  // WorkoutNames routes
  { method: "POST", path: "/workouts", handler: createWorkout },
  { method: "PUT", path: "/workouts/{workoutId}", handler: updateWorkout },
  { method: "DELETE", path: "/workouts/{workoutId}", handler: deleteWorkout },
  { method: "GET", path: "/workouts", handler: getAllWorkouts },
  { method: "GET", path: "/workouts/{workoutId}", handler: getWorkoutById },

  // UserWorkoutHistory routes
  { method: "POST", path: "/workoutHistories", handler: createWorkoutHistory },
  {
    method: "PUT",
    path: "/workoutHistories/{historyId}",
    handler: updateWorkoutHistory,
  },
  {
    method: "DELETE",
    path: "/workoutHistories/{historyId}",
    handler: deleteWorkoutHistory,
  },
  { method: "GET", path: "/workoutHistories", handler: getAllWorkoutHistories },
  {
    method: "GET",
    path: "/workoutHistories/users/{userId}",
    handler: getAllWorkoutHistoriesByUser,
  },
  {
    method: "GET",
    path: "/workoutHistories/{historyId}",
    handler: getWorkoutHistoryById,
  },

  //   // CalorieIntake routes
  //   { method: 'POST', path: '/calorieIntakes', handler: createCalorieIntake },
  //   { method: 'PUT', path: '/calorieIntakes/{intakeId}', handler: updateCalorieIntake },
  //   { method: 'DELETE', path: '/calorieIntakes/{intakeId}', handler: deleteCalorieIntake },
  //   { method: 'GET', path: '/calorieIntakes', handler: getAllCalorieIntakes },
  //   { method: 'GET', path: '/calorieIntakes/users/{userId}', handler: getUserCalorieIntakes },
  //   { method: 'GET', path: '/calorieIntakes/{intakeId}', handler: getCalorieIntakeById },

  //   // Auth routes
  //   { method: 'POST', path: '/login', handler: login },
  //   { method: 'DELETE', path: '/token', handler: deleteToken},
  //   { method: 'GET', path: '/validateToken', handler: validateToken},
];

module.exports = routes;
