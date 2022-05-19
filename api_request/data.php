<?php 

    require_once('helper.php');

    $query  = "SELECT * FROM request ORDER BY id DESC";
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

        echo json_encode( array('request' => $result) );
    }

?>