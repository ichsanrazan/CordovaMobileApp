package cordova.telkomsel.cordovamobileapp.retrofit

import cordova.telkomsel.cordovamobileapp.activityLog.model.Activity
import cordova.telkomsel.cordovamobileapp.activityLog.model.ActivityList
import cordova.telkomsel.cordovamobileapp.activityLog.model.ActivityResponse
import cordova.telkomsel.cordovamobileapp.activityLog.model.PICList
import cordova.telkomsel.cordovamobileapp.authentication.model.UserRequest
import cordova.telkomsel.cordovamobileapp.authentication.model.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface RetrofitService {

    @GET("activity/read.php")
    fun getActivityList(): Call<ActivityList>

    @POST("activity/create.php")
    @Headers("Content-Type: application/json")
    fun createActivity(@Body params: Activity): Call<ActivityResponse>

    @GET("pic/read.php")
    fun getPICList(): Call<PICList>

    @POST("login.php")
    fun login(
        @Body userRequest: UserRequest
    ): Call<UserResponse>

}