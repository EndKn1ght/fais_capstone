const Users = require("../controllers/usersController");

const userRoutes = [
  {
    method: "GET",
    path: "/users/{token}",
    handler: Users.getUsers,
  },
  {
    method: "GET",
    path: "/user/{token}",
    handler: Users.getUserById,
  },
  {
    method: "POST",
    path: "/register",
    handler: Users.createUser,
  },
  {
    method: "PUT",
    path: "/users/{token}/{id}",
    handler: Users.updateUser,
  },
  {
    method: "DELETE",
    path: "/users/{token}",
    handler: Users.deleteUser,
  },
];

module.exports = userRoutes;
