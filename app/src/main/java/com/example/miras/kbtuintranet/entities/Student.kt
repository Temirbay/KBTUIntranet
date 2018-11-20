package com.example.miras.kbtuintranet.entities

import android.arch.persistence.room.Entity
import android.net.Uri

class Student {
    var name: String = ""
    var surname: String = ""
    var year: String = ""
    var uuid : String = ""
    var userId : String = ""
    var imageUrl : String = ""

    constructor()

    constructor(name : String, surname : String, year : String) {
        this.name = name
        this.surname = surname
        this.year = year
    }

    constructor(name : String, surname : String, year : String, userId : String) {
        this.name = name
        this.surname = surname
        this.year = year
        this.userId = userId
    }

    constructor(name : String, surname : String, year : String, userId : String, imageUrl : String) {
        this.name = name
        this.surname = surname
        this.year = year
        this.userId = userId
        this.imageUrl = imageUrl
    }
}