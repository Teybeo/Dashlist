
this.html = this.html || {};




// Menu navigation
html.Menu = function(args) {
	this.cls = "generated-menu";
	this.renderTo = args.render_to;
	this.elements = args.elements || {};
}

html.Menu.prototype.init = function() {
	var me = this;
	me.container = $('<ul />');
	me.container.addClass(me.cls);

	$(this.elements).each(function(index, item) {
		var li = $("<li/>");
		li.text(item.label);

		if(item.click)
			$(li).on("click", function(e) {
				item.click(e);
			});
		
		me.container.append(li);
	});

	me.renderTo.append(me.container);
};



/*
// Input type téléphone
html.PhoneNumberInput = function(placeholder,error_check_callback, valid_check_callback, check_callback) {
	this.cls = "myPhoneNumberInput";
	this.type = "text";
	this.placeholder = placeholder || "";
	this.error_check_callback = error_check_callback;
	this.valid_check_callback = valid_check_callback;
	this.check_callback = check_callback;
	this.regex = /(\+\d+(\s|-))?0\d(\s|-)?(\d{2}(\s|-)?){4}/;
}

// Héritage de phoneNumber
html.PhoneNumberInput.prototype.setRenderTo = html.TextInput.prototype.setRenderTo;
html.PhoneNumberInput.prototype.init = html.TextInput.prototype.init;
html.PhoneNumberInput.prototype.keyListener = html.TextInput.prototype.keyListener;
html.PhoneNumberInput.prototype.getValue = html.TextInput.prototype.getValue;

html.PhoneNumberInput.prototype.check = function(e) {
	var me = this;

	if(me.element) {
		if(me.element.val().match(me.regex)) {
			if(!me.check_callback || me.check_callback(e, me.element)) {	
				if(me.valid_check_callback)
					me.valid_check_callback(e, me.element);
				return true;
			}
		}
	}
	if(me.error_check_callback)
		me.error_check_callback(e, me.element);

	return false;
}
*/
