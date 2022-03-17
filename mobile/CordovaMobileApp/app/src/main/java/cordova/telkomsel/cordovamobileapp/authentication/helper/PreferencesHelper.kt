package cordova.telkomsel.cordovamobileapp.authentication.helper

import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper (context: Context) {

    private val PREFS_NAME = "sharedpreflogin"
    private var sharedpref: SharedPreferences
    val editor: SharedPreferences.Editor

    init {
        sharedpref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        editor = sharedpref.edit()
    }

    fun put(key: String, value: String){
        editor
    }

}