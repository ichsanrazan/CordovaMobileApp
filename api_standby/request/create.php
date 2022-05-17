<?php 

    require_once('helper.php');

    //Get the input request parameters
	$inputJSON = file_get_contents('php://input');
	$input = json_decode($inputJSON, TRUE); //convert JSON into array

	//Check for Mandatory parameters
	if(isset($input['date_from']) && isset($input['pic_from']) && isset($input['date_to']) && isset($input['pic_to'])){
	$date_from = $input['date_from'];
	$pic_from = $input['pic_from'];
	$date_to = $input['date_to'];
	$pic_to = $input['pic_to'];

    //query
	$insertQuery  = "INSERT INTO request(date_from, pic_from, date_to, pic_to, status) VALUES (?,?,?,?,'0')";
	if($stmt = $db_connect->prepare($insertQuery)){
		$stmt->bind_param("ssss",$date_from,$pic_from,$date_to,$pic_to,);
		$stmt->execute();
		$response["message"] = "request created";
		$stmt->close();
	}
	}
	else{
		$response["message"] = "error!";
	}
	echo json_encode($response);
?>