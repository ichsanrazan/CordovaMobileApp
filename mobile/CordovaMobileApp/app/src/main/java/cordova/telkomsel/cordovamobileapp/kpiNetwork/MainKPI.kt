package cordova.telkomsel.cordovamobileapp.kpiNetwork

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import cordova.telkomsel.cordovamobileapp.activityLog.MainLog
import cordova.telkomsel.cordovamobileapp.standbySchedule.MainSchedule
import kotlinx.android.synthetic.main.activity_main_kpi.*
import kotlinx.android.synthetic.main.activity_main_log.*

class MainKPI : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var draweLayout: DrawerLayout
    private lateinit var listener: NavController.OnDestinationChangedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_kpi)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_kpi) as NavHostFragment
        navController = navHostFragment.findNavController()
        draweLayout = findViewById(R.id.drawer_layout_mainKPI)
        appBarConfiguration = AppBarConfiguration(navController.graph, draweLayout)

        setSupportActionBar(toolbar_activity_kpi)
        setupActionBarWithNavController(navController, appBarConfiguration)



        nav_view_kpi_network.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.mainActivity -> startActivity(Intent(this, MainActivity::class.java))
                R.id.activityLog -> startActivity(Intent(this, MainLog::class.java))
                R.id.standbySchedule -> startActivity(Intent(this, MainSchedule::class.java))
                R.id.kpiNetwork -> startActivity(Intent(this, MainKPI::class.java))
            }
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_kpi)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}