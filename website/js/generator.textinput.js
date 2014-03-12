/* =====================================================
			         generator.TextInput
	----------------------------------------------------
	Permet de gérer et de générer des inputs textuels
	de différents types.
	----------------------------------------------------
	Propriétés :
		render_to        : élément où afficher le formulaire (append)
		placeholder      : texte écrit quand le champ est vide
		min_size         : taille minimum (0 par défaut)
		max_size         : taille maximum (255 par défaut)
		check_onkey      : vérifie la valeur à chaque changement
		error_check_clbk : callback d'erreur de vérification
	    valid_check_clbk : callback de succès de vérification
	    check_clbk       : fonction supplémentaire de vérification
	                       (Doit renvoyer true ou false)
	    show_validation  : affiche l'état de la vérification

	Methodes :
		setRenderTo() : change le render_to 
		init()        : initialise l'objet 
		check()       : vérifie le validité du champ
		keyListener() : affecte une fonction à l'événement
		                keypress
		getValue()    : retourne la valeur du champ
   ===================================================== */

this.generator = this.generator || {};

generator.TextInput = function(args) {
	this.cls = "form-connection";
	this.type = "text";
	this.renderTo = args.render_to || false;
	this.placeholder = args.placeholder || "";
	this.min_size = args.min_size || 0;
	this.max_size = args.max_size || 255;
	this.disabled = args.disabled || false;
	this.check_onkey = args.check_onkey || false;
	this.error_check_callback = args.error_check_clbk || false;
	this.valid_check_callback = args.valid_check_clbk || false;
	this.check_callback = args.check_clbk || false;
	this.show_validation = args.show_validation || false;
}

generator.TextInput.prototype.setRenderTo = function(renderTo) {
	this.renderTo = renderTo;
}

generator.TextInput.prototype.check = function(e) {
	var me = this;

	if(me.element) {
		if(me.element.val().length <= this.max_size && me.element.val().length >= this.min_size) {
			if(!me.check_callback || me.check_callback(e, me.element)) {	
				if(me.valid_check_callback)
					me.valid_check_callback(e, me.element);

				if(me.show_validation) {
					me.validator.removeClass('form-connection-error');
					me.validator.addClass('form-connection-valid');
				}
				return true;
			}
		}
	}

	if(me.error_check_callback)
		me.error_check_callback(e, me.element);

	if(me.show_validation) {
		me.validator.addClass('form-connection-error');
		me.validator.removeClass('form-connection-valid');
	}

	return false;
}

generator.TextInput.prototype.init = function() {
	var me = this;

	if(me.renderTo) {
		me.element = $('<input />');

		me.element.attr("placeholder", this.placeholder);
		me.element.attr("maxlength", this.max_size);
		me.element.attr("type", this.type);
		if(me.disabled)
			me.element.attr('disabled', 'disabled');

		me.element.addClass(me.cls);
		me.element.addClass("form-connection");

		if(me.check_onkey) {
			me.element.on("input", function(){
				me.check();
			})
		}

		me.renderTo.append(me.element);

		if(me.show_validation) {
			me.validator = $('<div class="form-connection-info" />');
			me.renderTo.append(me.validator);
			me.element.css('float', 'left');
			me.element.css('display', 'inline-block');
		} else {
			me.element.css('width', '208px');
		}
	} else {
		console.log("Pas de renderTo défini");
	}
}

generator.TextInput.prototype.keyListener = function(f) {
	this.element.on('keypress', function(e) {
		f(e);
	});
}

generator.TextInput.prototype.getValue = function() {
	var me = this;

	if(me.element) {
		return me.element.val();
	} else {
		console.log("Pas d'element initialisé'");
		return false;
	}
}

/* =====================================================
			         generator.EmailInput
	----------------------------------------------------
	Permet de gérer et de générer des inputs textuels
	vérifiant une adresse mail.
	----------------------------------------------------
	Propriétés :
	    Voir inputText   
	Methodes :
	    Voir inputText      
   ===================================================== */

generator.EmailInput = function(args) {
	this.cls = "form-connection";
	this.type = "text";
	this.placeholder = args.placeholder || "";
	this.disabled = args.disabled || false;
	this.check_onkey = args.check_onkey || false;
	this.error_check_callback = args.error_check_clbk || false;
	this.valid_check_callback = args.error_check_clbk || false;
	this.check_callback = args.check_callback || false;
	this.show_validation = args.show_validation || false;
}

generator.EmailInput.prototype.check = function() {
	var me = this;
	var re = /\S+@\S+\.\S+/;

	if(me.element) {
		if(me.element.val().length > 0) {
			if(!me.check_callback || me.check_callback(e, me.element)) {
				if(re.test(me.element.val())) {	
					if(me.valid_check_callback)
						me.valid_check_callback(e, me.element);

					if(me.show_validation) {
						me.validator.removeClass('form-connection-error');
						me.validator.addClass('form-connection-valid');
					}
					return true;
				}
			}
		}
	}

	if(me.error_check_callback)
		me.error_check_callback(e, me.element);

	if(me.show_validation) {
		me.validator.addClass('form-connection-error');
		me.validator.removeClass('form-connection-valid');
	}

	return false;
}

generator.EmailInput.prototype.setRenderTo = generator.TextInput.prototype.setRenderTo;
generator.EmailInput.prototype.init = generator.TextInput.prototype.init;
generator.EmailInput.prototype.keyListener = generator.TextInput.prototype.keyListener;
generator.EmailInput.prototype.getValue = generator.TextInput.prototype.getValue;


/* =====================================================
			         generator.PasswordInput
	----------------------------------------------------
	Permet de gérer et de générer des inputs textuels
	pour mot de passe.
	----------------------------------------------------
	Propriétés :
	    Voir inputText   
	Methodes :
	    Voir inputText      
   ===================================================== */

generator.PasswordInput = function(args) {
	this.cls = "form-connection";
	this.type = "password";
	this.placeholder = args.placeholder || "";
	this.check_onkey = args.check_onkey || false;
	this.error_check_callback = args.error_check_clbk || false;
	this.valid_check_callback = args.error_check_clbk || false;
	this.check_callback = args.check_clbk || false;
	this.show_validation = args.show_validation || false;
	this.min_size = args.min_size || 0;
	this.max_size = args.max_size || 255;
	this.disabled = args.disabled || false;
}

generator.PasswordInput.prototype = generator.TextInput.prototype;