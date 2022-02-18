<?php
class Model_Activity extends CI_model
{

	public function __construct()
	{
		parent::__construct();
	}

	public function get_activity_list()
	{
		$db_load        = $this->load->database('default', TRUE);
		$query    		= "SELECT * FROM activity_list";
		$data_result    = $db_load->query($query)->result_array();
		return $data_result;
	}

	public function pic_submit($pic_comp, $pic_name, $pic_no)
	{
		$db_load 	= $this->load->database('default', TRUE);
		$db_data	= array(
			'name' => $pic_name,
			'company' => $pic_comp
		);
		if ($pic_comp != '' || $pic_name != '' || $pic_no != '') {
			$db_result	= $db_load->query(" SELECT * FROM activity_pic WHERE company = '$pic_comp' AND full_name = '$pic_name' AND phone_number = '$pic_no' LIMIT 1")->result_array();
			if (count($db_result) == 0 || $db_result[0] == 'undefined') {
				$db_load->query(" INSERT INTO activity_pic (company, full_name, phone_number) VALUES ('$pic_comp', '$pic_name', '$pic_no') ");
				$db_data['status'] = 'OK';
			} else {
				$db_data['status'] = 'NOK';
			}
		} else {
			$db_data['status'] = 'NA';
		}
		return $db_data;
	}

	public function activity_submit($act_date, $act_subject, $act_cdso, $act_crq, $act_category, $act_name, $act_svc, $act_pic)
	{
		$db_load 	= $this->load->database('default', TRUE);
		if ($act_svc == '') {
			$act_svc = 'null';
		}
		$db_data	= array(
			'crq_date' => $act_date,
			'crq_subject' => $act_subject,
			'pic_reporter' => $act_cdso,
			'category' => $act_category,
			'crq_no' => $act_crq,
			'crq_activity' => $act_name,
			'crq_serviceimp' => $act_svc,
			'crq_pic' => $act_pic
		);
		if ($act_date != '' || $act_subject != '' || $act_cdso != '' || $act_crq != '' || $act_category != '' || $act_name != '') {
			$db_result	= $db_load->query(" SELECT * FROM activity_list WHERE crq_date = '$act_date' AND crq_subject = '$act_subject' AND crq_no = '$act_crq' LIMIT 1")->result_array();
			if (count($db_result) == 0 || $db_result[0] == 'undefined') {
				$db_load->insert('activity_list', $db_data);
				$db_data['status'] = 'OK';
			} else {
				$db_data['status'] = 'NOK';
			}
		} else {
			$db_data['status'] = 'NA';
		}
		return $db_data;
	}

	public function tshoot_submit($inc_date, $inc_subject, $inc_cdso, $inc_no, $inc_category, $inc_name, $inc_svc, $inc_pic)
	{
		$db_load 	= $this->load->database('default', TRUE);
		$db_data	= array(
			'crq_date' => $inc_date,
			'crq_subject' => $inc_subject,
			'pic_reporter' => $inc_cdso,
			'category' => $inc_category,
			'crq_no' => $inc_no,
			'crq_activity' => $inc_name,
			'crq_serviceimp' => $inc_svc,
			'crq_pic' => $inc_pic
		);
		if ($inc_date != '' || $inc_subject != '' || $inc_cdso != '' || $inc_no != '' || $inc_category != '' || $inc_name != '') {
			$db_result	= $db_load->query(" SELECT * FROM activity_list WHERE crq_date = '$inc_date' AND crq_subject = '$inc_subject' AND crq_no = '$inc_no' LIMIT 1")->result_array();
			if (count($db_result) == 0 || $db_result[0] == 'undefined') {
				$db_load->insert('activity_list', $db_data);
				$db_data['status'] = 'OK';
			} else {
				$db_data['status'] = 'NOK';
			}
		} else {
			$db_data['status'] = 'NA';
		}
		return $db_data;
	}

	public function filterPicComp()
	{
		$db_load 	= $this->load->database('default', TRUE);
		$db_result	= $db_load->select('company')->distinct('company')->from('activity_pic')->get()->result();
		return $db_result;
	}

	public function filterPicName($pic)
	{
		$db_result 	= array();
		$db_load 	= $this->load->database('default', TRUE);
		$query		= $db_load->distinct()->like('company', $pic, 'none')->order_by('full_name', 'asc')->get('activity_pic')->result();
		foreach ($query as $row) {
			array_push($db_result, (object)[
				'full_name' => $row->full_name,
				'phone_number' => $row->phone_number
			]);
		}
		return $db_result;
	}

	public function filterPicNo($pic)
	{
		$db_load 	= $this->load->database('default', TRUE);
		$db_result	= $db_load->select('phone_number', 'full_name')->distinct('phone_number', 'full_name')->from('activity_pic')->like('company', $pic, 'none')->order_by('full_name', 'asc')->get()->result();
		return $db_result;
	}
	public function bc_play($input, $subject)
	{
		$db_result 	= array();
		$db_load 	= $this->load->database('default', TRUE);
		if ($subject == "all") {
			$query	= $db_load->query(" SELECT * FROM activity_list WHERE crq_date LIKE '$input' AND category NOT LIKE 'TROUBLESHOOT' ")->result();
		} else {
			$query	= $db_load->query(" SELECT * FROM activity_list WHERE crq_date LIKE '$input' AND crq_subject LIKE '$subject' AND category NOT LIKE 'TROUBLESHOOT' ")->result();
		}
		if (count($query) == 0 || $query[0] == 'undefined') {
			$db_result['status'] = 'NA';
			$db_result['crq_date'] = $input;
			$db_result['subject'] = $subject;
		} else {
			foreach ($query as $row) {
				array_push($db_result, (object)[
					'crq_no' => $row->crq_no,
					'pic_reporter' => $row->pic_reporter,
					'crq_activity' => $row->crq_activity,
					'crq_serviceimp' => $row->crq_serviceimp,
					'crq_pic' => $row->crq_pic,
					'crq_date' => $row->crq_date
				]);
			}
		}
		return $db_result;
	}
}
