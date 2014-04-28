<?php
	include("header.php");
	
	if(!isset($_SESSION['login']))
	{
		echo "<script type='text/javascript'>document.location.replace('index.php');</script>";
	}
	
	$user_id = mysql_real_escape_string($_SESSION['id']);
	
	$bdd = new PDO('mysql:host=localhost;dbname=dashlist', 'root', '', array(PDO::MYSQL_ATTR_INIT_COMMAND => 'SET NAMES utf8'));
	
	$query_board_members = $bdd->prepare('SELECT * FROM board_members WHERE user_id=?');
	$query_board_members->execute(array($user_id));
	
	$projects1 = array();
	$projects2 = array();
	
	while($data_bm = $query_board_members->fetch())
	{
		$query_board = $bdd->prepare('SELECT * FROM board WHERE id =?');
		$query_board->execute(array($data_bm[0]));
		
		if($data_b = $query_board->fetch())
		{
			if($data_bm[2] == 1)
			{ 
				$projects1[$data_b[0]]= $data_b[1];
			}
			else
			{
				$projects2[$data_b[0]]= $data_b[1];
			}
		}
	}
?>
	<div id="contents">
		<div class="sidebar">
			<span class="btn newp">Creer un nouveau projet</span>
			<br/><br/>
			<div id="my_projects">
				<h2>Mes projets</h2>
				<hr/>
				<table class="projects_table" id="my_projects_table" style="width: 100%">
					<?php	
						foreach($projects1 as $cle =>$valeur){
							echo '<tr class="tr_projects" id="'.$cle.'"><td style="padding-top: 10px; padding-bottom: 10px; padding-left: 6px; width: 90%;">'.$valeur.'</td><td><b>></b></td></tr>';
						}
					?>
				</table>
				<br/>
				<hr size="3" color="black"/>
			</div>
			
			<div id="all_projects">
				<h2>Autres projets</h2>
				<hr/>
					<table class="projects_table" id="all_projects_table" style="width: 100%">
					<?php	
						foreach($projects2 as $cle =>$valeur){
							echo '<tr class="tr_projects" id="'.$cle.'"><td style="padding-top: 10px; padding-bottom: 10px; padding-left: 6px; width: 90%;">'.$valeur.'</td><td><b>></b></td></tr>';
						}
					?>
				</table>
			</div>
			
		</div>
		<div class="main" id="selected_project" style="display: none;">
			<div id="table_project"></div>
			<div id="add_member">
				<br/><br/>
				<h2><b>Ajouter un membre</b></h2>
					<p>Vous pouvez inviter d'autres personnes à rejoindre ce projet<br/>Il suffit d'entrer le pseudonyme ou l'adresse mail de votre contact</p>
					<input type="text" id="invite_field" placeholder="Entrez un nom ou une adresse mail" size="55" /> <input type="button" onclick="javascript:inviteMembre()" value="Inviter"/>
					<br/><span class="error"></span>
				
			</div>
		</div>
		<div class="main" id="no_selected_project" style="height: 400px;">
			Selectionnez un projet dans la liste de gauche ou creez en un <a class="newp" href="#">en cliquant ici</a>.
		</div>
		
	</div>
	
	<script type="text/javascript" src="../js/project.js"></script>
	
<?php
	include("footer.php");
?>