/* =====================================================
			         generator.title
	----------------------------------------------------
	Permet d'afficher un titre de type h3
	----------------------------------------------------
	Propriétés :
	    text         : texte du titre
		render_to    : élément où afficher le titre (append)   
	Methodes :
		init()       : initialise l'objet   
   ===================================================== */

this.generator = this.generator || {};

generator.Title = function(args) {
	var args = args || {};

	this.cls = "generated-title";
	this.text = args.text || "";
	this.renderTo = args.render_to || null;
}

generator.Title.prototype.setRenderTo = function(renderTo) {
	this.renderTo = renderTo;
}

generator.Title.prototype.init = function() {
	
	var me = this;

	if(me.renderTo) {
		me.container = $('<h3 />');
		me.container.addClass(me.cls);
		me.container.text(me.text);

		me.renderTo.append(me.container);
	}
}