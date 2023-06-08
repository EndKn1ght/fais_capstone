const { DataTypes } = require('sequelize');
const sequelize = require('../database');

const WorkoutName = sequelize.define('WorkoutName', {
  workout_id: {
    type: DataTypes.INTEGER,
    primaryKey: true,
    autoIncrement: true,
  },
  workout_name: DataTypes.STRING,
});

module.exports = WorkoutName;
