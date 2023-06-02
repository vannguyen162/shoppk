var dropdownCountries;
var dropdownStates;

$(document).ready(function() {
	dropdownCountries = $("#country");
	dropdownStates = $("#listStates");

	dropdownCountries.on("change", function() {
		loadStates4Country();
		$("#state").val("").focus();
	});

	loadStates4Country();
});

function loadStates4Country() {
	selectedCountry = $("#country option:selected");
	countryId = selectedCountry.val();

	url = contextPath + "states/list_by_country/" + countryId;

	$.get(url, function(responseJSON) {
		dropdownStates.empty();

		$.each(responseJSON, function(index, state) {
			$("<option>").val(state.name).text(state.name).appendTo(dropdownStates);
		});
	}).fail(function() {
		showErrorModal("Lỗi: không thể cập nhật danh sách tỉnh thành!");
	});
}