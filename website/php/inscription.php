<?php

	if(!isset($_POST['login']) && basename($_SERVER['PHP_SELF']) == "inscription.php")
	{
		echo "<script type='text/javascript'>document.location.replace('index.php');</script>";
	}
	
	else
	{	
		$login = mysql_real_escape_string($_POST["login"]);
		$pass = mysql_real_escape_string($_POST["pass"]);
		$pass2 = mysql_real_escape_string($_POST["pass2"]);
		$mail = mysql_real_escape_string($_POST["mail"]);
		
		$bdd = new PDO('mysql:host=localhost;dbname=dashlist', 'root', '', array(PDO::MYSQL_ATTR_INIT_COMMAND => 'SET NAMES utf8'));
		$query = $bdd->prepare('SELECT * FROM user WHERE name=?');
		$query->execute(array($login));
		
		//Le login est déjà utilisé
		if($data = $query->fetch())
		{
			if(isset($data[1]))
			{
				echo '<span id="errorfield" class="texterror" style="color: red">Ce login est déjà utilisé</span>';
				exit();
			}
		}
		
		//Un ou plusieurs champs sont vides
		else if(empty($login) || empty($mail) || empty($pass) || empty($pass) || empty($pass2))
		{
			echo '<span id="errorfield" class="texterror" style="color: red">Tous les champs n\'ont pas été remplis</span>';
			exit();
		}
		
		//Le format de mail n'est pas valide
		else if(!filter_var($mail, FILTER_VALIDATE_EMAIL))
		{
			echo '<span id="errorfield" class="texterror" style="color: red">Le mail n\'est pas valide</span>';
			exit();
		}
		
		//Login trop court
		else if(strlen($login) <= 3)
		{
			echo '<span id="errorfield" class="texterror" style="color: red">Le login doit contenir au moins 4 caractères</span>';
			exit();
		}
		
		//Login trop long
		else if(strlen($login) >= 15)
		{
			echo '<span id="errorfield" class="texterror" style="color: red">Le login doit contenir moins de 15 caractères</span>';
			exit();
		}	
		
		//MDP1 != MDP2
		else if($pass != $pass2)
		{
			echo '<span id="errorfield" class="texterror" style="color: red">Les mots de passe ne sont pas identiques</span>';
			exit();
		}
		
		//MDP trop court
		else if(strlen($pass) < 5)
		{
			echo '<span id="errorfield" class="texterror" style="color: red">Le mot de passe doit contenir au moins 5 caractères</span>';
			exit();
		}
		
		$query2 = $bdd->prepare('SELECT * FROM user WHERE mail=?');
		$query2->execute(array($mail));

		if ($data2 = $query2->fetch()) 
		{
			//Mail ajouté à un tableau dont l'einscription se complète
			if($data2[4] == 1)
			{
				echo '<span id="errorfield" class="textok" style="color: green">Inscription réussie !</span>';
				$query2 = $bdd->prepare("UPDATE user SET name =?, password=?, is_pending='0' WHERE mail=?");
				$query2->execute(array($login, $pass, $mail));
				exit();
			}
			
			//Mail déjà inscrit
			else
			{
				echo '<span id="errorfield" class="texterror" style="color: red">Le mail est déjà utilisé</span>';
				exit();
			}
		}
		
		//Sinon, inscription classique
		else 
		{
			echo '<span id="errorfield" class="textok" style="color: green">Inscription réussie !</span>';
			$query2 = $bdd->prepare("INSERT INTO user VALUES ('', ? , ?, ?, 0)");
			$query2->execute(array($login, $pass, $mail));
			exit();
		}
	}
?>
		