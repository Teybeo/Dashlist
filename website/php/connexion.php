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
				echo '<span id="errorfield" class="textok" style="color: green">Vous êtes maintenant connecté</span>';
				session_start();
				$_SESSION = array();
				$_SESSION['login'] = $_POST['login'];
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
		echo $_POST['login'];
		exit();
	}
?>