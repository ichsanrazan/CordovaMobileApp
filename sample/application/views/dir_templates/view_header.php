<!DOCTYPE html>
<html lang="id">
<head>
	<!-- Required meta tags -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no, shrink-to-fit=no" />
	<meta name="description" content="Dashboard Datacomm & Core"> 

	<title><?=$title;?></title>

	<!-- CSS Bootstrap -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	
	<link rel="stylesheet" href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/all.min.css' />
	<link rel="stylesheet" href='https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/base/jquery-ui.css' />
	<link rel="stylesheet" href='https://cdn.datatables.net/1.11.0/css/jquery.dataTables.min.css'/>
	<link rel="stylesheet" href="assets/css/styleslider.css">
	<link rel="stylesheet" href="assets/css/header.css">
    <link rel="stylesheet" href="assets/css/styleisichart.css">
    <link rel="stylesheet" href="assets/css/tab_expand.css">
    <!-- DataTables -->
    <link rel="stylesheet" href="plugins/datatables/dataTables.bootstrap.css">
    <link rel="stylesheet" href="assets/css/costum.css">

	<!-- Cisco Next UI -->
	<link rel="stylesheet" href="plugins/next/css/next.min.css">
	<script type="text/javascript" src="plugins/next/js/customnext.js"></script>

    <!-- JQuery :: GLOBAL -->

    <!-- <script src='https://code.jquery.com/jquery-3.4.1.js'></script> -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <!-- <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script> -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>
    <script src='https://cdn.datatables.net/v/bs4-4.1.1/jq-3.3.1/dt-1.10.20/fc-3.3.0/fh-3.1.6/r-2.2.3/sp-1.0.1/datatables.min.js'></script>
    <!-- <script src='https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js'></script> -->
    <script src='https://code.highcharts.com/stock/highstock.js'></script>
    <script src='https://code.highcharts.com/stock/modules/exporting.js'></script>  
    
    <!-- Jquery Smoothstate/Caraousel -->
    <script src='assets/js/jquery.smoothState.min.js'></script>
    <script src='assets/js/mainslider.js'></script>
</head>
<body class="bg-light">
	<nav class="navbar navbar-expand-lg navbar-light bg-secondary">
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle" style="background-color: #a9e4d7;" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						Menu
					</a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdown">
						<a class="dropdown-item" href="activity">Activity Log</a>
					</div>
				</li>
				<li class="nav-item">
					<a class="nav-link bg-light" href="">Core</a>
				</li>
				<li class="nav-item">
					<a class="nav-link bg-light" href="">Datacomm</a>
				</li>
			</ul>
		</div>
		<a class="navbar-brand" href="activity">
			<img src="assets/images/cordova-light.png" width="75" height="30" alt="">
		</a>
	</nav>

	<div class="container-fluid">
		<div class="row-fluid">
			<div id="main">