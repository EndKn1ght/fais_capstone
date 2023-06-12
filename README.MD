## Auth Routes

| Method | Path               | Description             |
| ------ | ------------------ | ----------------------- |
| GET    | /auth/{token}      | Get auth by token       |
| DELETE | /auth/{token}      | Delete auth             |
| POST   | /login             | Login                   |

## Users Routes

| Method | Path                  | Description              |
| ------ | --------------------- | ------------------------ |
| GET    | /users/{token}        | Get users                |
| GET    | /user/{token}         | Get user by ID           |
| POST   | /register             | Create user              |
| PUT    | /users/{token}/{id}   | Update user              |
| DELETE | /users/{token}        | Delete user              |

## Calorie Intake Routes

| Method | Path                                | Description                          |
| ------ | ----------------------------------- | ------------------------------------ |
| GET    | /users/calorieIntakes/{token}       | Get calorie intakes by user ID       |
| GET    | /users/calorieIntake/{token}/{id}   | Get calorie intake by ID             |
| POST   | /users/calorieIntake/{token}        | Create calorie intake                |
| PUT    | /calorieIntakes/{token}/{id}        | Update calorie intake                |
| DELETE | /calorieIntakes/{token}/{id}        | Delete calorie intake                |

## User Workout History Routes

| Method | Path                                   | Description                             |
| ------ | -------------------------------------- | --------------------------------------- |
| GET    | /users/workoutHistory/{token}           | Get user workout history by user ID      |
| GET    | /users/workoutHistory/{token}/{id}      | Get user workout history by ID           |
| POST   | /users/workoutHistory/{token}           | Create user workout history              |
| PUT    | /workoutHistory/{token}/{id}            | Update user workout history              |
| DELETE | /workoutHistory/{token}/{id}            | Delete user workout history              |


## Workout Names Routes

| Method | Path                   | Description                |
| ------ | ---------------------- | -------------------------- |
| GET    | /workouts              | Get workout names          |
| GET    | /workout/{id}          | Get workout name by ID     |
| POST   | /workout               | Create workout name        |
| PUT    | /workout/{id}          | Update workout name        |
| DELETE | /workout/{id}          | Delete workout name        |