var dropDownCountry;
var dataListStates;
var fieldState;

$(document).ready(function() {
	dropDownCountry = $("#country");
	dataListStates = $("#listStates");
	fieldState = $("#state");

	dropDownCountry.on("change", function() {
		loadStatesForCountry();
		fieldState.val("").focus();
	});
});

function loadStatesForCountry() {
	selectedCountry = $("#country option:selected");
	countryId = selectedCountry.val();
	url = contextPath + "settings/list_states_by-country/" + countryId;

	$.get(url, function(responseJSON) {
		dataListStates.empty();

		$.each(responseJSON, function(index, state) {
			$("<option>").val(state.name).text(state.name).appendTo(
				dataListStates);
		});
	});
}

function checkPasswordMatch(confirmPassword) {
	if (confirmPassword.value != $("#password").val()) {
		confirmPassword.setCustomValidity("Mật khẩu không khớp!")
	} else {
		confirmPassword.setCustomValidity("");
	}

}
