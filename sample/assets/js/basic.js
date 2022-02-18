var base_url        = $('#base').val();             //  http://localhost/cordova/
var url_target      = base_url+'core_quality/';     //  http://localhost/cordova/core_quality/
var segment_1       = "";
var segment_2       = "";

// alert("Whyy...");

$("#breadcrumb_home_quality").click(function(){
    $.ajax({
        url     : "http://localhost/cordova/core_quality/getBreadcrumbData/",
        type    : "POST",               // Type of request to be send, called as method
        cache   : false,                // To unable request pages to be cached
        /*beforeSend: function(){
            $("#modal-loading").slideToggle("slow");
        },*/
        success : function(data)        // A function to be called if request succeeds
        {
            // alert(data);
            var hasil = JSON.parse(data);

            $("#breadcrumb_menu").empty();
            $("#breadcrumb_menu").prepend('<li id="home" class="breadcrumb-item"><a href="core_home"><i class="fas fa-home"></i></a></li><li class="breadcrumb-item active" aria-current="page">'+hasil+'</li><input type="hidden" id="base" value="'+base_url+'">');
        }
    });
});

$(".breadcrumb_quality").click(function(){
    base_url        = $('#base').val();                     //  http://localhost/cordova/
    segment_2       = $(this).attr('href');                 //  ex. core_quality_mss
    segment_1       = _repack_segment(segment_2);           //  ex. core_quality
    dest_url        = base_url+segment_2+"/getBreadcrumbData/"; 

    // alert(document.getElementsByClassName("breadcrumb_mss_quality")[0].getAttribute("href"));
    // alert(document.getElementsByClassName("breadcrumb_mss_quality")[0].getAttribute("href"));
    // alert(document.getElementById("kiri-fix-mid").getAttribute("href"));

    $.ajax({
        url     : dest_url,
        type    : "POST",               // Type of request to be send, called as method
        cache   : false,                // To unable request pages to be cached
        /*beforeSend: function(){
            $("#modal-loading").slideToggle("slow");
        },*/
        success : function(data)        // A function to be called if request succeeds
        {
            // alert(data);
            var hasil = JSON.parse(data);

            $("#breadcrumb_menu").empty();
            $("#breadcrumb_menu").prepend('<li id="home" class="breadcrumb-item"><a href="core_home"><i class="fas fa-home"></i></a></li><li id="second" class="breadcrumb-item"><a href="'+segment_1+'">'+hasil.upper_title+'</li></a></li><li class="breadcrumb-item active" aria-current="page">'+hasil.menu_title+'</li><input type="hidden" id="base" value="'+base_url+'">');
        }
    });
});

$("#second").click(function(){
    segment       = $(this).attr('href');                 //  ex. core_quality

    // alert("Bacod 1");
    $.ajax({
        url     : "http://localhost/cordova/core_quality/getBreadcrumbData/",
        type    : "POST",               // Type of request to be send, called as method
        cache   : false,                // To unable request pages to be cached
        success : function(data)        // A function to be called if request succeeds
        {
            alert(data);
            var hasil = JSON.parse(data);

            $("#breadcrumb_menu").empty();
            $("#breadcrumb_menu").prepend('<li id="home" class="breadcrumb-item"><a href="core_home"><i class="fas fa-home"></i></a></li><li class="breadcrumb-item active" aria-current="page">'+hasil+'</li><input type="hidden" id="base" value="'+base_url+'">');
        }
    });
    // alert("Bacod 2");
});

function _repack_segment(segment){
    var temp        = segment.split("_");   //  aaa_bbb_ccc =>  ex. core_quality_mss
    temp.pop();
    var new_segment = temp.join("_");       //  aaa_bbb     =>  ex. core_quality

    return new_segment;
}
