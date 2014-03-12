<!DOCTYPE HTML>
<html>
<head>
	<meta charset="UTF-8">
	<title>Dashlist</title>
	<link rel="stylesheet" href="css/style.css" type="text/css">
    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
	<script src="../js/generator.js"></script>
    
	<!-- Pop-up messages d'alertes -->
	<script src="../js/generator.message.js"></script>
	<link rel="stylesheet" href="../css/generator.message.css">

	<!-- Fenêtres -->
	<script src="../js/generator.ttbox.js"></script>
	<link rel="stylesheet" href="../css/generator.ttbox.css">

	<!-- Formulaires -->
	<script src="../js/generator.form.js"></script>
	<link rel="stylesheet" href="../css/generator.form.css">

	<!-- Champs -->
	<script src="../js/generator.textinput.js"></script>
	<link rel="stylesheet" href="../css/generator.textinput.css">

	<!-- Titres -->
	<script src="../js/generator.title.js"></script>
	<link rel="stylesheet" href="../css/generator.title.css">
    
</head>
<body>
	<div id="header">
		<div>
			<div class="logo">
				<a href="index.html"><img src="images/logo.png" width="130px" height="130px"/></a>
			</div>
			
			<div id="navigation">
					<a href="index.php">Accueil</a>
					<a href="features.php">Téléchargement</a>
					<a href="news.php">News</a>
					<a href="contact.php">Contact</a>
					<a href="log.php">Connexion</a>
                    <a class="inscription">Inscription</a>
			</div>
		</div>
	</div>

	<div id="contents">
		<div id="tagline" class="clearfix">
			<h1>Gérez vos projets simplement avec <b>Dashlist</b></h1>
			<div>
				<p>
					+ Capture écran Dashlist
				</p>
				<p>
					Non, vraiment, ça apporterait un cachet
				</p>
				<p>
					Ca ferait genre logiciel pro et tout, qu'on rigole pas dans le métier quoi. 
				</p>
			</div>
			<div>
				<p>
					Présentation rapide de Dashlist
				</p>
				<p>
					Explication des fonctionnalités de base et les trucs classes que tu peux faire avec
				</p>
				<p>
					Après t'improvises, tu cherches ce qui est le mieux à mettre pour rendre le site bien 	
				</p>
			</div>
		</div>
	</div>
	<div id="footer">
		<div class="clearfix">
			<div id="connect">
				<a href="#" target="_blank" class="facebook"></a><a href="#" target="_blank" class="googleplus"></a><a href="#" target="_blank" class="twitter"></a><a href="#" target="_blank" class="tumbler"></a>
			</div>
			<p>
				© 2014 Dashlist. All Rights Reserved.
			</p>
		</div>
	</div>
</body>
</html>
<SCRIPT TYPE="text/javascript">
	var error_clbk = function(data) {
	var error = new generator.Message({ 
		type : 'error', 
		title : data.error.title, 
    	message : data.error.msg, 
        modal : true, dismissible : true, 
        disable : box.window });
		error.init();
	};

	var fail_clbk = function(data) {
	var error = new generator.Message({ 
		type : 'error', 
		title : "Erreur", 
        message : "Erreur lors du traitement du formulaire", 
        modal : true, dismissible : true, 
        disable : box.window });
		error.init();
	};
			
// Pop-up inscription
	$(".inscription").on("click", function(e) {

		var login = new generator.TextInput({ placeholder : "Nom du compte", min_size : 3, check_onkey : true, show_validation : true });
		var mail = new generator.EmailInput({ placeholder : "Adresse mail", check_onkey : true, show_validation : true });

		var pass = new generator.PasswordInput({ placeholder : 'Mot de passe', min_size : 3, check_onkey : true, show_validation : true, check_clbk : function() { return (pass_check.getValue() == pass.getValue()); }});
		var pass_check = new generator.PasswordInput({ placeholder : 'Vérification mot de passe', min_size : 3, check_onkey : true, check_clbk : function() { pass.check(); }});

		var firstname = new generator.TextInput({ placeholder : 'Prénom (Facultatif)'});
		var lastname = new generator.TextInput({ placeholder : 'Nom (Facultatif)'});

		box = new generator.TTBox( { width: 340, elements : 
		new generator.Form( { elements :
			[[{ item  : new generator.Title({ text: "Formulaire d'inscription" }), width : 4 }],
			 [{ label : 'Login', item : login, name : 'login' }],
			 [{ label : 'Adresse mail', item : mail, name : 'mail'}],
			 [],
			 [{ label : 'Mot de passe', item : pass, name : 'password' }],
			 [{ label : 'Vérification', item : pass_check }],
			 [],
			 [{ label : 'Prénom', item : firstname, name : 'firstname'}],
			 [{ label : 'Nom', item : lastname, name : 'lastname'}]
			], design : "table", target : "form - new.php", success_clbk : function(data) {
		
		var error = new generator.Message({ type : 'success', 
			title : data.success.title, 
			message : data.success.msg, 
			modal : true, dismissible : true, 
			disable : box.window });
		box.hide();
		error.init();
		}, error_clbk : error_clbk, fail_clbk : fail_clbk } )
	});

	box.init();
	box.show();
});
</SCRIPT>