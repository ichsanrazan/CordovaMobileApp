<?php 

    require_once('helper.php');

    //Get the input request parameters
$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, TRUE); //convert JSON into array

    $id = $input['id'];
    $date_from = $input['date_from'];
    $pic_from = $input['pic_from'];
    $date_to = $input['date_to'];
    $pic_to = $input['pic_to'];

    $query  = "UPDATE request SET date_from='$date_from',pic_from='$pic_from',date_to='$date_to',pic_to='$pic_to' WHERE id='$id' ";
    $sql    = mysqli_query($db_connect, $query);

    if ($sql) {
        echo json_encode( array('message' => 'updated'));
    } else {
        echo json_encode( array('message' => 'error!'));
    }

?>