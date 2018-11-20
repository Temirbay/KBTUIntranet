package com.example.miras.kbtuintranet.entities

import android.arch.persistence.room.Entity
import android.os.Parcelable
import java.io.FileDescriptor

class Course(
        var name: String,
        var fall: String,
        var credit: String,
        var description: String,
        var teacherId: String) {
    var uuid : String = ""
}