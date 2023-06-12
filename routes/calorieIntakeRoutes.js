const calorieIntakeController = require("../controllers/calorieIntakeController");

const calorieIntakeRoutes = [
  {
    method: "GET",
    path: "/users/calorieIntakes/{token}",
    handler: calorieIntakeController.getCalorieIntakesByUserId,
  },
  {
    method: "GET",
    path: "/users/calorieIntake/{token}/{intakeId}",
    handler: calorieIntakeController.getCalorieIntakesByUserId,
  },
  {
    method: "POST",
    path: "/users/calorieIntake/{token}",
    handler: calorieIntakeController.createCalorieIntake,
  },
  {
    method: "PUT",
    path: "/calorieIntakes/{token}/{intakeId}",
    handler: calorieIntakeController.updateCalorieIntake,
  },
  {
    method: "DELETE",
    path: "/calorieIntakes/{token}/{intakeId}",
    handler: calorieIntakeController.deleteCalorieIntake,
  },
];

module.exports = calorieIntakeRoutes;
