/* =====================================================
			         generator.title
	----------------------------------------------------
	Permet de gérer et de générer des formulaires.
	----------------------------------------------------
	Propriétés :
		render_to    : élément où afficher le formulaire (append)
		elements     : tableau d'éléments composant le formulaire,
		               ces éléments peuvent doivent au moins
		               comporter un element item pour pouvoir
		               être utilisé. S'il comporte un champ name
		               et que item à une methode getValue, alors
		               leurs valeurs seront automatiquement utilisées 
		               pour être envoyée a target
		submit_value : valeur du bouton d'envoi
		target       : fichier à qui envoyer les données
		success_clbk : callback de retour positif de target
		error_clbk   : callback de retour négatif de target
		fail_clbk    : erreur lors de l'appel de target
		design       : flow | table - désigne le mode d'affichage
		               du formulaire. Si table est utilisé, alors
		               on peut donner une taille personnalisée à
		               un champ avec l'élement "width". Attention
		               utiliser des label avec les éléments ajoute
		               une nouvelle cellule.

	Methodes :
		setRenderTo() : change le render_to 
		init()        : initialise l'objet 
		check()       : vérifie le validité des champs
		send()        : vérifie et envoit les données, avant
		                de traiter la réponse
   ===================================================== */

this.generator = this.generator || {};

generator.Form = function(args) {
	var args = args || {};

	this.cls = "generated-form";
	this.renderTo = args.render_to || null;
	this.elements = args.elements || [];
	this.submit_value = args.submit_value || "Valider";
	this.target = args.target || "target.php";
	this.success_clbk = args.success_clbk;
	this.error_clbk = args.error_clbk;
	this.fail_clbk = args.error_ajax_clbk;
	this.design = args.design || "flow"; // flow ou table (voir + loin pour table)
	this.fields = [];
}

generator.Form.prototype.setRenderTo = function(renderTo) {
	this.renderTo = renderTo;
}

generator.Form.prototype.check = function(e) {

	var me = this;
	var result = true;

	$(me.fields).each(function(index, element) {
		if(element.check)
			if(element.check(e)) 
				result = result ? true : false;
			else
				result = false;
	});

	return result;
}

generator.Form.prototype.send = function(e) {
	var me = this;

	if(me.check()) {
		var to_send = {};
		$(me.fields).each(function(index, element) {
			to_send[element.name] = element.getValue();
		});

		$.ajax({ type     : 'POST',
	             url      :  me.target,
	             dataType : "json",
	             data     : { data : JSON.stringify({ data : to_send}) } })
	           .done(function(data) {
	           	   console.log(data);
	           	   if(data.success) {
	           	      if(me.success_clbk)
	                       me.success_clbk(data);
	           	   } else if(data.error) {
	           	   	  console.log("Erreur PHP");
	           	      if(me.error_clbk)
	                       me.error_clbk(data);
	           	   } else {
		           	   console.log("Erreur structure réponse");
		           	   if(me.fail_clbk)
		           	   	   me.fail_clbk();
	           	   }
	           })
	           .fail(function() {
	           	   console.log("Erreur Ajax");
	           	   if(me.fail_clbk)
	           	   	   me.fail_clbk();
	           });
    }
}

generator.Form.prototype.init = function() {
	var me = this;
	var keyListener = function(e) {
		var code = e.keyCode || e.which;

		 //Enter keycode
		if(code == 13) {
		   me.send(e);
		}

	};

	// Structure
	me.container = $('<div />');
	me.container.addClass(me.cls);
	me.renderTo.append(me.container);

	// Elements
	if(me.design == "flow") {

		var i = 0;

		$(me.elements).each(function(index, element) {
			
			if(element.item) {
				var div = $("<div/>");
				var label = $("<p/>");

				label.text(element.label);
				div.append(label);

				element.item.setRenderTo(div);
				element.item.init();

				if(element.item.keyListener)
					element.item.keyListener(keyListener);

				me.container.append(div);

				// Si l'élément à une valeur, un nom, et est activé
				if(element.item.getValue && element.name && !element.disabled) {
					element.item.name = element.name;
					me.fields[i++] = element.item;
				}
			}
		});

	} else if (me.design == "table") {

		var p = $("<p/>");
		var label = $("<p/>");
		var table = $("<table/>");

		table.addClass("form-table")

		var i = 0;

		$(me.elements).each(function(index, line_elements) {

			var line = $("<tr />");

			// On attend ici des lignes
			$(line_elements).each(function(index, element) {
				if(element.item) {
					if(element.label) {

						var label = $('<td />');

						label.text(element.label);
						line.append(label);
					}

					var data = $('<td />');

					if(element.width)
						data.attr("colspan", element.width);

					element.item.setRenderTo(data);
					element.item.init();

					if(element.item.keyListener)
						element.item.keyListener(keyListener);

					line.append(data);

					if(element.item.getValue && element.name) {
						element.item.name = element.name;
						me.fields[i++] = element.item;
					}
				}	
			});

			table.append(line);
		});

		me.container.append(table);
	}

	if(me.fields.length > 0)
		setTimeout( function() { me.fields[0].element.focus() }, 500 );

	// Submit
	me.submit = $('<input type="submit" class="btn" style="display: inline-block;" />');
	me.submit.val(this.submit_value);
	me.submit.on("click", function(e) {
		me.send(e);
	});
	var centered  = $('<p style="text-align: center; margin: 0;" />');
	centered.append(me.submit);
	me.container.append(centered);
}

