$(".inscription").click(function(){
	$("#ttbox").css("height", "300px").css("width", "360px").css("margin-left", "-180px");
	$("#ttbox").load("forms/form_inscription.php");
	$("#ttglobal").fadeIn(200);
});
				
$(".connexion").click(function(){
	$("#ttbox").css("height", "210px").css("width", "360px").css("margin-left", "-180px");
	$("#ttbox").load("forms/form_connexion.php");
	$("#ttglobal").fadeIn(200);
});
				
$("#ttglobal").click(function(e) {
	if (!(e.target.id == "ttbox" || $(e.target).parents("#ttbox").size())) { 
		$("#ttglobal").fadeOut(200);
	} 
});

$(".newp").click(function(){
	$("#ttbox").css("height", "150px").css("width", "360px").css("margin-left", "-180px");
	$("#ttbox").load("/forms/form_projet.php");
	$("#ttglobal").fadeIn(200);
});