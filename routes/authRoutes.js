const Auth = require('../controllers/authController');

const authRoutes = [
  {
    method: 'POST',
    path: '/auth',
    handler: Auth.createAuth,
  },
  {
    method: 'GET',
    path: '/auth/{token}',
    handler: Auth.getAuthByToken,
  },
  {
    method: 'DELETE',
    path: '/auth/{token}',
    handler: Auth.deleteAuth,
  },
];

module.exports = authRoutes;
