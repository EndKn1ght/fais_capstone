const Hapi = require("@hapi/hapi");
const sequelize = require("./database");
const usersRoutes = require("./routes/usersRoutes");
const workoutNamesRoutes = require("./routes/workoutNamesRoutes");
// const userWorkoutHistoryRoutes = require('./routes/userWorkoutHistoryRoutes');
const calorieIntakeRoutes = require("./routes/calorieIntakeRoutes");
const authRoutes = require("./routes/authRoutes");

const init = async () => {
  const server = Hapi.server({
    port: 3000,
    host: "localhost",
  });

  await sequelize.sync();

  server.route([
    ...usersRoutes,
    ...authRoutes,
    ...workoutNamesRoutes,
    ...calorieIntakeRoutes,
  ]);

  await server.start();
  console.log("Server running on %s", server.info.uri);
};

process.on("unhandledRejection", (err) => {
  console.error("Unhandled rejection:", err);
  process.exit(1);
});

init();
