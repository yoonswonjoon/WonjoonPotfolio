package com.vlm.wonjoonpotfolio.data.iAm

import java.io.Serializable


data class IAm(
    val birthday : String,
    val school : String,
    val name : String,
    val introduce : String
) : Serializable {
    constructor() : this("","","","")
}
