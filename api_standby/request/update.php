<?php 

    require_once('helper.php');

    //Get the input request parameters
$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, TRUE); //convert JSON into array

    $date = $input['date'];
    $pic = $input['pic'];
    $pic_to = $input['pic_to'];
    $division = $input['division'];

    $query  = "UPDATE schedule SET date='$date',pic='$pic_to',division='$division' WHERE date='$date' AND pic='$pic' ";
    $sql    = mysqli_query($db_connect, $query);

    if ($sql) {
        echo json_encode( array('message' => 'schedule updated'));
    } else {
        echo json_encode( array('message' => 'error!'));
    }

?>