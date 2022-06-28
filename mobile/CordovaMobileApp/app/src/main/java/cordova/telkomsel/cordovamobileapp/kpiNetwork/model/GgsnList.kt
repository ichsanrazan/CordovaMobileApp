package cordova.telkomsel.cordovamobileapp.kpiNetwork.model

data class GgsnList(val data: List<Ggsn>)
data class Ggsn(val id: Int?,
               val date_time: String?,
               val ggsn: String?,
               val scr: String?,
               val ccr: String?,)
data class GgsnResponse(val message: String?)