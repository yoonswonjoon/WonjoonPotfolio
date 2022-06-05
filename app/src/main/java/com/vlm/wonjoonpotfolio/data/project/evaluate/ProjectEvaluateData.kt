package com.vlm.wonjoonpotfolio.data.project.evaluate

import com.google.firebase.firestore.ServerTimestamp
import java.time.LocalDateTime
import java.util.*

data class ProjectEvaluateData(
    val eid: String = "",
    val point: Int = 0,
    val msg: String? = null,
    @ServerTimestamp val date: Date? = null ,
    val uid: String = "",
    val opened : Boolean = true
) {
}