package cordova.telkomsel.cordovamobileapp.kpiNetwork.model

data class MssList(val data: List<Mss>)
data class Mss(val id: Int?,
               val date_time: String?,
               val mss: String?,
               val scr: String?,
               val ccr: String?,)
data class MssResponse(val message: String?)