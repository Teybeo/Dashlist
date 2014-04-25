<?php
	$bdd = new PDO('mysql:host=localhost;dbname=dashlist', 'root', '', array(PDO::MYSQL_ATTR_INIT_COMMAND => 'SET NAMES utf8'));
	$query = $bdd->prepare('SELECT * FROM user WHERE name="'.mysql_real_escape_string($_POST['login']).'"');
	$query->execute();
	
	if($data = $query->fetch())
	{
		if(isset($data[1]))
		{
			if(isset($_POST['password']) && $data[2] == $_POST['password'])
			{
				echo 'Vous êtes maintenant connecté';
				exit();
			}
			else
			{
				echo 'Mauvaise combinaison login/password'.$data[2];
				exit();
			}
		}
		else
		{
				echo "Ce login n'existe pas ";
				exit();
		}
	}
	
	else
	{
		echo $_POST['login'];
		exit();
	}
?>