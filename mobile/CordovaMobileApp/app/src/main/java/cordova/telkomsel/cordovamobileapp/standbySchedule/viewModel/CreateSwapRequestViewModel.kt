package cordova.telkomsel.cordovamobileapp.standbySchedule.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cordova.telkomsel.cordovamobileapp.retrofit.RetrofitInstance
import cordova.telkomsel.cordovamobileapp.retrofit.RetrofitService
import cordova.telkomsel.cordovamobileapp.standbySchedule.model.RequestDelete
import cordova.telkomsel.cordovamobileapp.standbySchedule.model.RequestResponse
import cordova.telkomsel.cordovamobileapp.standbySchedule.model.SwapRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateSwapRequestViewModel : ViewModel() {

    lateinit var createSwapRequestLiveData: MutableLiveData<RequestResponse?>
    lateinit var deleteSwapRequestLiveData: MutableLiveData<RequestResponse?>

    init {
        createSwapRequestLiveData = MutableLiveData()
        deleteSwapRequestLiveData = MutableLiveData()
    }

    fun getCreateRequestObservable(): MutableLiveData<RequestResponse?> {
        return createSwapRequestLiveData
    }

    fun getDeleteRequestObservable(): MutableLiveData<RequestResponse?> {
        return deleteSwapRequestLiveData
    }

    fun createRequest(request: SwapRequest){
        val retroInstance = RetrofitInstance.getRetroInstanceSchedule().create(RetrofitService::class.java)
        val call = retroInstance.createRequest(request)
        call.enqueue(object: Callback<RequestResponse?> {
            override fun onFailure(call: Call<RequestResponse?>, t: Throwable) {
                createSwapRequestLiveData.postValue(null)
            }

            override fun onResponse(call: Call<RequestResponse?>, response: Response<RequestResponse?>) {
                if(response.isSuccessful){
                    createSwapRequestLiveData.postValue(response.body())
                } else {
                    createSwapRequestLiveData.postValue(null)
                }
            }
        })
    }

    fun deleteRequest(request: RequestDelete){
        val retroInstance = RetrofitInstance.getRetroInstanceSchedule().create(RetrofitService::class.java)
        val call = retroInstance.deleteRequest(request)
        call.enqueue(object: Callback<RequestResponse?> {
            override fun onFailure(call: Call<RequestResponse?>, t: Throwable) {
                deleteSwapRequestLiveData.postValue(null)
            }

            override fun onResponse(call: Call<RequestResponse?>, response: Response<RequestResponse?>) {
                if(response.isSuccessful){
                    deleteSwapRequestLiveData.postValue(response.body())
                } else {
                    deleteSwapRequestLiveData.postValue(null)
                }
            }
        })
    }

}