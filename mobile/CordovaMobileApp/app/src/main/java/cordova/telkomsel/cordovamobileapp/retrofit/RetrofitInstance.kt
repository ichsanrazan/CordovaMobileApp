package cordova.telkomsel.cordovamobileapp.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object{
        //val baseUrl = "http://192.168.1.5/mobileapp/mobile-app/restapi_activitylog/api/"
        val baseUrl = "http://192.168.18.179:8080/restapi_activitylog/api/"

        //Login
        //val baseUrl2 = "http://192.168.1.5/mobileapp/mobile-app/restapi_authentication/"
        val baseUrl2 = "http://192.168.18.179:8080/restapi_authentication/"

        fun getRetroInstance(): Retrofit {
            val logging = HttpLoggingInterceptor()
            logging.level = (HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
            client.addInterceptor(logging)

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun getRetroClientInstance(): Retrofit {
            val gson = GsonBuilder().setLenient().create()

            return Retrofit.Builder()
                .baseUrl(baseUrl2)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
    }
}