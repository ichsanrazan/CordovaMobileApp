<?php 

    require_once('helper.php');


    $name = isset($_GET['name']) ? $_GET['name'] : die();

    $query  = "SELECT * FROM request WHERE pic_from LIKE '$name'";
    $sql    = mysqli_query($db_connect, $query);

    if($sql) {

        $result = array();
        while ($row = mysqli_fetch_array($sql)) {
            array_push( $result,array(
                'id' => $row['id'],
                'date_from' => $row['date_from'],
                'pic_from' => $row['pic_from'],
                'date_to' => $row['date_to'],
                'pic_to' => $row['pic_to'],
                'status' => $row['status'],
            ) );
        }

        echo json_encode( array('data' => $result) );
    }else{
        echo json_encode( array('message' => 'error!'));
    }

?>