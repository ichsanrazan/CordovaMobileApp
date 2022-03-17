package cordova.telkomsel.cordovamobileapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import cordova.telkomsel.cordovamobileapp.activityLog.MainLog
import cordova.telkomsel.cordovamobileapp.authentication.LoginActivity
import cordova.telkomsel.cordovamobileapp.authentication.helper.Constant
import cordova.telkomsel.cordovamobileapp.authentication.helper.PreferencesHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var sharedpref: PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedpref = PreferencesHelper(this)
        textUsername.text = sharedpref.getString( Constant.PREF_FULLNAME )

        button_logout.setOnClickListener {
            sharedpref.clear()
            Toast.makeText(this, "Logout Berhasil", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }

        card_activityLog.setOnClickListener {
            val intent = Intent(this, MainLog::class.java)
            startActivity(intent)
        }
    }
}