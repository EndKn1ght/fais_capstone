const fs = require("fs");
const csv = require("csv-parser");
const WorkoutName = require("../../models/workoutNames");

// Function to read the CSV file and insert records into the table
const seedWorkoutNames = async () => {
  fs.createReadStream("data/workoutNames.csv")
    .pipe(csv())
    .on("data", async (row) => {
      try {
        const {
          id,
          workout_name,
          description,
          type,
          body_part,
          equipment,
          level,
        } = row;

        await WorkoutName.create({
          workout_id: id,
          workout_name,
          description,
          type,
          body_part,
          equipment,
          level,
        });

        console.log(`Record inserted: ${workout_name}`);
      } catch (error) {
        console.log(`Error inserting record: ${error.message}`);
      }
    })
    .on("end", () => {
      console.log("Seed completed");
    });
};

// Call the seed function
seedWorkoutNames();
