const { DataTypes } = require("sequelize");
const sequelize = require("../database");
const User = require("./users");

const Auth = sequelize.define("Auth", {
  token: {
    type: DataTypes.STRING(255),
    primaryKey: true,
  },
  user_id: {
    type: DataTypes.INTEGER,
    allowNull: false,
    references: {
      model: User,
      key: "user_id",
    },
    onDelete: "CASCADE",
    onUpdate: "CASCADE",
  },
  expiration_date: {
    type: DataTypes.DATE,
    allowNull: false,
  },
});

module.exports = Auth;
