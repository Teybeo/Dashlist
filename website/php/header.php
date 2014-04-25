<!DOCTYPE HTML>
<html>
<head>
	<meta charset="UTF-8">
	<title>Dashlist</title>
	<link rel="stylesheet" href="../css/style.css" type="text/css">
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
				$(".error").text(msg);
				$("#ttglobal").delay(400).fadeOut(200);
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
					<a href="features.php">Téléchargement</a>
					<a href="news.php">News</a>
					<a href="contact.php">Contact</a>
					<a href="#" class="connexion">Connexion</a>
                    <a href="#" class="inscription">Inscription</a>
			</div>
			<script type="text/javascript">
				$(".inscription").click(function(){
					$("#ttbox").css("height", "300px").css("width", "360px").css("margin-left", "-180px");
					$("#ttbox").load("form_inscription.html");
					$("#ttglobal").fadeIn(200);
				});
				
				$(".connexion").click(function(){
					$("#ttbox").css("height", "210px").css("width", "360px").css("margin-left", "-180px");
					$("#ttbox").load("form_connexion.html");
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