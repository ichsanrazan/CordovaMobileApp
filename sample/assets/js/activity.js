var input_pic_compErr = true,
    input_pic_nameErr = true,
    input_inc_idErr = true,
    input_inc_nameErr = true,
    input_act_nameErr = true,
    input_act_svcErr = true,
    input_act_crqErr = true;

$('.modal').on('shown.bs.modal', function(e){
    $('#home-test').prop("disabled", true);
});

$(document).ready(function () {

    $('#modal_create_submit').prop('disabled',true)
    'use strict';

    $('#modal_templates_pic_submit').prop('disabled',true)
    'use strict';

    $('#modal_inc_submit').prop('disabled',true)
    'use strict';

    $.ajax({
        url     : "activity/js_set_datatable_activity",
        type    : "POST",
        cache   : false,
        success : function(data)
        {
            var hasil = JSON.parse(data);

            table_scr = $('#t_activity').DataTable( {
                scrollX     : true,
                scrollY     : 450,
                responsive  : true,
                data        : hasil,
                select      : true,
                order       : [[ 0,"desc" ]],
                columns     : [
                    { title: "Date"      , data : "crq_date" },
                    { title: "Subject"     , data : "crq_subject" },
                    { title: "PIC Activity"   , data : "pic_reporter" },
                    { title: "CRQ/INC/IM"   , data : "crq_no" },
                    { title: "Category"   , data : "category" },
                    { title: "Description"     , data : "crq_activity" }
                ]
            });
        }
    });

    modalRadioCheck();
    modalValidation();
    modalSelectPicPartner();
    modalfilterOptionComp();
    modalcreateOptionComp();
    modalfilterOptionName();
    modalcreateOptionName();
    modalPicSubmit();
    modalActivitySubmit();
    modalIncSubmit();

});

let output = "";

$("#modal_bc_play").click(function(){
    var inputdt = $('#input_bc_date').val().split("-");
    var input = inputdt[0] + "-" + inputdt[1] + "-" + inputdt[2];
    if($('#pick_sub_false').is(':checked')) {
        var subject = 'all';
    } else if($('#pick_sub_true').is(':checked') && $('#picksubject').val() == null){
        var subject = 'all';
    } else {
        var subject = $('#picksubject').val();
    }
    console.log(subject);
    console.log(input);
    $.ajax({
        url: 'activity/broadcast_play',
        type: 'POST',
        data: {
            'input': input,
            'subject': subject
        },
        dataType: 'json',
        success: function(data) {
            console.log(data);
            if(data.status == 'NA') {
                alert('Tidak ada activity pada tanggal ' + data.crq_date + ' (subject: ' + data.subject + ')');
            } else {
                output += "Info Activity CDSO Jabotabek " + data[0].crq_date + ": \n";
                output += "============================= \n";
                data.forEach(addstring);
                document.getElementById("bc_text").innerHTML = output;
            }
            output = "";
        }
    });
});

function addstring(item,index) {
    let crq_pic = item.crq_pic;
    const pic_list = crq_pic.split(",");
    output += index + 1 + "). Activity Name: " + item.crq_activity + " (" + item.crq_no + ") \n";
    if (item.crq_serviceimp == "null") {
        output += "Impact Service: None \n";
    } else {
        output += "Impact Service: Yes (" + item.crq_serviceimp + ") \n";
    }
    if (item.crq_pic == "" || item.crq_pic == null ) {
        output += "PIC: " + item.pic_reporter + " \n";
        output += "----------------------------------------------------------- \n";
    } else {
        output += "PIC CDSO: " + item.pic_reporter + " \n";
        output += "PIC Activity: \n";
        pic_list.forEach(checkpiclist);
        output += "----------------------------------------------------------- \n";
    }
}

function checkpiclist(item) {
    let eachpic = item.split("|");
    if(eachpic.length > 0) {
        if(eachpic.length == 2) {
            output += eachpic[0] + " - " + eachpic[1] + "\n";
        } else if(eachpic.length == 3) {
            output += eachpic[0] + " - " + eachpic[1] + " (" + eachpic[2] + ") \n";
        }
    } else {
        output += "\n";
    }
}

$("#modal_bc_copy").click(function(){
    $('#copied_bc').show();
    var $temp = $("<textarea>");
    $("body").append($temp);
    $temp.val($('#bc_text').text()).select();
    document.execCommand("copy");
    $temp.remove();
    setTimeout(function(){
        $('#copied_bc').hide();
    }, 1000);
});

function modalresetForm() {
    input_act_nameErr = input_act_svcErr = input_act_crqErr = true;
    $('.input_act_name').siblings('.error').text('').fadeOut().parent().removeClass('hasError');
    $('.input_act_crq').siblings('.error').text('').fadeOut().parent().removeClass('hasError');
    $('.input_inc_id').siblings('.error').text('').fadeOut().parent().removeClass('hasError');
    $('.input_inc_name').siblings('.error').text('').fadeOut().parent().removeClass('hasError');
    $('.input_svc').siblings('.error').text('').fadeOut().parent().removeClass('hasError');
    $('.required_form').val('');
    $('.radio_default').prop('checked', true);
    $('.radio_nodefault').prop('checked', false);
    $('.form_prompt').css('display','none');
    $('#selpicpartner').css('display','none');
    $('#tableAdd').css('display','none');
    $('#add_pic').prop('disabled', true);
    $('#modal_create_submit').prop("disabled", true);
    $('#modal_inc_submit').prop("disabled", true);
    pairdataArray = [];
    verifiedDataArray = [];
}

let pairdata;
let pairdataArray = [];
let verifiedDataArray = [];

function modalcheckPic(company,name_phone) {
    let objpic = name_phone.split("|",2);
    let name = objpic[0];
    let phone = objpic[1];
    pairdata = company+"|"+name+"|"+phone;
    if(pairdataArray.indexOf(pairdata) == -1) {
        pairdataArray.push(pairdata);
        verifiedDataArray.push({
            company,
            name,
            phone,
            pairdata
        });
        modalAddPic(company,name,phone,pairdata);
    } else {
        alert('PIC sudah terdaftar');
    }
}

let idnum = 1;
function modalAddPic(company,name,phone,pairdata) {
    let tr = "<tr>";
    tr += "<td>" + idnum + "</td>";
    tr += "<td>" + company + "</td>";  
    tr += "<td>" + name + "</td>";
    tr += "<td>" + phone + "</td>";
    tr += "<td>" + "<a href='#' onclick='deleteonepic(this.id)' class='text-danger' id='"+pairdata+"'><i class='fa fa-times-circle'></i></a></td>";
    tr += "</tr>";
    $('#tableAdd tbody').append(tr);
    idnum += 1;
}

function deleteonepic(id) {
    if(pairdataArray.indexOf(id) != -1) {
        pairdataArray.splice($.inArray(id, pairdataArray),1);
        let deleteSelectedData = verifiedDataArray.find(o => o.pairdata == id );
        verifiedDataArray.splice($.inArray(deleteSelectedData, verifiedDataArray),1);
        $('#tableAdd tbody').empty();
        idnum = 1;
        for(let i = 0; i < verifiedDataArray.length; i+=1) {
            modalAddPic(
                verifiedDataArray[i].company,
                verifiedDataArray[i].name,
                verifiedDataArray[i].phone,
                verifiedDataArray[i].pairdata
            )
        }
    }
}

function modalRadioCheck() {
    $('#pick_tsel').click(function() {
        $('#modal_templates_pic_submit').prop('disabled',true)
        'use strict';
        $(this).prop('checked', true);
        $('#pick_vendor').prop('checked', false);
        if($(this).is(':checked')) {
            $('div#radiocheck').hide(); 
        } 
    });

    $('#pick_vendor').click(function() { 
        $('#modal_templates_pic_submit').prop('disabled',true)
        'use strict';
        $(this).prop('checked', true);
        $('#pick_tsel').prop('checked', false);
        if($(this).is(':checked')) {
            $('div#radiocheck').show(); 
        }
    });

    $('#pick_svc_true').click(function() {
        $('#modal_create_submit').prop('disabled',true)
        'use strict';
        $(this).prop('checked', true);
        $('#pick_svc_false').prop('checked', false);
        if($(this).is(':checked')) {
            $('div#radiosvc').show(); 
        } 
    });

    $('#pick_svc_false').click(function() { 
        $('#modal_create_submit').prop('disabled',true)
        'use strict';
        $(this).prop('checked', true);
        $('#pick_svc_true').prop('checked', false);
        if($(this).is(':checked')) {
            $('div#radiosvc').hide(); 
        }
    });

    $('#pick_pic_true').click(function() {
        $(this).prop('checked', true);
        $('#pick_pic_false').prop('checked', false);
        if($(this).is(':checked')) {
            $('div#selpicpartner').show(); 
            $('div#tableAdd').show();
        } 
    });

    $('#pick_pic_false').click(function() { 
        $(this).prop('checked', true);
        $('#pick_pic_true').prop('checked', false);
        if($(this).is(':checked')) {
            $('div#selpicpartner').hide(); 
            $('div#tableAdd').hide();
        }
    });

    $('#pick_sub_true').click(function() {
        $(this).prop('checked', true);
        $('#pick_sub_false').prop('checked', false);
        if($(this).is(':checked')) {
            $('div#selsubject').show(); 
        } 
    });

    $('#pick_sub_false').click(function() { 
        $(this).prop('checked', true);
        $('#pick_sub_true').prop('checked', false);
        if($(this).is(':checked')) {
            $('div#selsubject').hide(); 
        }
    });
}

function modalValidation() {
    $('input').blur(function () {
        // Template PIC Company
        if ($(this).hasClass('input_pic_comp')) {
            if ($(this).val().length === 0) {
                $(this).siblings('span.error').text('Please input PIC Company').fadeIn().parent().addClass('hasError');
                input_pic_compErr = true;
            } else {
                $(this).siblings('.error').text('').fadeOut().parent().removeClass('hasError');
                input_pic_compErr = false;
            }
        }
        // Template PIC Full Name
        if ($(this).hasClass('input_pic_name')) {
            if ($(this).val().length === 0) {
                $(this).siblings('span.error').text('Please input PIC Full Name').fadeIn().parent().addClass('hasError');
                input_pic_nameErr = true;
            } else {
                $(this).siblings('.error').text('').fadeOut().parent().removeClass('hasError');
                input_pic_nameErr = false;
            }
        }
        // Template INC/IM Id
        if ($(this).hasClass('input_inc_id')) {
            var incout = this.value;
            if ($(this).val().length === 0) {
                $(this).siblings('span.error').text('Please input INC/IM Number').fadeIn().parent().addClass('hasError');
                input_inc_idErr = true;
            } else if (incout.match(/(^(INC|IM)[0-9]{6,12}$)/gi) === null) {
                $(this).siblings('span.error').text('Please recheck INC/IM Format (IMXXXXX or INCXXXXX) - where X is Number in range 6-12 digits').fadeIn().parent().addClass('hasError');
                input_inc_idErr = true;
            } else {
                $(this).siblings('.error').text('').fadeOut().parent().removeClass('hasError');
                input_inc_idErr = false;
            }
        }
        // Template INC/IM Name
        if ($(this).hasClass('input_inc_name')) {
            if ($(this).val().length === 0) {
                $(this).siblings('span.error').text('Please input INC/IM Description').fadeIn().parent().addClass('hasError');
                input_inc_nameErr = true;
            } else {
                $(this).siblings('.error').text('').fadeOut().parent().removeClass('hasError');
                input_inc_nameErr = false;
            }
        }
        // Template Activity Name
        if ($(this).hasClass('input_act_name')) {
            if ($(this).val().length === 0) {
                $(this).siblings('span.error').text('Please input Activity Name').fadeIn().parent().addClass('hasError');
                input_act_nameErr = true;
            } else {
                $(this).siblings('.error').text('').fadeOut().parent().removeClass('hasError');
                input_act_nameErr = false;
            }
        }
        // Template CRQ No
        if ($(this).hasClass('input_act_crq')) {
            var output = this.value;
            if ($(this).val().length === 0) {
                $(this).siblings('span.error').text('Please input CRQ Number').fadeIn().parent().addClass('hasError');
                input_act_crqErr = true;
            } else if (output.match(/(^[0-9]{6,12}$)/gi) === null) {
                $(this).siblings('span.error').text('Please recheck CRQ Format (XXXXXX) - where X is Number in range 6-12 digits').fadeIn().parent().addClass('hasError');
                input_act_crqErr = true;
            } 
            else {
                $(this).siblings('.error').text('').fadeOut().parent().removeClass('hasError');
                input_act_crqErr = false;
            }
        }
        // Template Impact Service
        if ($(this).hasClass('input_svc')) {
            if ($(this).val().length === 0) {
                $(this).siblings('span.error').text('Please input Impact Service Description').fadeIn().parent().addClass('hasError');
                input_act_svcErr = true;
            } else {
                $(this).siblings('.error').text('').fadeOut().parent().removeClass('hasError');
                input_act_svcErr = false;
            }
        }      
        //enable disable submit button
        if($('#pick_vendor').is(':checked')){
            if (input_pic_compErr == true || input_pic_nameErr == true) {
                $('#modal_templates_pic_submit').prop('disabled',true);
            } else {
                $('#modal_templates_pic_submit').prop('disabled',false);
            }
        }

        if($('#pick_tsel').is(':checked')){
            if (input_pic_nameErr == true) {
                $('#modal_templates_pic_submit').prop('disabled',true);
            } else {
                $('#modal_templates_pic_submit').prop('disabled',false);
            }
        }

        if($('#pick_svc_true').is(':checked')){
            if (input_act_nameErr == true || input_act_crqErr == true || input_act_svcErr == true) {
                $('#modal_create_submit').prop('disabled',true);
            } else {
                $('#modal_create_submit').prop('disabled',false);
            }
        }

        if($('#pick_svc_false').is(':checked')){
            if (input_act_nameErr == true || input_act_crqErr == true) {
                $('#modal_create_submit').prop('disabled',true);
            } else {
                $('#modal_create_submit').prop('disabled',false);
            }
        }

        if (input_inc_idErr == true || input_inc_nameErr == true) {
            $('#modal_inc_submit').prop('disabled',true);
        } else {
            $('#modal_inc_submit').prop('disabled',false);
        }
    });

}

function modalSelectPicPartner() {
    $('#parter_pic').click(function (event) {
        event.preventDefault();

        var x = document.getElementById("toogle_pic");
        if (x.style.display === "none") {
          x.style.display = "block";
        } else {
          x.style.display = "none";
        }
    });
}

function modalfilterOptionComp() {
    $.ajax({
        url: 'activity/filter_pic_comp',
        type: 'POST',
        data: {'company': true},
        dataType: 'json',
        success: function(response) {
            modalcreateOptionComp(response.company,'company');
        }
    });
}

function modalcreateOptionComp(result,type){
    var listOption;
    var option;
    $('#'+type).empty();
    $('#'+type).append('<option value="" selected disabled>Select PIC Company</option>');
    for(index in result){
        listOption = result[index].company;
        option = "<option value='" + listOption + "'>" + listOption + "</option>";
        $('#'+type).append(option);
    }
}

function modalfilterOptionName(company) {
    $.ajax({
        url: 'activity/filter_pic_name',
        type: 'POST',
        data: {'company': company},
        dataType: 'json',
        success: function(response) {
            modalcreateOptionName(response,'pic_names');
        }
    });
}

function modalcreateOptionName(result,type){
    var listOptionName;
    var listOptionNo;
    var option;
    $('#'+type).empty();
    for(index in result){
        listOptionName = result[index].full_name;
        listOptionNo = result[index].phone_number;
        option = "<option value='" + listOptionName + "|" + listOptionNo +"'>" + listOptionName + "_" + listOptionNo +"</option>";
        $('#'+type).append(option);
    }
}

function modalPicSubmit() {
    $('#modal_templates_pic_submit').click(function (event) {
        event.preventDefault();

        if($('#pick_tsel').is(':checked')) {
            var pic_company = "TELKOMSEL"; 
        } else {
            var pic_company = $('#input_pic_comp').val(); 
        }

        var pic_name = $('#input_pic_name').val();
        var pic_no = $('#input_pic_no').val();

        $.ajax({
            url: 'activity/pic_form',
            type: 'POST',
            data: {
                    'pic_company': pic_company,
                    'pic_name': pic_name,
                    'pic_no': pic_no
                },
            dataType: 'json',
            success: function(data) {  
                if(data.status == 'NOK') {
                    alert(data.company + " - " + data.name + " (data sudah ada! coba lagi!)");
                } else if (data.status == 'NA') {
                    location.assign("activity");
                }
                else {
                    alert(data.company + " - " + data.name + " (berhasil ditambah)");
                    location.assign("activity");
                }
            }
        });

    });
}

function modalActivitySubmit() {
    $('#modal_create_submit').click(function (event) {
        event.preventDefault();
                
        var inputdt = $('#input_date').val().split("-");
        var act_date = inputdt[0] + "-" + inputdt[1] + "-" + inputdt[2];
        var act_subject = $('#select_subject').val();
        var act_cdso = $('#select_pic').val();
        var act_crq = 'CRQ#' + $('#input_act_crq').val();
        var act_category = $('#select_cat').val();
        var act_name = $('#input_act_name').val();
        var act_svc = $('#input_svc').val();

        var act_pic = '';
        for(let i = 0; i < pairdataArray.length; i++) {
            act_pic += pairdataArray[i];
            if(i < pairdataArray.length-1){
                act_pic += ",";
            }    
        }
        
        $.ajax({
            url: 'activity/activity_form',
            type: 'POST',
            data: {
                    'act_date': act_date,
                    'act_subject': act_subject,
                    'act_cdso': act_cdso,
                    'act_crq': act_crq,
                    'act_category': act_category,
                    'act_name': act_name,
                    'act_svc': act_svc,
                    'act_pic': act_pic
                },
            dataType: 'json',
            success: function(data) {  
                // console.log(data);
                if(data.status == 'NOK') {
                    alert('activity sudah terdaftar! coba lagi!');
                } else if (data.status == 'NA') {
                    location.assign("activity");
                    alert('error data not found!');
                }
                else {
                    alert("Activity " + data.crq_subject +" ("+ data.crq_no + ") berhasil ditambah");
                    location.assign("activity");
                }
            }
        });

        pairdataArray = [];
        verifiedDataArray = [];

    });
}

function modalIncSubmit() {
    $('#modal_inc_submit').click(function (event) {
        event.preventDefault();
                
        var inputdt = $('#input_date_inc').val().split("-");
        var inc_date = inputdt[0] + "-" + inputdt[1] + "-" + inputdt[2];
        var inc_subject = $('#select_subject_inc').val();
        var inc_cdso = $('#select_pic_inc').val();
        var inc_no = $('#input_inc_id').val();
        var inc_category = $('#select_cat_inc').val();
        var inc_name = $('#input_inc_name').val();
        var inc_svc = 'null';
        var inc_pic = 'null';
        
        $.ajax({
            url: 'activity/tshoot_form',
            type: 'POST',
            data: {
                    'inc_date': inc_date,
                    'inc_subject': inc_subject,
                    'inc_cdso': inc_cdso,
                    'inc_no': inc_no,
                    'inc_category': inc_category,
                    'inc_name': inc_name,
                    'inc_svc': inc_svc,
                    'inc_pic': inc_pic
                },
            dataType: 'json',
            success: function(data) {  
                // console.log(data);
                if(data.status == 'NOK') {
                    alert('INC/IM log sudah terdaftar! coba lagi!');
                } else if (data.status == 'NA') {
                    location.assign("activity");
                    alert('error data not found!');
                }
                else {
                    alert("INC/IM " + data.crq_subject +" ("+ data.crq_no + ") berhasil ditambah");
                    location.assign("activity");
                }
            }
        });
    });
}