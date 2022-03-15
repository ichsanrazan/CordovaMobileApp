package cordova.telkomsel.cordovamobileapp.authentication.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserResponse {
    @SerializedName("message")
    @Expose
    var message: String? = null
}