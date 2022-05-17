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
$result = $standbySchedule->generate_schedule();


//======================================================================
//TEMPORARY TESTING

// $weekend_counter = 0;
// $loop_control_weekend = true;

// while ($loop_control_weekend) {
//     $randomTimestamp = mt_rand($dateBegin->getTimestamp(), $dateEnd->getTimestamp());
//     $randomDate = new DateTime();
//     $randomDate->setTimestamp($randomTimestamp);


//     if ($randomDate->format('l') == "Saturday" || $randomDate->format('l') == "Sunday") {
//         echo $randomDate->format('Y-m-l-d');
//         echo "</br>";
//         $weekend_counter++;
//     }
//     if ($weekend_counter == 2) {
//         $loop_control_weekend = false;
//     }
// }


// $weekday_counter = 0;
// $loop_control_weekday = true;

// while ($loop_control_weekday) {
//     $randomTimestamp = mt_rand($dateBegin->getTimestamp(), $dateEnd->getTimestamp());
//     $randomDate = new DateTime();
//     $randomDate->setTimestamp($randomTimestamp);


//     if ($randomDate->format('l') != "Saturday" || $randomDate->format('l') != "Sunday") {
//         echo $randomDate->format('Y-m-l-d');
//         echo "</br>";
//         $weekday_counter++;
//     }
//     if ($weekday_counter == 5) {
//         $loop_control_weekday = false;
//     }
// }
