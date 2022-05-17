package cordova.telkomsel.cordovamobileapp.standbySchedule.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cordova.telkomsel.cordovamobileapp.activityLog.model.PICList
import cordova.telkomsel.cordovamobileapp.retrofit.RetrofitInstance
import cordova.telkomsel.cordovamobileapp.retrofit.RetrofitService
import cordova.telkomsel.cordovamobileapp.standbySchedule.model.ScheduleList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScheduleListViewModel: ViewModel() {
    lateinit var scheduleListData: MutableLiveData<ScheduleList>

    init{
        scheduleListData = MutableLiveData()
    }

    fun getScheduleListObservableData(): MutableLiveData<ScheduleList>{
        return scheduleListData
    }

    fun getScheduleList(){
        val retroInstance = RetrofitInstance.getRetroInstanceSchedule().create(RetrofitService::class.java)
        val call = retroInstance.getScheduleList()
        call.enqueue(object: Callback<ScheduleList> {
            override fun onFailure(call: Call<ScheduleList>, t: Throwable) {
                scheduleListData.postValue(null)
            }

            override fun onResponse(call: Call<ScheduleList>, response: Response<ScheduleList>) {
                if(response.isSuccessful){
                    scheduleListData.postValue(response.body())
                } else {

                }
            }
        })
    }




}