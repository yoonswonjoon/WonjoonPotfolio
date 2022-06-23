package com.vlm.wonjoonpotfolio.data.posting.reply

import java.util.*

enum class ReplyType{
    POSTING_REPLY,
    REPLY_REPLY
}

data class Reply(
    val rid : String,
    val type : ReplyType,
    val data : Any? = null,
    val date : Date,
    val nickname: String,
    val contents : String,
    val voteUp : Int,
    val voteDown : Int
) {
}