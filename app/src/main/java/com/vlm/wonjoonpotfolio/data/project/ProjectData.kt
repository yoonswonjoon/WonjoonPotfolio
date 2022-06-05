package com.vlm.wonjoonpotfolio.data.project

import android.net.Uri
import com.vlm.wonjoonpotfolio.domain.insertLine
import java.io.Serializable


data class ProjectDto(
    val uid : String = "",
    val git : String? = null,
    val number :Int = 0,
    val name : String = "" ,
    val long : String = "",
    val briefEx : String = "",
    val uri : String= "", // !!
    val imgUriList : List<String?> = listOf(), // !!
    val downloadUri :String = "",
    val participant : List<String> = listOf(),
    val projectDetail : String = "" ,
    val difficult :Map<String, String> = mapOf(),
    val architecture: String? = null,
    val server: String? = null,
    val localDB: String? = null,
    val logIn: String? = null,
    val ui: String? = null,
    val stacks: Map<String, String>? = null,
    val accPoint : Int =0,
    val participantCount : Int = 0
) : Serializable {
    constructor() : this("")

    fun toProjectData(uri: Uri?): ProjectData {
        val m = mutableMapOf<String,String>()
        this.stacks?.forEach {
            m[it.key] = it.value.toString()
        }
        return ProjectData(
            uid =this.uid,
            git = this.git,
            projectStatecks = ProjectStacks(
                architecture = this.architecture,
                server = this.server,
                localDB = this.localDB,
                logIn = this.logIn,
                ui = this.ui,
                stacks = m
            ),
            number = this.number,
            name = this.name,
            long = this.long,
            briefEx = this.briefEx,
            uri = uri, // !!
//            imgUriList = this.imgUriList, // !!
            downloadUri = this.downloadUri,
            participant = this.participant,
            projectDetail = this.projectDetail,
            difficult = this.difficult.map { it.key to it.value.insertLine() }.toMap().toSortedMap(),
            accPoint = this.accPoint,
            participantCount = this.participantCount
        )
    }
}

data class ProjectStacks(
    val architecture : String? = null,
    val server : String? = null,
    val localDB : String? = null,
    val logIn : String? = null,
    val ui : String? = null,
    val stacks : Map<String,String>? = null
){

}


data class ProjectData(
    val uid : String = "",
    val projectStatecks : ProjectStacks = ProjectStacks(),
    val git : String? = null,
    val number : Int = 0,
    val name: String = "",
    val long : String = "",
    val briefEx: String = "",
    val uri : Uri? = null,
    val imgUriList : List<Uri?> = listOf(),
    val downloadUri : String? = null,
    val participant : List<String> = listOf(),
    val projectDetail : String = "",
    val difficult : Map<String,String> = mapOf(),
    val comments : List<WjComment> = listOf(),
    val accPoint : Int,
    val participantCount : Int
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