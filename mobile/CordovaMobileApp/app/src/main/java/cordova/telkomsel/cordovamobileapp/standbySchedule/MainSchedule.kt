package cordova.telkomsel.cordovamobileapp.standbySchedule

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
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
import cordova.telkomsel.cordovamobileapp.kpiNetwork.MainKPI
import cordova.telkomsel.cordovamobileapp.standbySchedule.fragment.*
import cordova.telkomsel.cordovamobileapp.standbySchedule.model.RequestDelete
import cordova.telkomsel.cordovamobileapp.standbySchedule.viewModel.CreateSwapRequestViewModel
import kotlinx.android.synthetic.main.activity_main_schedule.*

class MainSchedule : AppCompatActivity() {
    private lateinit var createSwapRequestViewModel: CreateSwapRequestViewModel
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var draweLayout: DrawerLayout
    private lateinit var listener: NavController.OnDestinationChangedListener

    private val dashboardFragment = ScheduleFragment()
    private val swapFragment = SwapScheduleFragment()
    private val messageFragment = MessageScheduleFragment()
    private val calendarFragment = CalendarScheduleFragment()
    private val notificationFragment = RequestScheduleFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_schedule)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_schedule) as NavHostFragment
        navController = navHostFragment.findNavController()
        draweLayout = findViewById(R.id.drawer_layout_mainSchedule)
        appBarConfiguration = AppBarConfiguration(navController.graph, draweLayout)

        setSupportActionBar(toolbar_activity_schedule)
        setupActionBarWithNavController(navController, appBarConfiguration)

        nav_view_activity_schedule.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.mainActivity -> startActivity(Intent(this, MainActivity::class.java))
                R.id.activityLog -> startActivity(Intent(this, MainLog::class.java))
                R.id.standbySchedule -> startActivity(Intent(this, MainSchedule::class.java))
                R.id.kpiNetwork -> startActivity(Intent(this, MainKPI::class.java))
            }
            true
        }

        schedule_bottom_navigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.ic_dashboard -> replaceFragment(dashboardFragment)
                R.id.ic_calendar -> replaceFragment(calendarFragment)
                R.id.ic_message -> replaceFragment(messageFragment)
                R.id.ic_request -> replaceFragment(notificationFragment)
                R.id.ic_swap -> replaceFragment(swapFragment)
            }
            true
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_schedule)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun replaceFragment(fragment: Fragment){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment_activity_schedule, fragment)
            transaction.commit()
        }
    }

    fun loadRequestData(id: Int?) {
        val deleteRequestDialog = AlertDialog.Builder(this)
            .setTitle("Hapus request")
            .setMessage("Apakah anda yakin untuk menghapus request ini?")
            .setPositiveButton("Ya"){_, _ ->
                val deleteRequest = RequestDelete(id)
                createSwapRequestViewModel.getDeleteRequestObservable().observe(this, {
                    if(it == null){
                        Toast.makeText(this, "Request gagal untuk dihapus", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "Request berhasil untuk dihapus", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                })
                createSwapRequestViewModel.deleteRequest(deleteRequest)
            }
            .setNegativeButton("Tidak"){_, _ ->
            }.create()
    }



}