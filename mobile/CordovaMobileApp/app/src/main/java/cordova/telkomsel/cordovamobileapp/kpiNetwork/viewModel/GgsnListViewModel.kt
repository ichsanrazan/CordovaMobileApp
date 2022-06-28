package cordova.telkomsel.cordovamobileapp.kpiNetwork.viewModel

import androidx.lifecycle.MutableLiveData
import cordova.telkomsel.cordovamobileapp.kpiNetwork.model.GgsnList
import cordova.telkomsel.cordovamobileapp.retrofit.RetrofitInstance
import cordova.telkomsel.cordovamobileapp.retrofit.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GgsnListViewModel {

    lateinit var recyclerListData: MutableLiveData<GgsnList>

    init{
        recyclerListData = MutableLiveData()
    }

    fun getGgsnListObservableData() : MutableLiveData<GgsnList> {
        return recyclerListData
    }

    fun getGgsnList(){
        val retroInstance = RetrofitInstance.getRetroInstanceNetwork().create(RetrofitService::class.java)
        val call = retroInstance.getGgsnList()
        call.enqueue(object: Callback<GgsnList> {
            override fun onFailure(call: Call<GgsnList>, t: Throwable) {
                recyclerListData.postValue(null)
            }

            override fun onResponse(call: Call<GgsnList>, response: Response<GgsnList>) {
                if(response.isSuccessful){
                    recyclerListData.postValue(response.body())
                } else {
                    recyclerListData.postValue(null)

                }
            }
        })
    }
}