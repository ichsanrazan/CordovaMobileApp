var base_url        = $('#base').val();
var url_target      = base_url+'core_capacity_ggsn/';

var chart           = new Array();
var destination     = 
    [
        "cm_ggsn_pdp", "cm_ggsn_bearer", "cm_ggsn_pdp_bearer", 
        "cm_ggsn_throughput", "cm_ggsn_throughput_jabo",
        "cm_ggsn_max_cpu", "cm_ggsn_avg_cpu", "cm_ggsn_memory"
    ];
var target_function = 
    [
        "js_getdata_pdp", "js_getdata_bearer", "js_getdata_pdp_bearer",
        "js_getdata_throughput", "js_getdata_throughput_jabo",
        "js_getdata_max_cpu", "js_getdata_avg_cpu", "js_getdata_memory"
    ];
var func_controller =
    [
        "js_routes_cap_ggsn_1", "js_routes_cap_ggsn_2", "js_routes_cap_ggsn_3",
        "js_routes_cap_ggsn_4", "js_routes_cap_ggsn_5", 
        "js_routes_cap_ggsn_6", "js_routes_cap_ggsn_7", "js_routes_cap_ggsn_8"
    ];
var chart_title     = 
    [
        "PDP Activation", "Active Bearer", "Total PDP and Bearer",
        "Total Throughput", "Total Throughput Jabotabek",
        "MAX CPU Load", "AVG CPU Load", "Memory Usage"
    ];

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