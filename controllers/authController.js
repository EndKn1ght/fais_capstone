const Auth = require("../models/auth");
const User = require("../models/users");
const argon2 = require("argon2");
const crypto = require("crypto");
require("dotenv").config();

const generateToken = (length) => {
  const buffer = crypto.randomBytes(length);
  return buffer.toString("hex");
};

const login = async (request, h) => {
  try {
    const { username, password } = request.payload;

    // Find the user by username
    const user = await User.findOne({ where: { username } });

    // If user is not found or password is incorrect, return error response
    if (!user || !(await argon2.verify(user.password, password))) {
      return h.response("Invalid credentials").code(401);
    }

    // Generate a token
    const token = generateToken(64);

    // Save the token in the Auth table
    await Auth.create({
      token: token,
      user_id: user.user_id,
      expiration_date: new Date(Date.now() + 24 * 60 * 60 * 1000),
    });

    // Return the access token in the response
    return {
      access_token: token,
    };
  } catch (error) {
    console.error(error);
    return h.response({ message: "Error logging in" }).code(500);
  }
};

const getAuthByToken = async (request, h) => {
  try {
    const { token } = request.params;

    const auth = await Auth.findOne({ where: { token } });
    if (!auth) {
      return h
        .response({ message: "Authentication token not found" })
        .code(404);
    }
    return auth;
  } catch (error) {
    return h.response({ message: "Error" }).code(500);
  }
};

const deleteAuth = async (request, h) => {
  try {
    const { token } = request.params;

    const auth = await Auth.findOne({ where: { token } });
    if (!auth) {
      return h
        .response({ message: "Authentication token not found" })
        .code(404);
    }
    await auth.destroy();
    return h.response({ message: "Token successfully deleted" }).code(202);
  } catch (error) {
    return h.response({ message: "Error" }).code(500);
  }
};

module.exports = { login, getAuthByToken, deleteAuth };
