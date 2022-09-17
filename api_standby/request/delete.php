<?php 

    require_once('helper.php');

    //Get the input request parameters
    $inputJSON = file_get_contents('php://input');
    $input = json_decode($inputJSON, TRUE); //convert JSON into array

    $id = $input['id'];

    $query  = "DELETE FROM request WHERE id='$id' ";
    $sql    = mysqli_query($db_connect, $query);

    if ($sql) {
        echo json_encode( array('message' => 'request deleted!'));
    } else {
        echo json_encode( array('message' => 'error!'));
    }

?>