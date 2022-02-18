var base_url        = $('#base').val();
var url_target      = base_url+'core_quality_hlr/';

var chart           = new Array();
var destination     = ["qm_hlr_gprs_lup", "qm_hlr_lup", "qm_hlr_sms", "qm_hlr_sri", "qm_hlr_ulr_ula", "qm_hlr_air_aia"];
var target_function = ["js_getdata_gprs_lup", "js_getdata_lup", "js_getdata_sms", "js_getdata_sri", "js_getdata_ulr_ula", "js_getdata_air_aia"];
var func_controller = 
    [
        "js_cont_qual_hlr_1", "js_cont_qual_hlr_2", "js_cont_qual_hlr_3", 
        "js_cont_qual_hlr_4", "js_cont_qual_hlr_5", "js_cont_qual_hlr_6"
    ];
var chart_title     = ["GPRS Loc. Update", "Location Update", "SMS", "SRI", "ULR ULA", "AIR AIA"];

/*Highcharts.setOptions({
    colors: ['#8E3200', '#245064', '#797039', '#00FFFF', '#FF8C00', '#1E90FF', '#FFD700', '#FF00FF', '#4B0082', '#00FF00'],
    time: {
        timezone: 'Indonesia/Jakarta'
    },
});*/

$(document).ready(function() {
    for (var i = 0; i < func_controller.length; i++) {
        display_quality_mss(chart[i], destination[i], func_controller[i], chart_title[i]);
    }

    $.datepicker.setDefaults({
        dateFormat: 'yy-mm-dd',
        onSelect: function(dateText) {
            this.onchange();
            this.onblur();
        }
    });
});

function display_quality_mss(highchart, chart_id, func_controller, chart_title){
    highchart = new Highcharts.Chart({
        chart: {
            renderTo: chart_id,
            // type    : 'line',
            type    : 'spline',
            zoomType: 'x',
            events  : {
                load: function (){
                    $.ajax({
                        // url     : url_target+target_function,
                        url     : func_controller,
                        type    : "POST",               // Type of request to be send, called as method
                        cache   : false,                // To unable request pages to be cached
                        /*beforeSend: function(){
                            $("#modal-loading").slideToggle("slow");
                        },*/
                        success : function(data)        // A function to be called if request succeeds
                        {
                            // alert(data);
                            var hasil = JSON.parse(data);
                            
                            for (var i = 0; i < hasil.list_ne.length; i++) {
                                highchart.addSeries({
                                    name: hasil.list_ne[i],
                                    data: hasil.data_chart[i]
                                });
                            }
                            /*$('#modal-loading').hide();  */    
                        }
                    }); 
                }
            }
        },
        plotOptions: {
            series: {
                marker: {
                    enabled: false
                }
            }
        },
        title: {
            text: chart_title,
            x: -20 //center
        },   
        xAxis: {
            type: 'datetime',
            labels: { 
                format: '{value:%e %b %Y <br> (%H:%M:%S)}',
            }
        },
        yAxis: {
            min: 0, 
            max: 100,
            // tickAmount: 6,
            // tickInterval: 10
        },
        tooltip:{
            shared: true,
            useHTML: true,
            xDateFormat: '%A, %b %e (%H:%M)'
        },
        rangeSelector:{
            selected: 1,
            inputDateFormat: '%Y-%m-%d',
            inputEditDateFormat: '%Y-%m-%d',
            allButtonsEnabled: true,
            enabled:true,
            inputEnabled:true,
            buttons: 
                [
                    {
                        type: 'hour',
                        count: 1,
                        text: 'Jam'
                    },
                    {
                        type: 'hour',
                        count: 24,
                        text: 'Hari'
                    },
                    {
                        type: 'day',
                        count: 7,
                        text: 'Mng'
                    },
                    {
                        type: 'month',
                        count: 1,
                        text: 'Bln'
                    }/*,
                    {
                        type: 'year',
                        count: 1,
                        text: 'Thn'
                    },
                    {
                        type: 'all',
                        text: 'All'
                    }*/
                ]
        }
    }, function(chart) {
        $('input.highcharts-range-selector', $('#'+chart_id)).datepicker();
    });
}