const Users = require("../controllers/usersController");

const userRoutes = [
  {
    method: "GET",
    path: "/users",
    handler: Users.getUsers,
  },
  {
    method: "GET",
    path: "/users/{id}",
    handler: Users.getUserById,
  },
  {
    method: "POST",
    path: "/users",
    handler: Users.createUser,
  },
  {
    method: "PUT",
    path: "/users/{id}",
    handler: Users.updateUser,
  },
  {
    method: "DELETE",
    path: "/users/{id}",
    handler: Users.deleteUser,
  },
];

module.exports = userRoutes;
