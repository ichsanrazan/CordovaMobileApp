package cordova.telkomsel.cordovamobileapp.activityLog.model

data class PICList(val data: List<PIC>)
data class PIC(val company: String?,
               val full_name: String?,
               val phone_number: String?)
data class PICResponse(val message: String?)