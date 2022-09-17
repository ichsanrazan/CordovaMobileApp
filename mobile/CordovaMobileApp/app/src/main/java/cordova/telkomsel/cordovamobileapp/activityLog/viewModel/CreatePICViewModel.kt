package cordova.telkomsel.cordovamobileapp.activityLog.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cordova.telkomsel.cordovamobileapp.activityLog.model.PIC
import cordova.telkomsel.cordovamobileapp.activityLog.model.PICResponse
import cordova.telkomsel.cordovamobileapp.retrofit.RetrofitInstance
import cordova.telkomsel.cordovamobileapp.retrofit.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreatePICViewModel : ViewModel() {

    lateinit var createPICLiveData: MutableLiveData<PICResponse?>
    init {
        createPICLiveData = MutableLiveData()
    }

    fun getCreatePICObservable(): MutableLiveData<PICResponse?> {
        return createPICLiveData
    }

    fun createPIC(pic: PIC){
        val retroInstance = RetrofitInstance.getRetroInstance().create(RetrofitService::class.java)
        val call = retroInstance.createPIC(pic)
        call.enqueue(object: Callback<PICResponse?> {
            override fun onFailure(call: Call<PICResponse?>, t: Throwable) {
                createPICLiveData.postValue(null)
            }

            override fun onResponse(call: Call<PICResponse?>, response: Response<PICResponse?>) {
                if(response.isSuccessful){
                    createPICLiveData.postValue(response.body())
                } else {
                    createPICLiveData.postValue(null)
                }
            }
        })
    }
}