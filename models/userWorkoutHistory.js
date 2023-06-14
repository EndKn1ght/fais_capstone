const { DataTypes } = require("sequelize");
const sequelize = require("../database");
const User = require("./users");
const WorkoutName = require("./workoutNames");

const UserWorkoutHistory = sequelize.define("UserWorkoutHistory", {
  history_id: {
    type: DataTypes.INTEGER,
    primaryKey: true,
    autoIncrement: true,
    allowNull: false,
  },
  user_id: {
    type: DataTypes.INTEGER,
    allowNull: false,
  },
  workout_id: {
    type: DataTypes.TEXT,
    allowNull: false,
  },
  workout_date: {
    type: DataTypes.DATE,
    allowNull: false,
  },
  duration_minutes: {
    type: DataTypes.INTEGER,
    allowNull: false,
  },
  calories_burned: {
    type: DataTypes.INTEGER,
    allowNull: false,
  },
});

UserWorkoutHistory.belongsTo(User, { foreignKey: "user_id" });
UserWorkoutHistory.belongsTo(WorkoutName, { foreignKey: "workout_id" });

module.exports = UserWorkoutHistory;
