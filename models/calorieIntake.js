const { DataTypes } = require("sequelize");
const sequelize = require("../database");
const User = require("./users");

const CalorieIntake = sequelize.define("CalorieIntake", {
  intake_id: {
    type: DataTypes.INTEGER,
    primaryKey: true,
    autoIncrement: true,
    allowNull: false,
  },
  user_id: {
    type: DataTypes.INTEGER,
    allowNull: false,
  },
  intake_date: {
    type: DataTypes.DATE,
    allowNull: false,
  },
  meal_description: {
    type: DataTypes.STRING,
    allowNull: false,
  },
  calories_consumed: {
    type: DataTypes.INTEGER,
    allowNull: false,
  },
});

CalorieIntake.belongsTo(User, { foreignKey: "user_id" });

module.exports = CalorieIntake;
