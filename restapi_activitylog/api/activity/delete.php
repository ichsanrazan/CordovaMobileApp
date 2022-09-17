<?php

// Headers
header('Access-Control-Allow-Origin: *');
header('Content-Type: application/json');
header('Access-Control-Allow-Methods: DELETE');
header('Access-Control-Allow-Headers: Access-Control-Allow-Headers,Content-Type,Access-Control-Allow-Methods, Authorization, X-Requested-With');

include_once '../../config/Database.php';
include_once '../../models/Activity_List.php';

// Instantiate DB & connect
$database = new Database();
$db = $database->connect();

$activityList = new Activity_List($db);
// Get raw posted data
$data = json_decode(file_get_contents("php://input"));

$activityList->activity_id = $data->activity_id;

if ($activityList->delete()) {
    echo json_encode(
        array('message' => 'Activity Deleted')
    );
} else {
    echo json_encode(
        array('message' => 'Activity Not Deleted')
    );
}
