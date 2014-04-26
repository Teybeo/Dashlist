<!DOCTYPE HTML>
<?php session_start(); ?>

<html>
<head>
	<meta charset="UTF-8">
	<title>Dashlist</title>
	<link rel="stylesheet" href="../css/style.css" type="text/css">
	<link rel="icon" type="image/png" href="../images/logo.png" />
    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>   
	<script type="text/javascript">
		function validerConnexion()
		{
			$.ajax({
				type: "POST",
				url: "connexion.php",
				data: { login: $("#co_login").val(), password: $("#co_pass").val() }
			})
			.done(function( msg ) {
				$(".error").html(msg);
				if($("#errorfield").hasClass("textok"))
				{
					setTimeout(function(){ location.reload(); }, 1200);
				}
			});
		}
		
		function validerInscription()
		{
			$.ajax({
				type: "POST",
				url: "inscription.php",
				data: { login: $("#ins_login").val(), 
						mail: $("#ins_mail").val(),
						pass: $("#ins_pass").val(),
						pass2: $("#ins_pass2").val()}
			})
			.done(function( msg ) {
				$(".error").html(msg);
				
				if($("#errorfield").hasClass("textok"))
				{
					$("#ttglobal").delay(600).fadeOut(200);
				}
			});
		}
	</script>
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
			<script type="text/javascript">
				$(".inscription").click(function(){
					$("#ttbox").css("height", "300px").css("width", "360px").css("margin-left", "-180px");
					$("#ttbox").load("forms/form_inscription.php");
					$("#ttglobal").fadeIn(200);
				});
				
				$(".connexion").click(function(){
					$("#ttbox").css("height", "210px").css("width", "360px").css("margin-left", "-180px");
					$("#ttbox").load("forms/form_connexion.php");
					$("#ttglobal").fadeIn(200);
				});
				
				$("#ttglobal").click(function(e) {
					if (!(e.target.id == "ttbox" || $(e.target).parents("#ttbox").size())) { 
						$("#ttglobal").fadeOut(200);
					} 
				});
	
			</script>
		</div>
	</div>