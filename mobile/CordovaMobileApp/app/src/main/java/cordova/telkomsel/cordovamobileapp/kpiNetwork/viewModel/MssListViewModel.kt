package cordova.telkomsel.cordovamobileapp.kpiNetwork.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cordova.telkomsel.cordovamobileapp.kpiNetwork.model.MssList
import cordova.telkomsel.cordovamobileapp.retrofit.RetrofitInstance
import cordova.telkomsel.cordovamobileapp.retrofit.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MssListViewModel: ViewModel() {

    lateinit var recyclerListData: MutableLiveData<MssList>

    init{
        recyclerListData = MutableLiveData()
    }

    fun getMssListObservableData() : MutableLiveData<MssList> {
        return recyclerListData
    }

    fun getMssList(){
        val retroInstance = RetrofitInstance.getRetroInstanceNetwork().create(RetrofitService::class.java)
        val call = retroInstance.getMssList()
        call.enqueue(object: Callback<MssList> {
            override fun onFailure(call: Call<MssList>, t: Throwable) {
                recyclerListData.postValue(null)
            }

            override fun onResponse(call: Call<MssList>, response: Response<MssList>) {
                if(response.isSuccessful){
                    recyclerListData.postValue(response.body())
                } else {
                    recyclerListData.postValue(null)

                }
            }
        })
    }
}