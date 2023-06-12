const CalorieIntake = require("../models/calorieIntake");
const User = require("../models/users");
const Auth = require("../models/auth");

const getCalorieIntakesByUserId = async (request, h) => {
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

    const calorieIntakes = await CalorieIntake.findAll({
      where: { user_id: auth.user_id },
    });

    return calorieIntakes;
  } catch (error) {
    console.log(error);
    return h.response({ message: "An error occurred" }).code(500);
  }
};

const getCalorieIntakeById = async (request, h) => {
  try {
    const { token, intakeId } = request.params;

    const auth = await Auth.findByPk(token);

    if (!auth) {
      return h.response({ message: "Not authorized" }).code(401);
    }

    const calorieIntake = await CalorieIntake.findByPk(intakeId, {
      include: User,
    });

    if (calorieIntake.user_id !== auth.user_id) {
      return h.response({ message: "Not authorized" }).code(401);
    }
    if (!calorieIntake) {
      return h.response({ message: "Calorie intake not found" }).code(404);
    }

    return calorieIntake;
  } catch (error) {
    console.log(error);
    return h.response({ message: "An error occurred" }).code(500);
  }
};

const createCalorieIntake = async (request, h) => {
  try {
    const { token } = request.params;
    const { intake_date, meal_description, calories_consumed } =
      request.payload;

    const auth = await Auth.findByPk(token);

    if (!auth) {
      return h.response({ message: "Not authorized" }).code(401);
    }

    const user = await User.findByPk(auth.user_id);
    if (!user) {
      return h.response({ message: "User not found" }).code(404);
    }

    const calorieIntake = await CalorieIntake.create({
      user_id: auth.user_id,
      intake_date,
      meal_description,
      calories_consumed,
    });

    return calorieIntake;
  } catch (error) {
    console.log(error);
    return h.response({ message: "An error occurred" }).code(500);
  }
};

const updateCalorieIntake = async (request, h) => {
  try {
    const { token, intakeId } = request.params;
    const { intake_date, meal_description, calories_consumed } =
      request.payload;

    const auth = await Auth.findByPk(token);
    const user = await User.findByPk(auth.userId);

    if (!auth || auth.userId !== user.id) {
      return h.response({ message: "Not authorized" }).code(401);
    }

    if (!user) {
      return h.response({ message: "User not found" }).code(404);
    }

    const calorieIntake = await CalorieIntake.findByPk(intakeId);
    if (!calorieIntake) {
      return h.response({ message: "Calorie intake not found" }).code(404);
    }

    calorieIntake.intake_date = intake_date;
    calorieIntake.meal_description = meal_description;
    calorieIntake.calories_consumed = calories_consumed;
    await calorieIntake.save();

    return calorieIntake;
  } catch (error) {
    console.log(error);
    return h.response({ message: "An error occurred" }).code(500);
  }
};

const deleteCalorieIntake = async (request, h) => {
  try {
    const { token, intakeId } = request.params;

    const auth = await Auth.findByPk(token);

    if (!auth) {
      return h.response({ message: "Not authorized" }).code(401);
    }

    const calorieIntake = await CalorieIntake.findByPk(intakeId);
    if (!calorieIntake) {
      return h.response({ message: "Calorie intake not found" }).code(404);
    }

    if (calorieIntake.user_id !== auth.userId) {
      return h.response({ message: "Not authorized" }).code(401);
    }

    await calorieIntake.destroy();
    return h
      .response({ message: "Calorie intake successfully deleted" })
      .code(202);
  } catch (error) {
    console.log(error);
    return h.response({ message: "An error occurred" }).code(500);
  }
};

module.exports = {
  getCalorieIntakesByUserId,
  createCalorieIntake,
  updateCalorieIntake,
  deleteCalorieIntake,
  getCalorieIntakeById,
};
