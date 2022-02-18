// var base_url    = $('#base').val();
// var url_target  = base_url+'core/';


// $(document).ready(function() {
//     _init_Alarm();
// });

// function _init_Alarm(){
//     $.ajax({
//         url     : url_target+"js_get_alert_number",
//         type    : "POST",             // Type of request to be send, called as method
//         cache   : false,             // To unable request pages to be cached
//         success : function(data)   // A function to be called if request succeeds
//         {
//             // alert(data);
//             var hasil = JSON.parse(data);

//             $("#total_alert_alarm").text(hasil.sgsn);
//             $("#total_alert_alarm").css('color', 'red');
//         }
//     });
// };

var home = document.getElementById("divhome");
var btn = home.getElementsByClassName("btn");
for (var i = 0; i < btn.length; i++ ){
    btn[i].addEventListener("click", function(){
        var current = document.getElementsByClassName("active");
        if (current.length > 0) {
            current[0].className = current[0].className.replace(" active", "");
        }
        this.className += " active";
    });
}

$('a[data-toggle="tab"]').on('shown.bs.tab', function(e){
   $($.fn.dataTable.tables(true)).DataTable()
      .scroller.measure();
});