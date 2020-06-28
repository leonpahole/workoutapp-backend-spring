package com.leonpahole.workoutapp.model;

// each exercise can have time, weight and repetitions. to help user efficiently input the data about exercises, exercise types are introduced
// note that these types dont have any effect on data stored, i can have a TIMED exercise and still input weight (for example kettlebell swings with 20 kg for 1 minute)
/*
TIMED - exercise requires time input (like jogging) and usually doesn't have sets
WEIGHT - exercise requires weight and repetitions (like bench press)
BODYWEIGHT - exercise requires repetitions and only sometimes weight (like pull ups)
*/
public enum ExerciseType {
    TIMED, WEIGHT, BODYWEIGHT
}