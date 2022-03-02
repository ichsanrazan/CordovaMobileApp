package cordova.telkomsel.cordovamobileapp.activityLog.model

data class ActivityList(val data: List<Activity>)
data class Activity(val crq_date: String?,
                    val crq_subject: String?,
                    val pic_reporter: String?,
                    val category: String?,
                    val crq_no: String?,
                    val crq_activity: String?,
                    val crq_serviceimp: String?,
                    val crq_pic: String?,)
data class ActivityResponse(val message: String?)
