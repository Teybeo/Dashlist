<?php
	include("header.php");
	
	if(!isset($_SESSION['login']))
	{
		echo "<script type='text/javascript'>document.location.replace('index.php');</script>";
	}
	
	$bdd = new PDO('mysql:host=localhost;dbname=dashlist', 'root', '', array(PDO::MYSQL_ATTR_INIT_COMMAND => 'SET NAMES utf8'));
	$query_board_members = $bdd->prepare('SELECT * FROM board_members WHERE user_id="'.mysql_real_escape_string($_SESSION['id']).'"');
	$query_board_members->execute();
	$projects1 = array();
	$projects2 = array();
	
	while($data_bm = $query_board_members->fetch())
	{
		$query_board = $bdd->prepare('SELECT * FROM board WHERE id ="'.$data_bm[0].'"');
		$query_board->execute();
		
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
		<div class="main" id="selected_project" style="display: none;"></div>
		<div class="main" id="no_selected_project" style="height: 400px;">
			Selectionnez un projet dans la liste de gauche ou creez en un <a class="newp" href="#">en cliquant ici</a>.
		</div>
	</div>
	
	<script type="text/javascript">
		$(".newp").click(function(){
			$("#ttbox").css("height", "150px").css("width", "360px").css("margin-left", "-180px");
			$("#ttbox").load("forms/form_projet.php");
			$("#ttglobal").fadeIn(200);
		});
		
		$('td').click(function(e){
			e.preventDefault();
			var id = $(this).parent().attr("id");
			var title = $(this).text();
			var admin = 0;
			$("#selected_project").fadeOut(300);
			if($(this).closest('table').attr('id') == "my_projects_table")
				admin = 1;
	
			$.ajax({
				type: "POST",
				url: "get_project.php",
				data: {	id: id,
						nom: title,
						admin: admin}
			})
			.done(function( msg ) {
				$("#selected_project").fadeIn(300);
				$("#selected_project").html(msg);
			});
			
			$("#no_selected_project").fadeOut(300);
		});
	</script>
	
<?php
	include("footer.php");
?>