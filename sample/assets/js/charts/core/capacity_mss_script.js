var base_url        = $('#base').val();
var url_target      = base_url+'core_capacity_mss/';

var chart           = new Array();
var destination     = 
    [
        "cm_mss_scc", "cm_mss_iucs", "cm_mss_nbip", 
        "cm_mss_aoip", "cm_mss_pcmb", "cm_mss_csfb",
        "cm_mss_bhca", "cm_mss_cpuload", "cm_mss_vlr"
    ];
var target_function = 
    [
        "js_getdata_scc", "js_getdata_iucs", "js_getdata_nbip",
        "js_getdata_aoip", "js_getdata_pcmb", "js_getdata_csfb",
        "js_getdata_bhca", "js_getdata_cpuload", "js_getdata_vlr"
    ];
var func_controller =
    [
        "js_routes_cap_mss_1", "js_routes_cap_mss_2", "js_routes_cap_mss_3",
        "js_routes_cap_mss_4", "js_routes_cap_mss_5", "js_routes_cap_mss_6",
        "js_routes_cap_mss_7", "js_routes_cap_mss_8", "js_routes_cap_mss_9"
    ];
var chart_title     = 
    [
        "SCC", "IuCSoIP", "NBIP", "AoIP", "PCMB",
        "CSFB", "BHCA", "CPU Load", "VLR"
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
            /*formatter: function () {
                return this.points.reduce(function (s, point) {
                    return s + '<br/>' + point.series.name + ': ' + point.y;
                }, '<b>' + this.x + '</b>');
            },
            shared: true*/

            /*formatter: function () {
                return this.points.reduce(function (s, point) {
                    return s + '<br/>' + point.series.name + ': ' + point.y + "[Note : testing]";
                }, '<b>' + Highcharts.dateFormat('%A, %b %e (%H:%M)', this.x) + '</b>');
            },
            shared: true*/
            

            shared: true,  // True tooltips semua, false tooltips per line aja
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