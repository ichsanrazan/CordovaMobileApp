package cordova.telkomsel.cordovamobileapp.authentication.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserResponse {
    @SerializedName("full_name")
    @Expose
    var full_name: String? = null

    @SerializedName("status")
    @Expose
    var status: Int? = null
}