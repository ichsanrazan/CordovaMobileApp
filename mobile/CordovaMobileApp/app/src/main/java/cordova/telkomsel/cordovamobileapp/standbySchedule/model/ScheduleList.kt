package cordova.telkomsel.cordovamobileapp.standbySchedule.model

data class ScheduleList(val data: List<Schedule>)
data class Schedule(val id: Int?,
                    val date: String?,
                    val pic: String?,
                    val division: String?)