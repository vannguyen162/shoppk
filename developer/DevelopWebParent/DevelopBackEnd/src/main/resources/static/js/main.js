/*(function () {
	"use strict";

	var treeviewMenu = $('.app-menu');

	// Toggle Sidebar
	$('[data-toggle="sidebar"]').click(function(event) {
		event.preventDefault();
		$('.app').toggleClass('sidenav-toggled');
	});

	// Activate sidebar treeview toggle
	$("[data-toggle='treeview']").click(function(event) {
		event.preventDefault();
		if(!$(this).parent().hasClass('is-expanded')) {
			treeviewMenu.find("[data-toggle='treeview']").parent().removeClass('is-expanded');
		}
		$(this).parent().toggleClass('is-expanded');
	});

	// Set initial active toggle
	$("[data-toggle='treeview.'].is-expanded").parent().toggleClass('is-expanded');

	//Activate bootstrip tooltips
	$("[data-toggle='tooltip']").tooltip();

});
*/
/*
	$(document).ready(function() {
	   let v = document.getElementById('tbarcode').textContent
	   generatedBarcode(v)
	});
   function generatedBarcode (value){
		JsBarcode("#barcode", value,{
		format: 'code128',
		displayValue: false
		});
	}*/

	$(document).ready(function() {
		$("#logoutlink").on("click", function(e) {
			e.preventDefault();
			document.logoutForm.submit();
		});
		customizeTabs();
	});
	
	function customizeTabs(){
		var url = document.location.toString();
		if(url.match('#')){
			$('.nav-tabs a[href="#' + url.split('#')[1] + '"]').tab('show');
		}
		$('.nav-tabs a').on('shown.bs.tab', function(e){
			window.location.hash = e.target.hash;
		});
	}
    


