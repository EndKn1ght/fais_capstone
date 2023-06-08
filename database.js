const { Sequelize } = require('sequelize');
require('dotenv').config();

const db_host = process.env.DATABASE_HOST;
const db_user = process.env.DATABASE_USER;
const db_pass = process.env.DATABASE_PASS;
const db_name = process.env.DATABASE_NAME;

// Replace the database credentials with your own
const sequelize = new Sequelize(db_name, db_user, db_pass, {
  host: db_host,
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
