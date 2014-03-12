<?php
	include("header.php");
?>
	
	<div id="contents">
		<div class="section">
			<h1>Contact</h1>
			<p>
				Vous avez une question ?<br/>Vous avez un problème avec Dashlist ?<br/>Vous vous sentez mal-aimés et vous souhaitez juste parler à quelqu'un ?<br/>Alors envoyez-nous un mail via le formulaire ci-dessous et nous ferons en sorte d'y répondre après notre partie de golf sous-marin quotidienne.<br/><br/>Amicalement,<br/>TT & Teybeo & Guiks
			</p>
			<form action="index.html" method="post" class="message">
				<input type="text" placeholder="Votre nom" onFocus="this.select();" onMouseOut="javascript:return false;"/>
				<input type="text" placeholder="Votre adresse mail" onFocus="this.select();" onMouseOut="javascript:return false;"/>
				<input type="text" placeholder="Sujet" onFocus="this.select();" onMouseOut="javascript:return false;"/>
				<textarea></textarea>
				<input type="submit" value="Send"/>
			</form>
		</div>
		<div class="section contact">
			<p>
				Joignez-nous par téléphone: <span>01.23.45.67.89</span>
			</p>
			<p>
				Ou trouvez-nous à: <span>ESGI<br> 12 rue Erard <br/>75012 Paris</span>
			</p>
		</div>
	</div>
		<br/>	<br/>	<br/>	<br/>	<br/>	<br/>
<?php
	include("footer.php");
?>