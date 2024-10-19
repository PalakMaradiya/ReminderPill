package com.example.reminderpill.utils

import android.content.Context
import android.content.SharedPreferences

class PrefManager(context: Context?) {

    val PRIVATE_MODE = 0
    val PREF_NAME = "SharedPreferences"
    val IS_LOGIN = "isLogIn"
    val SHOWN_INTRO = "ShownIntro"


    var pref: SharedPreferences? = context?.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    var prefs: SharedPreferences? = context?.getSharedPreferences(SHOWN_INTRO, PRIVATE_MODE)
    var editor: SharedPreferences.Editor? = pref?.edit()

    fun setLogin(isLogIn: Boolean) {
        editor?.putBoolean(IS_LOGIN, isLogIn)
        editor?.commit()
    }

    fun setUsername(username: String) {
        editor?.putString("username", username)
        editor?.commit()
    }

    fun setUserEmail(userEmail: String) {
        editor?.putString("userEmail", userEmail)
        editor?.commit()
    }

    fun setUserPhone(userPhone: String) {
        editor?.putString("userPhone", userPhone)
        editor?.commit()
    }


    fun isLogIn(): Boolean {
        return pref?.getBoolean(IS_LOGIN, false)!!

    }

    fun getUsername(): String? {
        return pref?.getString("username", "")
    }

    fun getUserEmail(): String? {
        return pref?.getString("userEmail", "")
    }

    fun getUserPhone(): String? {
        return pref?.getString("userPhone", "")
    }

    fun removeData() {
        editor?.clear()
        editor?.commit()
    }
    fun clearPreferences() {
        editor?.clear()
        editor?.apply()
    }
    fun isFirstTimeLaunch(): Boolean? {
        return prefs?.getBoolean("first_time_launch", true)
    }

    fun setFirstTimeLaunch(isFirstTime: Boolean) {
        return prefs?.edit()?.putBoolean("first_time_launch", isFirstTime)!!.apply()
    }





}