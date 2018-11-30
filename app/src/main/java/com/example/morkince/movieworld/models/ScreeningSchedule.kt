package com.example.morkince.movieworld.models

import com.google.firebase.Timestamp

class ScreeningSchedule {
    var screening_schedule_branch: ArrayList<String> ?= null
    var screening_schedule_cinema_id: HashMap<String, String> ?= null
    var screening_schedule_date: Timestamp ?= null
}