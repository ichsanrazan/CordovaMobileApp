alert("Anone");
var url_target  = 'http://localhost/dashboard_monitoring/core_alarm/';
$(document).ready(function() {
    _init_Table_Alarm();
});

function _init_Table_Alarm(){
    alert("Need Info");
    $.ajax({
        url     : url_target+"js_set_datatable/mss",
        type    : "POST",             // Type of request to be send, called as method
        cache   : false,             // To unable request pages to be cached
        success : function(data)   // A function to be called if request succeeds
        {
            alert(data);
            var hasil = JSON.parse(data);

            table_scr = $('#t_alarm_mss').DataTable( {
                scrollX     : true,
                scrollY     : true,
                responsive  : true,
                data        : hasil,
                select      : true,
                columns     : [
                    { title: "NE Name" 		, data : "ne_name" },
                    { title: "Alarm Name" 	, data : "alarm_name" },
                    { title: "Severity" 	, data : "severity" },
                    { title: "Occur Time" 	, data : "occur_time" },
                    { title: "Clear Time" 	, data : "clear_time" },
                    { title: "Site Name" 	, data : "detail" },
                    { title: "Alarm ID" 	, data : "alarm_id" }
                    /*{ data: "ne_name" },
                	{ data: "alarm_name"},
                	{ data: "severity"},
                	{ data: "occur_time"},
                	{ data: "clear_time"},
                	{ data: "detail"},
                	{ data: "alarm_id"}*/
                ]
            });
        }
    });
};