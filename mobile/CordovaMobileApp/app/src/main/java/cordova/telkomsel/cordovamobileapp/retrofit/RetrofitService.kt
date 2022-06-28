package cordova.telkomsel.cordovamobileapp.retrofit

import cordova.telkomsel.cordovamobileapp.activityLog.model.Activity
import cordova.telkomsel.cordovamobileapp.activityLog.model.ActivityList
import cordova.telkomsel.cordovamobileapp.activityLog.model.ActivityResponse
import cordova.telkomsel.cordovamobileapp.activityLog.model.PICList
import cordova.telkomsel.cordovamobileapp.authentication.model.UserRequest
import cordova.telkomsel.cordovamobileapp.authentication.model.UserResponse
import cordova.telkomsel.cordovamobileapp.activityLog.model.*
import cordova.telkomsel.cordovamobileapp.kpiNetwork.model.*
import cordova.telkomsel.cordovamobileapp.standbySchedule.model.*
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    @GET("activity/read.php")
    fun getActivityList(): Call<ActivityList>

    @GET("activity/search.php")
    fun searchActivity(@Query("name") searchText: String): Call<ActivityList>

    @GET("activity/filter.php")
    fun filterActivity(@Query("start_date") start_date: String,
                       @Query("end_date") end_date: String,
                       @Query("subject") subject: String,
                       @Query("category") category: String) : Call<ActivityList>

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

    @GET("schedule/read.php")
    fun getScheduleList(): Call<ScheduleList>

    @POST("login.php")
    fun login(@Body userRequest: UserRequest): Call<UserResponse>

    @POST("request/create.php")
    @Headers("Content-Type: application/json")
    fun createRequest(@Body params: SwapRequest): Call<RequestResponse>

    @GET("request/data.php")
    fun getSwapRequestList(): Call<SwapRequestList>

    @GET("request/read.php")
    fun readRequest(@Query("name") searchText: String): Call<SwapRequestList>

    @GET("request/readfrom.php")
    fun notificationRequest(@Query("name") searchText: String): Call<SwapRequestList>

    @HTTP(method = "DELETE", path = "request/delete.php", hasBody = true)
    @Headers("Content-Type: application/json")
    fun deleteRequest(@Body params: RequestDelete): Call<RequestResponse>

    @PATCH("request/update.php")
    @Headers("Content-Type: application/json")
    fun updateRequest(@Body params: SwapRequest): Call<RequestResponse>

    @GET("api/mss.php")
    fun getMssList(): Call<MssList>

    @GET("api/ggsn.php")
    fun getGgsnList(): Call<GgsnList>

}