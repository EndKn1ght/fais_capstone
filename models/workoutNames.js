const { DataTypes } = require("sequelize");
const sequelize = require("../database");

const WorkoutName = sequelize.define("WorkoutName", {
  workout_id: {
    type: DataTypes.INTEGER,
    primaryKey: true,
    autoIncrement: true,
  },
  workout_name: {
    type: DataTypes.STRING,
    allowNull: false,
    unique: true,
  },
  description: {
    type: DataTypes.STRING,
    allowNull: false,
    unique: true,
  },
});

module.exports = WorkoutName;
