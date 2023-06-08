const { Auth } = require('../models/auth');

const createAuth = async (request, h) => {
  try {
    const { token, user_id, expiration_date } = request.payload;
    const auth = await Auth.create({
      token,
      user_id,
      expiration_date,
    });
    return auth;
  } catch (error) {
    return h.response('Error').code(500);
  }
};

const getAuthByToken = async (request, h) => {
  try {
    const { token } = request.params;
    const auth = await Auth.findOne({ where: { token } });
    if (!auth) {
      return h.response('Authentication token not found').code(404);
    }
    return auth;
  } catch (error) {
    return h.response('Error').code(500);
  }
};

const deleteAuth = async (request, h) => {
  try {
    const { token } = request.params;
    const auth = await Auth.findOne({ where: { token } });
    if (!auth) {
      return h.response('Authentication token not found').code(404);
    }
    await auth.destroy();
    return h.response().code(204);
  } catch (error) {
    return h.response('Error').code(500);
  }
};

module.exports = { createAuth, getAuthByToken, deleteAuth };
