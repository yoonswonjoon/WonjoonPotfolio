package com.vlm.wonjoonpotfolio.data.project

import android.net.Uri
import java.io.Serializable


data class ProjectDao(
    val number :Int = 0,
    val name : String = "" ,
    val long : String = "",
    val briefEx : String = "",
    val uri : String= "", // !!
    val uriList : List<String> = listOf(), // !!
    val downloadUri :String = "",
    val stacks : String = "",
    val participant : List<String> = listOf(),
    val projectDetail : String = "" ,
    val difficult :Map<String, String> = mapOf(),
) : Serializable {
    constructor() : this(1)

    fun toProjectData(uri : Uri?) : ProjectData{
        return ProjectData(
            number = this.number,
         name  = this.name ,
         long  = this.long,
         briefEx  = this.briefEx,
         uri = uri, // !!
         uriList = listOf(), // !!
         downloadUri  = null,
         stacks  = this.stacks,
         participant = "()",
         projectDetail = this.projectDetail ,
         difficult = listOf(),
        )
    }
}

data class ProjectData(
    val number : Int = 0,
    val name: String = "",
    val long : String = "",
    val briefEx: String = "",
    val uri : Uri? = null,
    val uriList : List<Uri?> = listOf(),
    val downloadUri : Uri? = null,
    val stacks : String = "",
    val participant : String = "",
    val projectDetail : String = "",
    val difficult : List<Pair<String,String>> = listOf(),
    val comments : List<WjComment> = listOf()
) {
}

data class WjComment(
    val picNum : Int = 0,
    val comment : String = "",
    val rePly : List<Reply> = listOf()
)

data class Reply(
    val picNum: Int = 0,
    val comment: String = ""
)