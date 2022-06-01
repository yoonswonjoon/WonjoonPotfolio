package com.vlm.wonjoonpotfolio.domain

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKey {

    val LOGIN_ID = stringPreferencesKey("LOGIN_ID")
    val LOGIN_PASSWORD = stringPreferencesKey("LOGIN_PASSWORD")
    val TEST_NULL = stringPreferencesKey("TEST_NULL")
}