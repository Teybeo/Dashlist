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
		$("#table_project").delay(300).html(msg);
		$("#selected_project").fadeIn(300);
	});
	
	$("#no_selected_project").fadeOut(300);
});
