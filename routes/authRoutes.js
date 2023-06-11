const Auth = require("../controllers/authController");

const authRoutes = [
  {
    method: "GET",
    path: "/auth/{token}",
    handler: Auth.getAuthByToken,
  },
  {
    method: "DELETE",
    path: "/auth/{token}",
    handler: Auth.deleteAuth,
  },
  {
    method: "POST",
    path: "/login",
    handler: Auth.login,
  },
];

module.exports = authRoutes;
