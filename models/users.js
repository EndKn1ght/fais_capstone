const { Sequelize, DataTypes } = require("sequelize");
const sequelize = require("../database");

const User = sequelize.define("User", {
  user_id: {
    type: DataTypes.INTEGER,
    primaryKey: true,
    autoIncrement: true,
    allowNull: false
  },
  username: {
    type: DataTypes.STRING,
    allowNull: false
  },
  password: {
    type: DataTypes.STRING,
    allowNull: false
  },
  email: {
    type: DataTypes.STRING,
    allowNull: false,
    validate: {
      isEmail: true,
    },
  },
  date_of_birth: {
    type: DataTypes.DATE,
    allowNull: false
  },
  height: {
    type: DataTypes.DECIMAL(5, 2),
    allowNull: false
  },
  weight: {
    type: DataTypes.DECIMAL(5, 2),
    allowNull: false
  },
});

module.exports = User;
