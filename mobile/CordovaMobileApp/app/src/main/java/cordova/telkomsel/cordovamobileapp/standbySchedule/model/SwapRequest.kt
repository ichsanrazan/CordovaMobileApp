package cordova.telkomsel.cordovamobileapp.standbySchedule.model

data class SwapRequestList(val data: List<SwapRequest>)
data class SwapRequest(val id: Int?,
                       val date_from: String?,
                       val pic_from: String?,
                       val date_to: String?,
                       val pic_to: String?,
                       val status: String?,)
data class RequestResponse(val message: String?)
data class RequestDelete(val id:Int?)