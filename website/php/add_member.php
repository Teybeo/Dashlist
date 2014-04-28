<?php
	if(!isset($_POST['entry']) && basename($_SERVER['PHP_SELF']) == "add_member.php")
	{
		//echo "<script type='text/javascript'>document.location.replace('index.php');</script>";
	}
	
	$entry = $_POST['entry'];
	$bdd = new PDO('mysql:host=localhost;dbname=dashlist', 'root', '', array(PDO::MYSQL_ATTR_INIT_COMMAND => 'SET NAMES utf8'));
	
	if(filter_var($entry, FILTER_VALIDATE_EMAIL))
	{
		$query_mail = $bdd->prepare('SELECT * FROM user WHERE mail ="'.mysql_real_escape_string($entry).'"');
		$query_mail->execute();
		
		if($data = $query_mail->fetch())
		{
			$query_board_member = $bdd->prepare('SELECT * FROM board_members WHERE user_id="'.$data[0].'" AND board_id="'.$_POST['id'].'"');
			$query_board_member->execute();
			
			if($data2 = $query_board_member->fetch())
			{
				echo '<span style="color: red">Ce user est déjà membre de ce projet</span>';
				exit();
			}
			
			else
			{
				$insert_member = $bdd->query("INSERT INTO board_members VALUES ('".$_POST['id']."'', '".$data[0]."'' , '0')");
				echo '<span style="color: green">L\'utilisateur "'.$entry.'" à été intégré au projet. Il pourra y participer dés son inscription sur Dashlist</span>';
				exit();
			}
		}
		
		else
		{
			$insert_member = $bdd->query("INSERT INTO user VALUES ('', '' , '', '".$entry."', 1)");
			$lastid = $bdd->lastInsertId();
			$insert_member2 = $bdd->query("INSERT INTO board_members VALUES ('".$_POST['id']."', '".$lastid."', '0')");
			echo '<span style="color: green">L\'utilisateur "'.$entry.'" à été intégré au projet. Il pourra y participer dés son inscription sur Dashlist</span>';
			exit();
		}
	}
	
	else
	{
		$query_user = $bdd->prepare('SELECT * FROM user WHERE  name="'.mysql_real_escape_string($entry).'"');
		$query_user->execute();
		
		if($data = $query_user->fetch())
		{
			$query_board_member = $bdd->prepare('SELECT * FROM board_members WHERE user_id="'.$data[0].'" AND board_id="'.$_POST['id'].'"');
			$query_board_member->execute();
			
			if($data2 = $query_board_member->fetch())
			{
				echo '<span style="color: red">'.$entry.' est déjà membre de ce projet</span>';
				exit();
			}
			
			else
			{
				$insert_member = $bdd->query("INSERT INTO board_members VALUES ('".$_POST['id']."', '".$data[0]."', '0')");
				echo '<span style="color: green">L\'utilisateur "'.$entry.'" à été intégré au projet</span>';
				exit();
			}
		}
		
		else
		{
			echo 'Ce nom de compte n\'existe pas';
			exit();
		}
	}