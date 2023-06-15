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
    method: "GET",
    path: "/recipe/{uri}",
    handler: recipeController.getRecipesUsingUri,
  },
];

module.exports = recipeRoutes;
