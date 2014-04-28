<?php
	
	$login = mysql_real_escape_string($_POST['login']);
	$password = mysql_real_escape_string($_POST['password']);
	
	if(!isset($login) && basename($_SERVER['PHP_SELF']) == "connexion.php")
	{
		echo "<script type='text/javascript'>document.location.replace('index.php');</script>";
	}
	
	else
	{
		$bdd = new PDO('mysql:host=localhost;dbname=dashlist', 'root', '', array(PDO::MYSQL_ATTR_INIT_COMMAND => 'SET NAMES utf8'));
		$query = $bdd->prepare('SELECT * FROM user WHERE name=?');
		$query->execute(array($login));
		
		if($data = $query->fetch())
		{
			if(isset($data[1]))
			{
				if(!empty($password) && $data[2] == $password)
				{
					echo '<span id="errorfield" class="textok" style="color: green">Vous êtes maintenant connecté</span>';
					session_start();
					$_SESSION = array();
					$_SESSION['login'] = $login;
					$_SESSION['id'] = $data[0];
					exit();
				}
				else
				{
					echo '<span id="errorfield" class="texterror" style="color: red">Mauvaise combinaison login/password</span>';
					exit();
				}
			}
			else
			{
					echo "<span id='errorfield' class='texterror' style='color: red'>Ce login n'existe pas </span>";
					exit();
			}
		}
		
		else
		{
			echo "<span id='errorfield' class='texterror' style='color: red'>Ce login n'existe pas </span>";
			exit();
		}
	}
?>