const argon2 = require("argon2");
const db = require("../config/db");

// Create a new user
const createUser = async (request, h) => {
  try {
    var { username, password, email, date_of_birth, height, weight } =
      request.payload;
    password = await argon2.hash(String(password));

    // Insert the new user into the Users table
    const result = await db.query(
      "INSERT INTO Users (username, password, email, date_of_birth, height, weight) VALUES (?, ?, ?, ?, ?, ?)",
      [username, password, email, date_of_birth, height, weight]
    );

    return h.response({ message: "User created successfully" }).code(201);
  } catch (error) {
    console.error("Error creating user:", error);
    return h.response({ message: "Failed to create user" }).code(500);
  }
};

// Update a user
const updateUser = async (request, h) => {
  try {
    const userId = request.params.userId;
    const { username, password, email, date_of_birth, height, weight } =
      request.payload;

    // Update the user in the Users table
    const result = await db.query(
      "UPDATE Users SET username = ?, password = ?, email = ?, date_of_birth = ?, height = ?, weight = ? WHERE user_id = ?",
      [username, password, email, date_of_birth, height, weight, userId]
    );

    if (result.affectedRows === 0) {
      return h.response({ message: "User not found" }).code(404);
    }

    return h.response({ message: "User updated successfully" });
  } catch (error) {
    console.error("Error updating user:", error);
    return h.response({ message: "Failed to update user" }).code(500);
  }
};

// Delete a user
const deleteUser = async (request, h) => {
  try {
    const userId = request.params.userId;

    // Delete the user from the Users table
    const result = await db.query("DELETE FROM Users WHERE user_id = ?", [
      userId,
    ]);

    if (result.affectedRows === 0) {
      return h.response({ message: "User not found" }).code(404);
    }

    return h.response({ message: "User deleted successfully" });
  } catch (error) {
    console.error("Error deleting user:", error);
    return h.response({ message: "Failed to delete user" }).code(500);
  }
};

// Get all users
const getAllUsers = async (request, h) => {
  try {
    // Retrieve all users from the Users table
    const [rows] = await db.query("SELECT * FROM Users");

    return h.response(rows);
  } catch (error) {
    console.error("Error retrieving users:", error);
    return h.response({ message: "Failed to retrieve users" }).code(500);
  }
};

// Get a user by ID
const getUserById = async (request, h) => {
  try {
    const userId = request.params.userId;

    // Retrieve the user from the Users table by user ID
    const [rows] = await db.query("SELECT * FROM Users WHERE user_id = ?", [
      userId,
    ]);

    if (rows.length === 0) {
      return h.response({ message: "User not found" }).code(404);
    }

    return h.response(rows[0]);
  } catch (error) {
    console.error("Error retrieving user:", error);
    return h.response({ message: "Failed to retrieve user" }).code(500);
  }
};

module.exports = {
  createUser,
  updateUser,
  deleteUser,
  getAllUsers,
  getUserById,
};
