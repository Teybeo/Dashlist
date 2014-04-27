<!DOCTYPE HTML>
<?php session_start(); ?>

<html>
<head>
	<meta charset="UTF-8">
	<title>Dashlist</title>
	<link rel="stylesheet" href="../css/style.css" type="text/css">
	<link rel="icon" type="image/png" href="../images/logo.png" />
    <script type="text/javascript" src="../js/jquery.min.js"></script>   
	<script type="text/javascript" src="../js/sign_log.js"></script>
</head>
<body>
	<div id="ttglobal">
		<div id="ttbox">
		</div>
	</div>
	<div id="header">
		<div>
			<div class="logo">
				<a href="index.php"><img src="../images/logo.png" width="130px" height="130px"/></a>
			</div>
			
			<div id="navigation">
					<a href="index.php">Accueil</a>
					<a href="download.php">Téléchargement</a>
					<a href="news.php">News</a>
					<a href="contact.php">Contact</a>
					<?php
						if(isset($_SESSION['login']))
						{
							echo '<a href="account.php">'.$_SESSION['login'].'</a>';
							echo '<a href="deconnexion.php" class="deconnexion">Déconnexion</a>';
						}
						else
						{
							echo '<a href="#" class="connexion">Connexion</a>';
							echo '<a href="#" class="inscription">Inscription</a>';
						}
					?>
			</div>
		</div>
	</div>