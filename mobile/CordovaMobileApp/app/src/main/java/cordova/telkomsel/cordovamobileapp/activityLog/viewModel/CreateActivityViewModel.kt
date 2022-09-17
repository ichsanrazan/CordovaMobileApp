package cordova.telkomsel.cordovamobileapp.activityLog.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cordova.telkomsel.cordovamobileapp.activityLog.model.Activity
import cordova.telkomsel.cordovamobileapp.activityLog.model.ActivityDelete
import cordova.telkomsel.cordovamobileapp.activityLog.model.ActivityResponse
import cordova.telkomsel.cordovamobileapp.retrofit.RetrofitInstance
import cordova.telkomsel.cordovamobileapp.retrofit.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateActivityViewModel : ViewModel() {

    lateinit var createActivityLiveData: MutableLiveData<ActivityResponse?>
    lateinit var deleteActivityLiveData: MutableLiveData<ActivityResponse?>

    init {
        createActivityLiveData = MutableLiveData()
        deleteActivityLiveData = MutableLiveData()
    }

    fun getCreateActivityObservable(): MutableLiveData<ActivityResponse?>{
        return createActivityLiveData
    }

    fun getDeleteActivityObservable(): MutableLiveData<ActivityResponse?>{
        return deleteActivityLiveData
    }

    fun createActivity(activity: Activity){
        val retroInstance = RetrofitInstance.getRetroInstance().create(RetrofitService::class.java)
        val call = retroInstance.createActivity(activity)
        call.enqueue(object: Callback<ActivityResponse?> {
            override fun onFailure(call: Call<ActivityResponse?>, t: Throwable) {
                createActivityLiveData.postValue(null)
            }

            override fun onResponse(call: Call<ActivityResponse?>, response: Response<ActivityResponse?>) {
                if(response.isSuccessful){
                    createActivityLiveData.postValue(response.body())
                } else {
                    createActivityLiveData.postValue(null)
                }
            }
        })
    }

    fun updateActivity(activity: Activity){
        val retroInstance = RetrofitInstance.getRetroInstance().create(RetrofitService::class.java)
        val call = retroInstance.updateActivity(activity)
        call.enqueue(object: Callback<ActivityResponse?> {
            override fun onFailure(call: Call<ActivityResponse?>, t: Throwable) {
                createActivityLiveData.postValue(null)
            }

            override fun onResponse(call: Call<ActivityResponse?>, response: Response<ActivityResponse?>) {
                if(response.isSuccessful){
                    createActivityLiveData.postValue(response.body())
                } else {
                    createActivityLiveData.postValue(null)
                }
            }
        })
    }

    fun deleteActivity(activity: ActivityDelete){
        val retroInstance = RetrofitInstance.getRetroInstance().create(RetrofitService::class.java)
        val call = retroInstance.deleteActivity(activity)
        call.enqueue(object: Callback<ActivityResponse?> {
            override fun onFailure(call: Call<ActivityResponse?>, t: Throwable) {
                deleteActivityLiveData.postValue(null)
            }

            override fun onResponse(call: Call<ActivityResponse?>, response: Response<ActivityResponse?>) {
                if(response.isSuccessful){
                    deleteActivityLiveData.postValue(response.body())
                } else {
                    deleteActivityLiveData.postValue(null)
                }
            }
        })
    }
}