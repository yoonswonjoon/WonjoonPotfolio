package com.vlm.wonjoonpotfolio.data.iAm.iAmTextData

import com.vlm.wonjoonpotfolio.ui.iAm.IAmMainViewState
import java.io.Serializable


data class IAmTextData(
    val birthday : String,
    val school : String,
    val name : String,
    val introduce : String,
    val eid : String,
    val before : String,
    val phone : String
) : Serializable {
    constructor() : this("","","","","","","")

    fun toIAmViewState() : IAmMainViewState{
        return IAmMainViewState(
            before = before,
            name = name,
            birthday = birthday,
            introduce = introduce,
            school = school,
            phone = phone,
            eid = eid,
        )
    }
}
