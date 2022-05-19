package cordova.telkomsel.cordovamobileapp.standbySchedule.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cordova.telkomsel.cordovamobileapp.activityLog.model.ActivityList
import cordova.telkomsel.cordovamobileapp.retrofit.RetrofitInstance
import cordova.telkomsel.cordovamobileapp.retrofit.RetrofitService
import cordova.telkomsel.cordovamobileapp.standbySchedule.model.SwapRequestList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SwapRequestLogViewModel: ViewModel() {
    lateinit var recyclerListData: MutableLiveData<SwapRequestList>

    init{
        recyclerListData = MutableLiveData()
    }

    fun getSwapRequestListObservableData() : MutableLiveData<SwapRequestList> {
        return recyclerListData
    }

    fun getSwapRequestList(){
        val retroInstance = RetrofitInstance.getRetroInstanceSchedule().create(RetrofitService::class.java)
        val call = retroInstance.getSwapRequestList()
        call.enqueue(object: Callback<SwapRequestList> {
            override fun onFailure(call: Call<SwapRequestList>, t: Throwable) {
                recyclerListData.postValue(null)
            }

            override fun onResponse(call: Call<SwapRequestList>, response: Response<SwapRequestList>) {
                if(response.isSuccessful){
                    recyclerListData.postValue(response.body())
                } else {
                    recyclerListData.postValue(null)

                }
            }
        })
    }

    fun getReadRequest(searchText: String){
        val retroInstance = RetrofitInstance.getRetroInstanceSchedule().create(RetrofitService::class.java)
        val call = retroInstance.readRequest(searchText)
        call.enqueue(object: Callback<SwapRequestList>{
            override fun onFailure(call: Call<SwapRequestList>, t: Throwable) {
                recyclerListData.postValue(null)
            }

            override fun onResponse(call: Call<SwapRequestList>, response: Response<SwapRequestList>) {
                if(response.isSuccessful){
                    recyclerListData.postValue(response.body())
                } else {

                }
            }
        })
    }
}