package com.example.miras.kbtuintranet.entities

import android.arch.persistence.room.Entity
import android.net.Uri

class Teacher {
    var name: String = ""
    var surname: String = ""
    var uuid : String = ""
    var userId : String = ""
    var imageUrl : String = ""

    constructor(name : String, surname : String) {
        this.name = name
        this.surname = surname
    }

    constructor(name : String, surname : String, userId : String) {
        this.name = name
        this.surname = surname
        this.userId = userId
    }


    constructor(name : String, surname : String, userId : String, imageUrl : String) {
        this.name = name
        this.surname = surname
        this.userId = userId
        this.imageUrl = imageUrl
    }

    constructor()
}