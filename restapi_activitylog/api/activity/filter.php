<?php

//Headers
header('Access-Control-Allow-Origin: *');
header('Content-Type: application/json');

include_once '../../config/Database.php';
include_once '../../models/Activity_List.php';

// Instantiate DB & connect
$database = new Database();
$db = $database->connect();

//Instantiate activity list object
$activityList = new Activity_List($db);

$start_date = isset($_GET['start_date']) ? $_GET['start_date'] : die();
$end_date = isset($_GET['end_date']) ? $_GET['end_date'] : die();
$subject = isset($_GET['subject']) ? $_GET['subject'] : die();
$category = isset($_GET['category']) ? $_GET['category'] : die();

$result = $activityList->filter($start_date, $end_date, $subject, $category);
$num = $result->rowCount();

$activityList_arr = array();
$activityList_arr['data'] = array();

if ($num > 0) {
    //Loop through all the rows and add to the array
    while ($row = $result->fetch(PDO::FETCH_ASSOC)) {
        extract($row);
        $activityList_item = array(
            'activity_id' => $activity_id,
            'crq_date' => $crq_date,
            'crq_subject' => $crq_subject,
            'pic_reporter' => $pic_reporter,
            'category' => $category,
            'crq_no' => $crq_no,
            'crq_activity' => $crq_activity,
            'crq_serviceimp' => $crq_serviceimp,
            'crq_pic' => $crq_pic,
            'owner' => $owner
        );
        array_push($activityList_arr['data'], $activityList_item);
    }
    echo json_encode($activityList_arr);
} else {
    $activityList_item = array(
        'activity_id' => "",
        'crq_date' => "",
        'crq_subject' => "",
        'pic_reporter' => "",
        'category' => "",
        'crq_no' => "",
        'crq_activity' => "",
        'crq_serviceimp' => "",
        'crq_pic' => "",
        'owner' => ""
    );
    array_push($activityList_arr['data'], $activityList_item);
    echo json_encode($activityList_arr);
}
