<?php
		
		$id_projet= mysql_real_escape_string($_POST['id']);
		$nom_projet = mysql_real_escape_string($_POST['nom']);
		
		echo '<h1 id="'.$id_projet.'" style="font-size: 26pt"><b>'.$nom_projet.'</b></h1>';
		
		$bdd = new PDO('mysql:host=localhost;dbname=dashlist', 'root', '', array(PDO::MYSQL_ATTR_INIT_COMMAND => 'SET NAMES utf8'));
		$query_member = $bdd->prepare('SELECT * FROM board_members WHERE board_id=?');
		$query_member->execute(array($id_projet));
		
		echo '<table style="width: 92%" id="table_membres" class="'.$_POST["id"].'">';
		while($data = $query_member->fetch())
		{
			$query_user = $bdd->prepare('SELECT * FROM user WHERE id=?');
			$query_user->execute(array($data[1]));
			
			while($data2 = $query_user->fetch())
			{
				$is_admin = 0;
				
				echo '<tr id ="'.$data2[0].'">';
				
					if($data2[4] == 1)
						echo '<td><b>'.$data2[3].'</b></td><td>';
					else
						echo '<td><b>'.$data2[1].'</b></td><td>';
						
				if($data[2] == 1)
				{
					echo '<i>Administrateur</i>';
					$is_admin = 1;
				}
				
				else if($data2[4] == 1)
				{
					echo '<i>En attente d\'inscription</i>';
				}
				
				else
				{
					if($_POST['admin'] == 1)
						echo '<u><a>Promouvoir en Administrateur</a></u>';
					else
						echo '<i>Membre</i>';
				}
				echo '</td>';
				if($is_admin == 0 && $_POST['admin'] == 1)
				{
					echo '<td><img style="width: 30px; height: 30px;" src="../images/delete.png"/></td>';
				}
				echo '</tr>';
			}
		}
		echo '</table>';
	
?>