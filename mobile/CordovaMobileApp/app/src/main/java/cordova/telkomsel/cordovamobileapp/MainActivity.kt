package cordova.telkomsel.cordovamobileapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cordova.telkomsel.cordovamobileapp.activityLog.MainLog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        card_activityLog.setOnClickListener {
            val intent = Intent(this, MainLog::class.java)
            startActivity(intent)
        }
    }
}