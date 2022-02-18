<?php
defined('BASEPATH') or exit('No direct script access allowed');

class Activity extends CI_Controller
{
    var $info;

    public function __construct()
    {
        parent::__construct();
        $this->load->model('Model_Activity', 'm_act');

        $this->info['tab_title']    = "Activity Log";
        $this->info['upper_title']  = "";
        $this->info['breadcrumb']   = $this->uri->segment(1);
        $this->info['style']        = "none";
        $this->info['script']       = "none";
    }

    private function _init_Sidebar_Data($info)
    {
        $data['title']          = $info['tab_title'];
        $data['upper_title']    = $info['upper_title'];
        $data['breadcrumb']     = $info['breadcrumb'];
        $data['style']          = $info['style'];
        $data['script']         = $info['script'];

        return $data;
    }

    private function _loadViews($controller, $data)
    {
        $this->load->view('dir_templates/view_header', $data);
        $this->load->view($controller);
        $this->load->view('dir_templates/view_footer', $data);
    }

    public function index()
    {
        $data = $this->_init_Sidebar_Data($this->info);
        $this->_loadViews('view_activity', $data);
    }

    public function js_set_datatable_activity()
    {
        $tbl_activity    = $this->m_act->get_activity_list();
        echo json_encode($tbl_activity);
    }

    public function pic_form()
    {
        $data = $this->m_act->pic_submit(trim($_POST['pic_company']), trim($_POST['pic_name']), trim($_POST['pic_no']));
        echo json_encode($data);
    }

    public function activity_form()
    {
        $data = $this->m_act->activity_submit(trim($_POST['act_date']), trim($_POST['act_subject']), trim($_POST['act_cdso']), trim($_POST['act_crq']), trim($_POST['act_category']), trim($_POST['act_name']), trim($_POST['act_svc']), trim($_POST['act_pic']));
        echo json_encode($data);
    }

    public function tshoot_form()
    {
        $data = $this->m_act->tshoot_submit(trim($_POST['inc_date']), trim($_POST['inc_subject']), trim($_POST['inc_cdso']), trim($_POST['inc_no']), trim($_POST['inc_category']), trim($_POST['inc_name']), trim($_POST['inc_svc']), trim($_POST['inc_pic']));
        echo json_encode($data);
    }

    public function filter_pic_comp()
    {
        $data['company'] = $this->m_act->filterPicComp();
        echo json_encode($data);
    }

    public function filter_pic_name()
    {
        $data = $this->m_act->filterPicName(trim($_POST['company']));
        echo json_encode($data);
    }

    public function broadcast_play()
    {
        $data = $this->m_act->bc_play(trim($_POST['input']), trim($_POST['subject']));
        echo json_encode($data);
    }
}
