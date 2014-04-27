function validerConnexion()
{
	$.ajax({
		type: "POST",
		url: "connexion.php",
		data: { login: $("#co_login").val(), password: $("#co_pass").val() }
	})
	.done(function( msg ) {
		$(".error").html(msg);
		if($("#errorfield").hasClass("textok"))
		{
			setTimeout(function(){ location.reload(); }, 1200);
		}
	});
}
		
function validerInscription()
{
	$.ajax({
		type: "POST",
		url: "inscription.php",
		data: { login: $("#ins_login").val(), 
				mail: $("#ins_mail").val(),
				pass: $("#ins_pass").val(),
				pass2: $("#ins_pass2").val()}
	})
	.done(function( msg ) {
		$(".error").html(msg);
		
		if($("#errorfield").hasClass("textok"))
		{
			$("#ttglobal").delay(600).fadeOut(200);
		}
	});
}