<?php

//Headers
header('Access-Control-Allow-Origin: *');
header('Content-Type: application/json');

include_once '../../config/Database.php';
include_once '../../models/Activity_Pic.php';

// Instantiate DB & connect
$database = new Database();
$db = $database->connect();

//Instantiate activity list object
$activityPic = new Activity_Pic($db);

//Query activity pic and count number of rows
$result = $activityPic->read();
$num = $result->rowCount();

if ($num > 0) {
    $activityPic_arr = array();
    $activityPic_arr['data'] = array();

    //Loop through all the rows and add to the array
    while ($row = $result->fetch(PDO::FETCH_ASSOC)) {
        extract($row);
        $activityPic_item = array(
            'id' => $id,
            'company' => $company,
            'full_name' => $full_name,
            'phone_number' => $phone_number,
        );
        array_push($activityPic_arr['data'], $activityPic_item);
    }
    echo json_encode($activityPic_arr);
} else {
    echo json_encode(
        array('message' => 'No Activity PIC Found')
    );
}
