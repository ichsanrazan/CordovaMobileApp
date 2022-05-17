<?php

//Headers
header('Access-Control-Allow-Origin: *');
header('Content-Type: application/json');

include_once '../config/Database.php';
include_once '../models/Standby_Schedule.php';


// Instantiate DB & connect
$database = new Database();
$db = $database->connect();

$standbySchedule = new Standby_Schedule($db);
$result = $standbySchedule->read();
$num = $result->rowCount();

if ($num > 0) {
    $standbySchedule_arr = array();
    $standbySchedule_arr['data'] = array();

    //Loop through all the rows and add to the array
    while ($row = $result->fetch(PDO::FETCH_ASSOC)) {
        extract($row);
        $standbySchedule_item = array(
            'id' => $id,
            'date' => $date,
            'pic' => $pic,
            'division' => $division,
        );
        array_push($standbySchedule_arr['data'], $standbySchedule_item);
    }
    echo json_encode($standbySchedule_arr);
} else {
    echo json_encode(
        array('message' => 'No Schedule Found')
    );
}
