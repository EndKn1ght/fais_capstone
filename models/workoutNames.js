const { DataTypes } = require("sequelize");
const sequelize = require("../database");

const WorkoutName = sequelize.define("WorkoutName", {
  workout_id: {
    type: DataTypes.STRING(4),
    primaryKey: true,
    allowNull: false,
  },
  workout_name: {
    type: DataTypes.STRING,
    allowNull: false,
    unique: true,
  },
  description: {
    type: DataTypes.TEXT,
    allowNull: false,
  },
  type: {
    type: DataTypes.STRING,
    allowNull: false,
  },
  body_part: {
    type: DataTypes.STRING,
    allowNull: false,
  },
  equipment: {
    type: DataTypes.STRING,
    allowNull: false,
  },
  level: {
    type: DataTypes.STRING,
    allowNull: false,
  },
});

module.exports = WorkoutName;
