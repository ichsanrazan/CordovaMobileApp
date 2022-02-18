var base_url        = $('#base').val();
var url_target      = base_url+'core_quality_sgsn/';

var chart           = new Array();
var destination     = 
    [
        "qm_sgsn_2g_attach", "qm_sgsn_2g_inter_rau", "qm_sgsn_2g_intra_rau", 
        "qm_sgsn_2g_pdp_context", "qm_sgsn_3g_attach", "qm_sgsn_3g_inter_rau", 
        "qm_sgsn_3g_intra_rau", "qm_sgsn_3g_pdp_context", "qm_sgsn_4g_combine_attach", 
        "qm_sgsn_4g_inter_tau", "qm_sgsn_4g_intra_tau", "qm_sgsn_4g_pdn_connect", 
        "qm_sgsn_4g_service_req", "qm_sgsn_4g_default_bearer"
    ];
var target_function = 
    [
        "js_getdata_2g_attach", "js_getdata_2g_inter_rau", "js_getdata_2g_intra_rau", 
        "js_getdata_2g_pdp_context", "js_getdata_3g_attach", "js_getdata_3g_inter_rau", 
        "js_getdata_3g_intra_rau", "js_getdata_3g_pdp_context", "js_getdata_4g_combine_attach",
        "js_getdata_4g_inter_tau", "js_getdata_4g_intra_tau", "js_getdata_4g_pdn_connect", 
        "js_getdata_4g_service_req", "js_getdata_4g_default_bearer"
        
    ];
var func_controller = 
    [
        "js_routes_qual_sgsn_2g_1", "js_routes_qual_sgsn_2g_2", "js_routes_qual_sgsn_2g_3",
        "js_routes_qual_sgsn_2g_4", "js_routes_qual_sgsn_3g_1", "js_routes_qual_sgsn_3g_2", 
        "js_routes_qual_sgsn_3g_3", "js_routes_qual_sgsn_3g_4", "js_routes_qual_sgsn_4g_1", 
        "js_routes_qual_sgsn_4g_2", "js_routes_qual_sgsn_4g_3", "js_routes_qual_sgsn_4g_4", 
        "js_routes_qual_sgsn_4g_5", "js_routes_qual_sgsn_4g_6"
    ];
var chart_title     = 
    [
        "2G-Attach SR", "2G-INTER RAU SR", "2G-INTRA RAU SR", "2G-PDP Context SR",
        "3G-Attach SR", "3G-INTER RAU SR", "3G-INTRA RAU SR", "3G-PDP Context SR",
        "4G-Combine Attach SR", "4G-INTER TAU SR", "4G-INTRA TAU SR", 
        "4G-PDN Connect SR", "4G-Service Request SR", "4G-Default Bearer SR"
    ];

$(document).ready(function() {
    for (var i = 0; i < func_controller.length; i++) {
        /*chart[i].destroy();
        chart[i] = undefined;*/
        display_quality_sgsn(chart[i], destination[i], func_controller[i], chart_title[i]);
    }

    $.datepicker.setDefaults({
        dateFormat: 'yy-mm-dd',
        onSelect: function(dateText) {
            this.onchange();
            this.onblur();
        }
    });
});

$.datepicker.setDefaults({
        dateFormat: 'yy-mm-dd',
        onSelect: function(dateText) {
            this.onchange();
            this.onblur();
        }
    });

    $("#test2").click(function(){
        for (var i = 0; i < chart.length; i++) {
            alert("The paragraph was clicked. 1");
            chart[i].destroy();
        }
    });

    /*$("#test2" ).on("click", function() {
        alert("test2 1");
    });*/

function display_quality_sgsn(highchart, chart_id, func_controller, chart_title){
    console.log(highchart);
    highchart = new Highcharts.Chart({
        chart: {
            renderTo: chart_id,
            alignTicks: false,
            // type    : 'line',
            type    : 'spline',
            zoomType: 'x',
            events  : {
                load: function (){
                    $.ajax({
                        // url     : "<?= base_url(); ?>core_quality_sgsn/js_getdata_pdpsr",
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
                        count: 3,
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
                    /*{
                        type: 'month',
                        count: 1,
                        text: 'Bln'
                    },
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

$("ul.nav-tabs a").click(function (e) {
  e.preventDefault();  
    $(this).tab('show');
});

/*function display_quality_mss(highchart, chart_id, func_controller, chart_title){
    var chart = Highcharts.chart(chart_id[i], {
        chart: {
            type    : 'spline',
            zoomType: 'x',
            events  : {
                load: function (){
                    $.ajax({
                        url     : func_controller,
                        type    : "POST",               // Type of request to be send, called as method
                        cache   : false,                // To unable request pages to be cached
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
                        }
                    }); 
                }
            }
        },
        title: {
            text: 'Fruit Consumption'
        },
        xAxis: {
            categories: ['Apples', 'Bananas', 'Oranges']
        },
        yAxis: {
            title: {
                text: 'Fruit eaten'
            }
        },
        series: [{
            name: 'Jane',
            data: [1, 0, 4]
        }, {
            name: 'John',
            data: [5, 7, 3]
        }]
    });

}*/