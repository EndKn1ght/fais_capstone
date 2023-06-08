const User = require('../models/users');
const argon2 = require('argon2');

const getUsers = async (request, h) => {
  try {
    const users = await User.findAll();
    return users;
  } catch (error) {
    return h.response('Error').code(500);
  }
};

const getUserById = async (request, h) => {
  try {
    const { id } = request.params;
    const user = await User.findByPk(id);
    if (!user) {
      return h.response('User not found').code(404);
    }
    return user;
  } catch (error) {
    console.log(error);
    return h.response('Error').code(500);
  }
};

const createUser = async (request, h) => {
  try {
    const { username, password, email, date_of_birth, height, weight } = request.payload;
    const hashPassword = await argon2.hash(String(password));

    const user = await User.create({
      username,
      password : hashPassword,
      email,
      date_of_birth,
      height,
      weight,
    });
    return user;
  } catch (error) {
    console.log(error)
    return h.response('Error').code(500);
  }
};

const updateUser = async (request, h) => {
  try {
    const { id } = request.params;
    const { username, password, email, date_of_birth, height, weight } = request.payload;
    const hashPassword = await argon2.hash(String(password));
    const user = await User.findByPk(id);
    if (!user) {
      return h.response('User not found').code(404);
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
    return h.response('Error').code(500);
  }
};

const deleteUser = async (request, h) => {
  try {
    const { id } = request.params;
    const user = await User.findByPk(id);
    if (!user) {
      return h.response('User not found').code(404);
    }
    await user.destroy();
    return h.response().code(204);
  } catch (error) {
    return h.response('Error').code(500);
  }
};

module.exports = { getUsers, getUserById, createUser, updateUser, deleteUser };
