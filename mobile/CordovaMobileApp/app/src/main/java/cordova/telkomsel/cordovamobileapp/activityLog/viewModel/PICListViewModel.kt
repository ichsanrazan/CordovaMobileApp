package cordova.telkomsel.cordovamobileapp.activityLog.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cordova.telkomsel.cordovamobileapp.activityLog.model.PICList
import cordova.telkomsel.cordovamobileapp.retrofit.RetrofitInstance
import cordova.telkomsel.cordovamobileapp.retrofit.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PICListViewModel: ViewModel() {
    lateinit var picListData: MutableLiveData<PICList>

    init{
        picListData = MutableLiveData()
    }

    fun getPICListObservableData(): MutableLiveData<PICList>{
        return picListData
    }

    fun getPICList(){
        val retroInstance = RetrofitInstance.getRetroInstance().create(RetrofitService::class.java)
        val call = retroInstance.getPICList()
        call.enqueue(object: Callback<PICList> {
            override fun onFailure(call: Call<PICList>, t: Throwable) {
                picListData.postValue(null)
            }

            override fun onResponse(call: Call<PICList>, response: Response<PICList>) {
                if(response.isSuccessful){
                    picListData.postValue(response.body())
                } else {

                }
            }
        })
    }
}