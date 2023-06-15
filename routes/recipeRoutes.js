const recipeController = require("../controllers/recipeController");

const recipeRoutes = [
  {
    method: "GET",
    path: "/recipes",
    handler: recipeController.getRandomRecipes,
  },
  {
    method: "GET",
    path: "/recipes/{query}",
    handler: recipeController.getRecipes,
  },
  {
    method: "POST",
    path: "/recipe",
    handler: recipeController.getRecipesUsingUri,
  },
];

module.exports = recipeRoutes;
