package cordova.telkomsel.cordovamobileapp.activityLog

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import cordova.telkomsel.cordovamobileapp.MainActivity
import cordova.telkomsel.cordovamobileapp.R
import kotlinx.android.synthetic.main.activity_main_log.*

class MainLog : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var draweLayout: DrawerLayout
    private lateinit var listener: NavController.OnDestinationChangedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_log)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_log) as NavHostFragment
        navController = navHostFragment.findNavController()
        draweLayout = findViewById(R.id.drawer_layout_mainLog)
        appBarConfiguration = AppBarConfiguration(navController.graph, draweLayout)

        setSupportActionBar(toolbar_activity_log)
        setupActionBarWithNavController(navController, appBarConfiguration)


        nav_view_activity_log.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.mainActivity -> startActivity(Intent(this, MainActivity::class.java))
            }
            true
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_log)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_log_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }
    private fun setMode(selectedMode: Int) {
        when (selectedMode) {
            R.id.action_help -> {
                val uGuide = Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/file/d/1SUqE3rpuw2M2Oastxbbzr6M5TA37Ek2g/view?usp=sharing"))
                startActivity(uGuide)
            }
        }
    }
}