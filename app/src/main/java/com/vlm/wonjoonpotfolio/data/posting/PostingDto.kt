package com.vlm.wonjoonpotfolio.data.posting

import android.net.Uri
import com.vlm.wonjoonpotfolio.data.posting.reply.ReplyType
import java.util.*

data  class PostingDto(
    val pid : String,
    val dataUri : String? = null, //uri
    val date : Date,
    val nickname: String,
    val contents : String,
    val voteUp : Int,
    val voteDown : Int,
    val version : Int = 1
) {
    constructor() : this(
        pid = "", dataUri = null, date = Date(0), nickname="", contents="", voteUp=0, voteDown=0, version=0
    )

    fun toPosting(uri: Uri?) : Posting{
        return if(uri == null){
            Posting(
                pid = this.pid,
                dataUri = null,
                date = this.date,
                nickname = this.nickname,
                contents = this.contents,
                voteUp = this.voteUp,
                voteDown = this.voteDown,
                version = this.version
            )
        }else{
            Posting(
                pid = this.pid,
                dataUri = uri,
                date = this.date,
                nickname = this.nickname,
                contents = this.contents,
                voteUp = this.voteUp,
                voteDown = this.voteDown,
                version = this.version
            )
        }
    }
}

data  class Posting(
    val pid : String,
    val dataUri : Uri? = null, //uri
    val date : Date,
    val nickname: String,
    val contents : String,
    val voteUp : Int,
    val voteDown : Int,
    val version : Int = 1
) {

}