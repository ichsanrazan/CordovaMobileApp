<?php

// Headers
header('Access-Control-Allow-Origin: *');
header('Content-Type: application/json');
header('Access-Control-Allow-Methods: POST');
header('Access-Control-Allow-Headers: Access-Control-Allow-Headers,Content-Type,Access-Control-Allow-Methods, Authorization, X-Requested-With');

include_once '../../config/Database.php';
include_once '../../models/Activity_List.php';

// Instantiate DB & connect
$database = new Database();
$db = $database->connect();

$activityList = new Activity_List($db);
// Get raw posted data
$data = json_decode(file_get_contents("php://input"));

$activityList->crq_date = $data->crq_date;
$activityList->crq_subject = $data->crq_subject;
$activityList->pic_reporter = $data->pic_reporter;
$activityList->category = $data->category;
$activityList->crq_no = $data->crq_no;
$activityList->crq_activity = $data->crq_activity;
$activityList->crq_serviceimp = $data->crq_serviceimp;
$activityList->crq_pic = $data->crq_pic;
$activityList->owner = $data->owner;

//Check if there is duplicate activity
if ($activityList->read_check_duplicates()) {
    if ($activityList->create()) {
        echo json_encode(
            array('message' => 'Activity Created')
        );
    } else {
        echo json_encode(
            array('message' => 'Activity Not Created')
        );
    }
} else {
    echo json_encode(
        array('message' => 'NOK')
    );
}
