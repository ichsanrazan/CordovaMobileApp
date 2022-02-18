<ol class="breadcrumb breadcrumb-fix">
    <li class="breadcrumb-item"><a id="home-test" href="sum_home"><i class="fas fa-home"></i></a></li>
    <li class="breadcrumb-item active" aria-current="page"><?php echo $title ?></li>
    <input type="hidden" id="base" value="<?php echo base_url(); ?>">
</ol>

<div class="card" style="width: 100%;">
    <div class="card-body">
        <div class="row">
            <div class="col-sm-1">
                <label>Action</label>
            </div>
            <div class="col-sm-2">
                <a href="#modal_templates_pic" data-toggle="modal" data-target="#modal_templates_pic"><i class="fas fa-user-plus"></i><span> - add pic partner</span></a>
            </div>
            <div class="col-sm-3">
                <a href="#modal_create" data-toggle="modal" data-target="#modal_create"><i class="fas fa-tasks"></i><span> - add activity CRQ/WO</span></a>
            </div>
            <div class="col-sm-3">
                <a href="#modal_inc" data-toggle="modal" data-target="#modal_inc"><i class="fas fa-tools"></i><span> - add troubleshoot INC/IM</span></a>
            </div>
            <div class="col-sm-2">
                <a href="#modal_bc" data-toggle="modal" data-target="#modal_bc"><i class="fas fa-play-circle"></i><span> - broadcast Activity</span></a>
            </div>
        </div>
    </div>
</div>

<div class="card" style="width: 100%;">
    <div class="card-body">
        <div class="row">
            <table id="t_activity" class="table table-striped table-bordered" style="width:100%"></table>
        </div>
    </div>
</div>

<?php if (validation_errors()) : ?>
    <div class="alert alert-danger" role="alert">
        <?= validation_errors(); ?>
    </div>
<?php endif; ?>
<?= $this->session->flashdata('success_message'); ?>
<?= $this->session->flashdata('alert_message'); ?>

<div class="modal fade" id="modal_templates_pic" style="top: 50px;" data-keyboard="false">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">

            <!-- Modal Header -->
            <div class="modal-header">
                <h4 class="modal-title">Add PIC Templates</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>

            <!-- Modal body -->
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="form-group">
                        <div class="col">
                            <div class="row">
                                <input type="radio" class="radiopick" id="pick_tsel" name="pick_tsel" checked>
                                <label class="col-sm-2" for="pick_tsel">TSEL</label><br>
                                <input type="radio" class="radiopick" id="pick_vendor" name="pick_vendor">
                                <label class="col-sm-2" for="pick_vendor">VENDOR</label><br>
                            </div>
                            <div id="radiocheck" name="radiocheck" class="row" style="display:none;">
                                <label class="col-sm-4 col-form-label">Company Name</label>
                                <input type="text" class="form-control input_pic_comp" id="input_pic_comp" name="input_pic_comp" oninput="this.value = this.value.toUpperCase()" required>
                                <span class="error"></span>
                            </div>
                            <div class="row">
                                <label class="col-sm-6 col-form-label">Full Name</label>
                                <input type="text" class="form-control input_pic_name" id="input_pic_name" name="input_pic_name" required>
                                <span class="error"></span>
                            </div>
                            <div class="row">
                                <label class="col-sm-6 col-form-label">Phone Number</label>
                                <input type="text" class="form-control" id="input_pic_no" name="input_pic_no" placeholder="(optional)">
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Modal footer -->
            <div class="modal-footer">
                <button id="modal_templates_pic_submit" class="form btn btn-success"><i class="fa fa-paper-plane"></i> Submit</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="modal_create" style="top: 35px;" data-keyboard="false">
    <div class="modal-dialog modal-dialog-scrollable modal-lg">
        <div class="modal-content">

            <!-- Modal Header -->
            <div class="modal-header">
                <h4 class="modal-title">Activity Form</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>

            <!-- Modal body -->
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="form-group">
                        <div class="row">
                            <label class="col-sm-2 col-form-label">Date</label>
                            <div class="col-sm-4">
                                <input type="date" class="form-control" id="input_date" name="input_date" value="<?php $date = '';
                                                                                                                    date_default_timezone_set('Asia/Jakarta');
                                                                                                                    $date = date('Y-m-d');
                                                                                                                    echo $date; ?>">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <label class="col-sm-2 col-form-label">Subject</label>
                            <div class="col-sm-4">
                                <select id="select_subject" name="select_subject" class="custom-select">
                                    <option id="selected_pick" selected value="cs_core">Core CS</option>
                                    <option value="ps_core">Core PS</option>
                                    <option value="datacomm">Datacomm</option>
                                    <option value="security">Security</option>
                                </select>
                            </div>
                            <label class="col-sm-2 col-form-label">Reporter</label>
                            <div class="col-sm-4">
                                <select id="select_pic" name="select_pic" class="custom-select">
                                    <option id="selected_pick" selected value="Bruury">Bruury SR Efendy</option>
                                    <option value="Habibi">Mohamad Habibi</option>
                                    <option value="Rahmat">Rahmat Jaelani</option>
                                    <option value="Wahyu">Wahyu Sulaksono</option>
                                    <option value="Chandra">Chandra Eka Kurniawan</option>
                                    <option value="Erlangga">Erlangga Ervansyah</option>
                                    <option value="Gunawan">Gunawan</option>
                                    <option value="Hasian">Hasian SM Hutagalung</option>
                                    <option value="Wildan">Wildan Khalidy</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <label class="col-sm-2 col-form-label">CRQ Number</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control required_form input_act_crq" id="input_act_crq" name="input_act_crq" placeholder="">
                                <span class="error"></span>
                            </div>
                            <label class="col-sm-2 col-form-label">Category</label>
                            <div class="col-sm-4">
                                <select id="select_cat" name="select_cat" class="custom-select">
                                    <option selected value="ADD_UPGRADE">Add/Upgrade Resource</option>
                                    <option value="AUDIT_REHEARSAL">Audit/Rehearsal</option>
                                    <option value="HQ_PROJECT">HQ Project</option>
                                    <option value="RECONFIGURE">Reconfiguration</option>
                                    <option value="CORRECTIVE_PREVENTIVE">Corrective/Preventive</option>
                                    <option value="OTHER">Other</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col">
                                <label class="col-form-label">Activity Name</label>
                                <input type="text" class="form-control required_form input_act_name" id="input_act_name" name="input_act_name" required>
                                <span class="error"></span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <label class="col-sm-3">Service Impact ?</label>
                            <input type="radio" class="radiopick radio_default" id="pick_svc_false" name="pick_svc_false" checked>
                            <label class="col-sm-1">No</label><br>
                            <input type="radio" class="radiopick radio_nodefault" id="pick_svc_true" name="pick_svc_true">
                            <label class="col-sm-1">Yes</label><br>
                        </div>
                        <div class="row">
                            <div id="radiosvc" class="col-sm-12 form_prompt" name="radiosvc" style="display:none;">
                                <input type="text" class="form-control required_form input_svc" id="input_svc" name="input_svc" placeholder="Description" required>
                                <span class="error"></span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <label class="col-sm-3">Add PIC Detail ?</label>
                            <input type="radio" class="radiopick radio_default" id="pick_pic_false" name="pick_pic_false" checked>
                            <label class="col-sm-1">No</label><br>
                            <input type="radio" class="radiopick radio_nodefault" id="pick_pic_true" name="pick_pic_true">
                            <label class="col-sm-1">Yes</label><br>
                        </div>
                        <div class="row" id="selpicpartner" class="input-group" name="selpicpartner" style="display:none;">
                            <div class="col">
                                <div class="input-group">
                                    <select id="company" class="form-control required_form" onchange="modalfilterOptionName(this.value);$('#picvalue_name')[0].reset();$('#add_pic').prop('disabled', false);">
                                        <option selected disabled>Select PIC Company</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col">
                                <form id="picvalue_name">
                                    <div class="input-group">
                                        <select id="pic_names" class="form-control required_form">
                                            <option selected disabled>Select PIC Name</option>
                                        </select>
                                    </div>
                                </form>
                            </div>
                            <button class="btn btn-info" id="add_pic" onclick="modalcheckPic($('#company').val(),$('#pic_names').val())" disabled><i class="fa fa-plus"></i></button>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="table-responsive" id="tableAdd" style="display:none;">
                            <table class="table table-striped table-hover">
                                <thead>
                                    <tr>
                                        <th>No</th>
                                        <th>Company</th>
                                        <th>Full Name</th>
                                        <th>Phone Number</th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody></tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Modal footer -->
            <div class="modal-footer">
                <button type="submit" id="modal_create_reset" class="form btn btn-warning" onclick="modalresetForm()"><i class="fa fa-undo"></i> Reset Form</button>
                <button type="submit" id="modal_create_submit" class="form btn btn-success"><i class="fa fa-paper-plane"></i> Submit</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="modal_inc" style="top: 35px;" data-keyboard="false">
    <div class="modal-dialog modal-dialog-scrollable modal-lg">
        <div class="modal-content">

            <!-- Modal Header -->
            <div class="modal-header">
                <h4 class="modal-title">INC/IM Troubleshoot Form</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>

            <!-- Modal body -->
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="form-group">
                        <div class="row">
                            <label class="col-sm-2 col-form-label">Date</label>
                            <div class="col-sm-4">
                                <input type="date" class="form-control" id="input_date_inc" name="input_date_inc" value="<?php $date = '';
                                                                                                                            date_default_timezone_set('Asia/Jakarta');
                                                                                                                            $date = date('Y-m-d');
                                                                                                                            echo $date; ?>">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <label class="col-sm-2 col-form-label">Subject</label>
                            <div class="col-sm-4">
                                <select id="select_subject_inc" name="select_subject_inc" class="custom-select">
                                    <option id="selected_pick_inc" selected value="cs_core">Core CS</option>
                                    <option value="ps_core">Core PS</option>
                                    <option value="datacomm">Datacomm</option>
                                    <option value="security">Security</option>
                                </select>
                            </div>
                            <label class="col-sm-2 col-form-label">PIC CDSO</label>
                            <div class="col-sm-4">
                                <select id="select_pic_inc" name="select_pic_inc" class="custom-select">
                                    <option id="selected_pick_inc" selected value="Bruury">Bruury SR Efendy</option>
                                    <option value="Habibi">Mohamad Habibi</option>
                                    <option value="Rahmat">Rahmat Jaelani</option>
                                    <option value="Wahyu">Wahyu Sulaksono</option>
                                    <option value="Chandra">Chandra Eka Kurniawan</option>
                                    <option value="Erlangga">Erlangga Ervansyah</option>
                                    <option value="Gunawan">Gunawan</option>
                                    <option value="Hasian">Hasian SM Hutagalung</option>
                                    <option value="Wildan">Wildan Khalidy</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <label class="col-sm-2 col-form-label">INC/IM No</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control required_form input_inc_id" id="input_inc_id" name="input_inc_id" placeholder="">
                                <span class="error"></span>
                            </div>
                            <label class="col-sm-2 col-form-label">Category</label>
                            <div class="col-sm-4">
                                <select id="select_cat_inc" name="select_cat_inc" class="custom-select">
                                    <option selected value="TROUBLESHOOT">INC/IM Troubleshoot</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col">
                                <label class="col-form-label">INC/IM Title</label>
                                <input type="text" class="form-control required_form input_inc_name" id="input_inc_name" name="input_inc_name" required>
                                <span class="error"></span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Modal footer -->
            <div class="modal-footer">
                <button type="submit" id="modal_inc_reset" class="form btn btn-warning" onclick="modalresetForm()"><i class="fa fa-undo"></i> Reset Form</button>
                <button type="submit" id="modal_inc_submit" class="form btn btn-success"><i class="fa fa-paper-plane"></i> Submit</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="modal_bc" style="top: 35px;" data-keyboard="false">
    <div class="modal-dialog modal-dialog-scrollable modal-lg">
        <div class="modal-content">
            <!-- Modal Header -->
            <div class="modal-header">
                <h4 class="modal-title">Broadcast Activity</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <!-- Modal Body -->
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="form-group">
                        <div class="row">
                            <div class="col-sm-2">
                                <label class="col-form-label">Select Date</label>
                            </div>
                            <div class="col">
                                <input type="date" class="form-control" id="input_bc_date" name="input_bc_date" value="<?php $date = '';
                                                                                                                        date_default_timezone_set('Asia/Jakarta');
                                                                                                                        $date = date('Y-m-d');
                                                                                                                        echo $date; ?>">
                            </div>
                            <div class="col offset-sm-1">
                                <div class="btn-group" role="group" aria-label="Basic example">
                                    <button type="button" id="modal_bc_play" class="btn btn-success"><i class="fas fa-play-circle"></i> start</button>
                                    <button type="button" id="modal_bc_copy" class="btn btn-info"><i class="fas fa-copy"></i> copy</button>
                                </div>
                            </div>
                            <div class="col">
                                <span id="copied_bc" class="col text-success" style="display:none;">copied!</span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <label class="col-sm-2">All Subject ?</label>
                            <input type="radio" class="radiopick radio_default" id="pick_sub_false" name="pick_sub_false" checked>
                            <label class="col-sm-1">Yes</label><br>
                            <input type="radio" class="radiopick radio_nodefault" id="pick_sub_true" name="pick_sub_true">
                            <label class="col-sm-1">No</label><br>
                            <div id="selsubject" name="selsubject" class="col" style="display:none;">
                                <select id="picksubject" name="picksubject" class="custom-select">
                                    <option selected disabled value="all">Select Subject</option>
                                    <option value="cs_core">Core CS</option>
                                    <option value="ps_core">Core PS</option>
                                    <option value="datacomm">Datacomm</option>
                                    <option value="security">Security</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <label class="col-sm-2 col-form-label">Preview</label>
                        <textarea class="form-control" name="bc_text" id="bc_text" rows="10" readonly></textarea>
                    </div>
                </div>
            </div>
            <!-- Modal footer -->
            <div class="modal-footer">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="assets/js/activity.js"></script>