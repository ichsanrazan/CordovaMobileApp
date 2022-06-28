<?php 

    require_once('db.php');

    $query  = "SELECT * FROM kpi_core_test ORDER BY id DESC";
    $sql    = mysqli_query($db_connect, $query);

    if($sql) {

        $result = array();
        while ($row = mysqli_fetch_array($sql)) {
            array_push( $result,array(
                'id' => $row['id'],
                'date_time' => $row['date_time'],
                'mss' => $row['mss'],
                'scr' => $row['scr'],
                'ccr' => $row['ccr'],
            ) );
        }

        echo json_encode( array('data' => $result) );
    }

?>