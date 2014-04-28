<?php
	$entry = mysql_real_escape_string($_POST['entry']);
	$id_projet = mysql_real_escape_string($_POST['id']);
	
	if(!isset($_POST['entry']) && basename($_SERVER['PHP_SELF']) == "add_member.php")
	{
		echo "<script type='text/javascript'>document.location.replace('index.php');</script>";
	}
	
	$bdd = new PDO('mysql:host=localhost;dbname=dashlist', 'root', '', array(PDO::MYSQL_ATTR_INIT_COMMAND => 'SET NAMES utf8'));
	
	if(filter_var($entry, FILTER_VALIDATE_EMAIL))
	{
		$query_mail = $bdd->prepare('SELECT * FROM user WHERE mail=?');
		$query_mail->execute(array($entry));
		
		if($data = $query_mail->fetch())
		{
			$query_board_member = $bdd->prepare('SELECT * FROM board_members WHERE user_id=? AND board_id=?');
			$query_board_member->execute(array($data[0], $id_projet));
			
			if($data2 = $query_board_member->fetch())
			{
				echo '<span style="color: red">Ce user est déjà membre de ce projet</span>';
				exit();
			}
			
			else
			{
				$insert_member = $bdd->prepare("INSERT INTO board_members VALUES (?, ?, '0')");
				$insert_member->execute(array($id_projet, $data[0]));
				echo '<span style="color: green">L\'utilisateur "'.$entry.'" à été intégré au projet. Il pourra y participer dés son inscription sur Dashlist</span>';
				exit();
			}
		}
		
		else
		{
			$insert_member = $bdd->prepare("INSERT INTO user VALUES ('', '' , '', ?, 1)");
			$insert_member->execute(array($entry));
			$lastid = $bdd->lastInsertId();
			
			$insert_member2 = $bdd->prepare("INSERT INTO board_members VALUES (?, ?, '0')");
			$insert_member2->execute(array($id_projet, $lastid));
			echo '<span style="color: green">L\'utilisateur "'.$entry.'" à été intégré au projet. Il pourra y participer dés son inscription sur Dashlist</span>';
			exit();
		}
	}
	
	else
	{
		$query_user = $bdd->prepare('SELECT * FROM user WHERE  name=?');
		$query_user->execute(array($entry));
		
		if($data = $query_user->fetch())
		{
			$query_board_member = $bdd->prepare('SELECT * FROM board_members WHERE user_id=? AND board_id=?');
			$query_board_member->execute(array($data[0], $id_projet));
			
			if($data2 = $query_board_member->fetch())
			{
				echo '<span style="color: red">'.$entry.' est déjà membre de ce projet</span>';
				exit();
			}
			
			else
			{
				$insert_member = $bdd->prepare("INSERT INTO board_members VALUES (?, ?, '0')");
				$insert_member->execute(array($id_projet, $data[0]));
				echo '<span style="color: green">L\'utilisateur "'.$entry.'" à été intégré au projet</span>';
				exit();
			}
		}
		
		else
		{
			echo '<span style="color: red">Ce nom de compte n\'existe pas</span>';
			exit();
		}
	}