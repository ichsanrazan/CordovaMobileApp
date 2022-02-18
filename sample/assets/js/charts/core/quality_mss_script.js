var base_url        = $('#base').val();
var url_target      = base_url+'core_quality_mss/';

var chart           = new Array();
var destination     = ["qm_mss_scr", "qm_mss_ccr", "qm_mss_csfbr", "qm_mss_hosr", "qm_mss_locsr", "qm_mss_pagingsr", "qm_mss_authsr"];
var target_function = ["js_getdata_scr", "js_getdata_ccr", "js_getdata_csfb", "js_getdata_hosr", "js_getdata_locsr", "js_getdata_pagingsr", "js_getdata_authsr"];
var func_controller = 
    [
        "js_routes_qual_mss_1", "js_routes_qual_mss_2", "js_routes_qual_mss_3", 
        "js_routes_qual_mss_4", "js_routes_qual_mss_5", "js_routes_qual_mss_6", 
        "js_routes_qual_mss_7"
    ];
var chart_title     = ["SCR", "CCR", "CS-Fall Back", "Handover SR", "Location Update SR", "Paging SR", "Authentication SR"];

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