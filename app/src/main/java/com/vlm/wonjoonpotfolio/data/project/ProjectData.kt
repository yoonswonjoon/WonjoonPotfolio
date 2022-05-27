package com.vlm.wonjoonpotfolio.data.project

import android.net.Uri
import java.io.Serializable


data class ProjectDto(
    val number :Int = 0,
    val name : String = "" ,
    val long : String = "",
    val briefEx : String = "",
    val uri : String= "", // !!
    val uriList : List<String> = listOf(), // !!
    val downloadUri :String = "",
    val participant : List<String> = listOf(),
    val projectDetail : String = "" ,
    val difficult :Map<String, String> = mapOf(),
    val architecture: String? = null,
    val server: String? = null,
    val localDB: String? = null,
    val logIn: String? = null,
    val ui: String? = null,
    val stacks: String? = null
) : Serializable {
    constructor() : this(1)

    fun toProjectData(uri: Uri?): ProjectData {
        return ProjectData(
            projectStatecks = ProjectStacks(
                architecture = this.architecture,
                server = this.server,
                localDB = this.localDB,
                logIn = this.logIn,
                ui = this.ui,
                stacks = this.stacks
            ),
            number = this.number,
            name = this.name,
            long = this.long,
            briefEx = this.briefEx,
            uri = uri, // !!
            uriList = listOf(), // !!
            downloadUri = this.downloadUri,
            participant = this.participant,
            projectDetail = this.projectDetail,
            difficult = this.difficult,
        )
    }
}

data class ProjectStacks(
    val architecture : String? = null,
    val server : String? = null,
    val localDB : String? = null,
    val logIn : String? = null,
    val ui : String? = null,
    val stacks : String? = null
){

}


data class ProjectData(
    val projectStatecks : ProjectStacks = ProjectStacks(),
    val number : Int = 0,
    val name: String = "",
    val long : String = "",
    val briefEx: String = "",
    val uri : Uri? = null,
    val uriList : List<Uri?> = listOf(),
    val downloadUri : String? = null,
    val participant : List<String> = listOf(),
    val projectDetail : String = "",
    val difficult : Map<String,String> = mapOf(),
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