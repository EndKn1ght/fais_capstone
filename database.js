const { Sequelize } = require('sequelize');

// Replace the database credentials with your own
const sequelize = new Sequelize('capstone', 'root', '', {
  host: 'localhost',
  dialect: 'mysql',
});

// Test the database connection
sequelize
  .authenticate()
  .then(() => {
    console.log('Connection to the database has been established successfully.');
  })
  .catch((error) => {
    console.error('Unable to connect to the database:', error);
  });

module.exports = sequelize;
