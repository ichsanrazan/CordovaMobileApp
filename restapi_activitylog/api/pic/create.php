<?php

// Headers
header('Access-Control-Allow-Origin: *');
header('Content-Type: application/json');
header('Access-Control-Allow-Methods: POST');
header('Access-Control-Allow-Headers: Access-Control-Allow-Headers,Content-Type,Access-Control-Allow-Methods, Authorization, X-Requested-With');

include_once '../../config/Database.php';
include_once '../../models/Activity_Pic.php';

// Instantiate DB & connect
$database = new Database();
$db = $database->connect();

$activityPic = new Activity_Pic($db);
// Get raw posted data
$data = json_decode(file_get_contents("php://input"));

$activityPic->company = $data->company;
$activityPic->full_name = $data->full_name;
$activityPic->phone_number = $data->phone_number;


//Check if there is duplicate activity pic
if ($activityPic->read_check_duplicates()) {
    if ($activityPic->create()) {
        echo json_encode(
            array('message' => 'Activity PIC Created')
        );
    } else {
        echo json_encode(
            array('message' => 'Activity PIC Not Created')
        );
    }
} else {
    echo json_encode(
        array('message' => 'NOK')
    );
}
