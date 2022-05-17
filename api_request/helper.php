<?php 

    define('HOST', 'localhost');
    define('USER', 'root');
    define('PASS', '');
    define('DB', 'standby');

    $db_connect = mysqli_connect( HOST, USER, PASS, DB ) or die ('Unable to connect db');
    
    header('Content-Type: application/json');

?>