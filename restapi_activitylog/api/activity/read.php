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

//Query activity list and count number of rows
$result = $activityList->read();
$num = $result->rowCount();

if ($num > 0) {
    $activityList_arr = array();
    $activityList_arr['data'] = array();

    //Loop through all the rows and add to the array
    while ($row = $result->fetch(PDO::FETCH_ASSOC)) {
        extract($row);
        $activityList_item = array(
            'crq_date' => $crq_date,
            'crq_subject' => $crq_subject,
            'pic_reporter' => $pic_reporter,
            'category' => $category,
            'crq_no' => $crq_no,
            'crq_activity' => $crq_activity,
            'crq_serviceimp' => $crq_serviceimp,
            'crq_pic' => $crq_pic
        );
        array_push($activityList_arr['data'], $activityList_item);
    }
    echo json_encode($activityList_arr);
} else {
    echo json_encode(
        array('message' => 'No Activity Found')
    );
}
