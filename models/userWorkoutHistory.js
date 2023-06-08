const { DataTypes } = require('sequelize');
const sequelize = require('../database');
const User = require('./users');
const WorkoutName = require('./workoutNames');

const UserWorkoutHistory = sequelize.define('UserWorkoutHistory', {
  history_id: {
    type: DataTypes.INTEGER,
    primaryKey: true,
    autoIncrement: true,
  },
  user_id: DataTypes.INTEGER,
  workout_id: DataTypes.INTEGER,
  workout_date: DataTypes.DATE,
  duration_minutes: DataTypes.INTEGER,
  calories_burned: DataTypes.INTEGER,
});

UserWorkoutHistory.belongsTo(User, { foreignKey: 'user_id' });
UserWorkoutHistory.belongsTo(WorkoutName, { foreignKey: 'workout_id' });

module.exports = UserWorkoutHistory;
