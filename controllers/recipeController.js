const axios = require("axios");
require("dotenv").config();

const getRandomRecipes = async (request, h) => {
  try {
    const searchTerm = "chicken"; // Specify the search term for random recipes
    const appId = process.env.EDAMAM_APP;
    const appKey = process.env.EDAMAM_KEY;
    const tipe = ["Breakfast", "Dinnner", "Lunch", "Snack", "Teatime"];

    // Set up the API endpoint and parameters for recipe search
    const apiUrl = "https://api.edamam.com/api/recipes/v2";
    const queryParams = {
      q: searchTerm,
      app_id: appId,
      app_key: appKey,
      type: "public",
      random: true,
      mealType: tipe[(Math.random() * tipe.length) | 0],
    };

    // Make the API request to retrieve recipes
    const response = await axios.get(apiUrl, { params: queryParams });

    // Return the random recipes
    return response.data;
  } catch (error) {
    // Handle any errors
    console.error(error);
    return h.response({ message: "Error" }).code(500);
  }
};

const getRecipes = async (request, h) => {
  try {
    const { query } = request.params;
    const appId = process.env.EDAMAM_APP;
    const appKey = process.env.EDAMAM_KEY;

    // Set up the API endpoint and parameters
    const apiUrl = "https://api.edamam.com/api/recipes/v2";
    const queryParams = {
      q: query,
      app_id: appId,
      app_key: appKey,
      type: "public",
    };

    // Make the API request
    const response = await axios.get(apiUrl, { params: queryParams });

    // Return the API response
    return response.data;
  } catch (error) {
    // Handle any errors
    console.error(error);
    return h.response({ message: "Error" }).code(500);
  }
};

const getRecipesUsingUri = async (request, h) => {
  try {
    const { uri } = request.params;
    const appId = process.env.EDAMAM_APP;
    const appKey = process.env.EDAMAM_KEY;

    // Set up the API endpoint and parameters
    const apiUrl = `https://api.edamam.com/api/recipes/v2/${uri}`;
    const queryParams = {
      app_id: appId,
      app_key: appKey,
      type: "public",
    };

    // Make the API request
    const response = await axios.get(apiUrl, { params: queryParams });

    // Return the API response
    return response.data;
  } catch (error) {
    // Handle any errors
    console.error(error);
    return h.response({ message: "Error" }).code(500);
  }
};

module.exports = { getRandomRecipes, getRecipes, getRecipesUsingUri };
