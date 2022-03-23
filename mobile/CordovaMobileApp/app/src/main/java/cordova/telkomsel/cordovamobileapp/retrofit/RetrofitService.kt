package cordova.telkomsel.cordovamobileapp.retrofit

import cordova.telkomsel.cordovamobileapp.activityLog.model.Activity
import cordova.telkomsel.cordovamobileapp.activityLog.model.ActivityList
import cordova.telkomsel.cordovamobileapp.activityLog.model.ActivityResponse
import cordova.telkomsel.cordovamobileapp.activityLog.model.PICList
import cordova.telkomsel.cordovamobileapp.authentication.model.UserRequest
import cordova.telkomsel.cordovamobileapp.authentication.model.UserResponse
import cordova.telkomsel.cordovamobileapp.activityLog.model.*
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    @GET("activity/read.php")
    fun getActivityList(): Call<ActivityList>

    @POST("activity/create.php")
    @Headers("Content-Type: application/json")
    fun createActivity(@Body params: Activity): Call<ActivityResponse>

    @PATCH("activity/update.php")
    @Headers("Content-Type: application/json")
    fun updateActivity(@Body params: Activity): Call<ActivityResponse>

    @HTTP(method = "DELETE", path = "activity/delete.php", hasBody = true)
    @Headers("Content-Type: application/json")
    fun deleteActivity(@Body params: ActivityDelete): Call<ActivityResponse>

    @GET("pic/read.php")
    fun getPICList(): Call<PICList>

    @POST("pic/create.php")
    @Headers("Content-Type: application/json")
    fun createPIC(@Body params: PIC): Call<PICResponse>

    @POST("login.php")
    fun login(
        @Body userRequest: UserRequest
    ): Call<UserResponse>
}