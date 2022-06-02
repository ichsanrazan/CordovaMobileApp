<?php

require_once('helper.php');
mysqli_report(MYSQLI_REPORT_ERROR | MYSQLI_REPORT_STRICT);

//Get the input request parameters
$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, TRUE); //convert JSON into array

$id = $input['id'];
$date_from = $input['date_from'];
$date_to = $input['date_to'];

$pic_from = $input['pic_from'];
$pic_to = $input['pic_to'];

$db_connect->set_charset('utf8mb4');
$stmt = $db_connect->begin_transaction();

$stmt = $db_connect->prepare("UPDATE schedule SET date = '$date_from'" . " WHERE pic = '$pic_to' AND date = '$date_to'");
$stmt->execute();

$stmt = $db_connect->prepare("UPDATE schedule SET date = '$date_to'" . " WHERE pic = '$pic_from' AND date = '$date_from'");
$stmt->execute();

$stmt = $db_connect->prepare("UPDATE request SET status = 1 WHERE id = '$id'");
$stmt->execute();


if ($db_connect->commit()) {
    echo json_encode(array('message' => 'schedule updated'));
} else {
    echo json_encode(array('message' => 'error!'));
}
