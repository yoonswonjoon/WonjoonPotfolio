package com.vlm.wonjoonpotfolio.data.iAm.iAmTextData

import java.io.Serializable


data class IAmTextData(
    val birthday : String,
    val school : String,
    val name : String,
    val introduce : String
) : Serializable {
    constructor() : this("","","","")
}
