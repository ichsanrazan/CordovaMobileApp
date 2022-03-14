package cordova.telkomsel.cordovamobileapp.activityLog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cordova.telkomsel.cordovamobileapp.activityLog.model.ActivityList
import cordova.telkomsel.cordovamobileapp.retrofit.RetrofitInstance
import cordova.telkomsel.cordovamobileapp.retrofit.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityLogViewModel: ViewModel() {
    lateinit var recyclerListData: MutableLiveData<ActivityList>

    init{
        recyclerListData = MutableLiveData()
    }

    fun getActivityListObservableData() : MutableLiveData<ActivityList>{
        return recyclerListData
    }

    fun getActivityList(){
        val retroInstance = RetrofitInstance.getRetroInstance().create(RetrofitService::class.java)
        val call = retroInstance.getActivityList()
        call.enqueue(object: Callback<ActivityList>{
            override fun onFailure(call: Call<ActivityList>, t: Throwable) {
                recyclerListData.postValue(null)
            }

            override fun onResponse(call: Call<ActivityList>, response: Response<ActivityList>) {
                if(response.isSuccessful){
                    recyclerListData.postValue(response.body())
                } else {
                    recyclerListData.postValue(null)
                }
            }
        })
    }
}