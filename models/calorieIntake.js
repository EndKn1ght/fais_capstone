const { DataTypes } = require('sequelize');
const sequelize = require('../database');
const User = require('./users');

const CalorieIntake = sequelize.define('CalorieIntake', {
  intake_id: {
    type: DataTypes.INTEGER,
    primaryKey: true,
    autoIncrement: true,
  },
  user_id: DataTypes.INTEGER,
  intake_date: DataTypes.DATE,
  meal_description: DataTypes.STRING,
  calories_consumed: DataTypes.INTEGER,
});

CalorieIntake.belongsTo(User, { foreignKey: 'user_id' });

module.exports = CalorieIntake;
