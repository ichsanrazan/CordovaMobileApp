package cordova.telkomsel.cordovamobileapp.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstance {
    companion object{
        //val baseUrl = "http://192.168.1.8/mobileapp/mobile-app/restapi_activitylog/api/"
        //val baseUrl = "http://192.168.18.179:8080/restapi_activitylog/api/"
        val baseUrl = "https://restapi-activitylog.000webhostapp.com/restapi_activitylog/api/"

        //Login

        //val baseUrl2 = "http://192.168.1.8/mobileapp/mobile-app/restapi_authentication/"
        //val baseUrl2 = "http://192.168.18.179:8080/restapi_authentication/"
        val baseUrl2 = "https://restapi-activitylog.000webhostapp.com/restapi_authentication/"


        //val baseUrl3 = "http://192.168.18.179:8080/api_standby/"
        //val baseUrl3 = "http://192.168.1.8/mobileapp/mobile-app/api_standby/"
        val baseUrl3 = "https://api-standby.000webhostapp.com/api_standby/"

        //val baseUrl4 = "http://192.168.1.8/mobileapp/mobile-app/api_network/"
        val baseUrl4 = "http://192.168.18.179:8080/api_network/"

        fun getRetroInstance(): Retrofit {
            val logging = HttpLoggingInterceptor()
            logging.level = (HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
            client.addInterceptor(logging)
            //client.callTimeout(5, TimeUnit.SECONDS)

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun getRetroInstanceSchedule(): Retrofit {
            val logging = HttpLoggingInterceptor()
            logging.level = (HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
            client.addInterceptor(logging)
            //client.callTimeout(5, TimeUnit.SECONDS)

            return Retrofit.Builder()
                .baseUrl(baseUrl3)
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

        fun getRetroInstanceNetwork(): Retrofit {
            val logging = HttpLoggingInterceptor()
            logging.level = (HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
            client.addInterceptor(logging)
            //client.callTimeout(5, TimeUnit.SECONDS)

            return Retrofit.Builder()
                .baseUrl(baseUrl4)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}