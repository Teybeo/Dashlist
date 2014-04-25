<?php
	$bdd = new PDO('mysql:host=localhost;dbname=dashlist', 'root', '', array(PDO::MYSQL_ATTR_INIT_COMMAND => 'SET NAMES utf8'));
	$query = $bdd->prepare('SELECT * FROM user WHERE name="'.mysql_real_escape_string($_POST['login']).'"');
	$query->execute();
	
	$login = mysql_real_escape_string($_POST["login"]);
	$pass = mysql_real_escape_string($_POST["pass"]);
	$pass2 = mysql_real_escape_string($_POST["pass2"]);
	$mail = mysql_real_escape_string($_POST["mail"]);
	
	if($data = $query->fetch())
	{
		if(isset($data[1]))
		{
			echo '<span id="errorfield" class="texterror" style="color: red">Ce login est déjà utilisé</span>';
			exit();
		}
	}
	
	else if(empty($login) || empty($mail) || empty($pass) || empty($pass) || empty($pass2))
	{
		echo '<span id="errorfield" class="texterror" style="color: red">Tous les champs n\'ont pas été remplis</span>';
		exit();
	}
	
	else if(!filter_var($mail, FILTER_VALIDATE_EMAIL))
	{
		echo '<span id="errorfield" class="texterror" style="color: red">Le mail n\'est pas valide</span>';
		exit();
	}
		
	else if(strlen($login) <= 3)
	{
		echo '<span id="errorfield" class="texterror" style="color: red">Le login doit contenir au moins 4 caractères</span>';
		exit();
	}
	
	else if(strlen($login) >= 15)
	{
		echo '<span id="errorfield" class="texterror" style="color: red">Le login doit contenir moins de 15 caractères</span>';
		exit();
	}	
	else if($pass != $pass2)
	{
		echo '<span id="errorfield" class="texterror" style="color: red">Les mots de passe ne sont pas identiques</span>';
		exit();
	}
	
	else if(strlen($pass) < 5)
	{
		echo '<span id="errorfield" class="texterror" style="color: red">Le mot de passe doit contenir au moins 5 caractères</span>';
		exit();
	}
	
	echo '<span id="errorfield" class="textok" style="color: green">Inscription réussie !</span>';
	$query2 = $bdd->query("INSERT INTO user VALUES ('', '".$login."' , '".$pass."', '".$mail."')");
	exit();
		