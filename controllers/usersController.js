const User = require("../models/users");
const Auth = require("../models/auth");
const argon2 = require("argon2");

const getUsers = async (request, h) => {
  try {
    const { token } = request.params;

    const auth = await Auth.findOne({ where: { token } });
    if (!auth) {
      return h.response({ message: "Not authorized" }).code(401);
    }

    const users = await User.findAll();
    return users;
  } catch (error) {
    console.log(error);
    return h.response({ message: "An error occurred" }).code(500);
  }
};

const getUserById = async (request, h) => {
  try {
    const { token } = request.params;

    const auth = await Auth.findOne({ where: { token } });
    if (!auth) {
      return h.response({ message: "Not authorized" }).code(401);
    }

    const user = await User.findByPk(auth.user_id);
    if (!user) {
      return h.response({ message: "User not found" }).code(404);
    }

    return user;
  } catch (error) {
    console.log(error);
    return h.response({ message: "An error occurred" }).code(500);
  }
};

const createUser = async (request, h) => {
  try {
    const { username, password, email, date_of_birth, height, weight } =
      request.payload;
    const hashPassword = await argon2.hash(String(password));

    const userUname = await User.findAll({
      attributes: ["username"],
      where: {
        username: username,
      },
    });

    console.log(userUname);

    if (userUname.length == 0) {
      const user = await User.create({
        username,
        password: hashPassword,
        email,
        date_of_birth,
        height,
        weight,
      });
      return user;
    } else {
      return h.response({ message: "Username already taken" }).code(409);
    }
  } catch (error) {
    return h.response({ message: "Error creating user" }).code(500);
  }
};

const updateUser = async (request, h) => {
  try {
    const { token, id } = request.params;
    const { username, password, email, date_of_birth, height, weight } =
      request.payload;

    const auth = await Auth.findOne({ where: { token } });
    if (!auth) {
      return h.response({ message: "Not authorized" }).code(401);
    }

    const hashPassword = await argon2.hash(String(password));
    const user = await User.findByPk(id);
    if (!user) {
      return h.response({ message: "User not found" }).code(404);
    }

    user.username = username;
    user.password = hashPassword;
    user.email = email;
    user.date_of_birth = date_of_birth;
    user.height = height;
    user.weight = weight;
    await user.save();

    return user;
  } catch (error) {
    return h.response({ message: "An error occurred" }).code(500);
  }
};

const deleteUser = async (request, h) => {
  try {
    const { token } = request.params;

    const auth = await Auth.findOne({ where: { token } });

    if (!auth) {
      return h.response({ message: "Not authorized" }).code(401);
    }

    const user = await User.findByPk(auth.user_id);
    if (!user) {
      return h.response({ message: "User not found" }).code(404);
    }

    await user.destroy();
    return h.response({ message: "User successfully deleted" }).code(202);
  } catch (error) {
    console.log(error);
    return h.response({ message: "An error occurred" }).code(500);
  }
};

module.exports = { getUsers, getUserById, createUser, updateUser, deleteUser };
