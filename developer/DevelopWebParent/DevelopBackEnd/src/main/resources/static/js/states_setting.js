var buttonLoad4States;
var dropDownCountry4States;
var dropDownStates;
var buttonAddStates;
var buttonUpdateStates;
var buttonDeleteStates;
var lableStatesName;
var fieldStatesName;

$(document).ready(function(){
	buttonLoad4States = $("#buttonLoadCountriesForStates");
	dropDownCountry4States = $("#dropDownCountriesForStates");
	dropDownStates = $("#dropDownStates");
	buttonAddStates = $("#buttonAddState");
	buttonUpdateStates = $("#buttonUpdateState");
	buttonDeleteStates = $("#buttonDeleteState");
	lableStateName = $("#lableStateName");
	fieldStateName = $("#fieldStateName");
	
	buttonLoad4States.click(function(){
		loadCountries4States();
	});
	
	dropDownCountry4States.on("change", function(){
		loadStates4Country();
	});
	
	dropDownStates.on("change", function(){
		changeFormStateToSelectedState();
	});
	
	buttonAddStates.click(function(){
		if(buttonAddStates.val() == "Add"){
			addState();
		}else{
			changeFormStateToNew();
		}
	});
	
	buttonUpdateStates.click(function(){
		updateState();
	});
	
	buttonDeleteStates.click(function(){
		deleteState();
	});
});

function deleteState(){
	stateId = dropDownStates.val();
	
	url = contextPath + "states/delete/" + stateId;
	$.ajax({
		type: 'DELETE',
		url: url,
		beforeSend: function(xhr){
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		}
	}).done(function(){
		$("#dropDownStates option[value='" + stateId + "']").remove();
		changeFormStateToNew();
		showToastMessage("Đã xóa tỉnh/thành phố");
	}).fail(function(){
		showToastMessage("Lỗi: không thể kết nối tới máy chủ!");
	});	
}

function updateState(){
	if(!validateFormState()) return;
	
	url = contextPath + "states/save";
	stateId = dropDownStates.val();
	stateName = fieldStateName.val();
	
	selectedCountry = $("#dropDownCountriesForStates option:selected");
	countryId = selectedCountry.val();
	countryName = selectedCountry.text();
	
	jsonData = {id: stateId, name: stateName, country: {id: countryId, name: countryName}};
	
	$.ajax({
		type: 'POST',
		url: url,
		beforeSend: function(xhr){
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		},
		data: JSON.stringify(jsonData),
		contentType: 'application/json'
	}).done(function(stateId){
		$("#dropDownStates option:selected").text(stateName);
		showToastMessage("Đã cập nhật tỉnh/thành phố ");
		changeFormStateToNew();
	}).fail(function(){
		showToastMessage("Lỗi: không thể kết nối tới máy chủ!");
	});
}

function addState(){
	
	if(!validateFormCountry()) return;
	
	url = contextPath + "states/save";
	stateName = fieldStateName.val();
	
	selectedCountry = $("#dropDownCountriesForStates option:selected");
	countryId = selectedCountry.val();
	countryName = selectedCountry.text();
	
	jsonData = {name: stateName, country: {id: countryId, name: countryName}};
	
	$.ajax({
		type: 'POST',
		url: url,
		beforeSend: function(xhr){
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		},
		data: JSON.stringify(jsonData),
		contentType: 'application/json'
	}).done(function(stateId){
		selectNewlyAddedState(stateId, stateName);
		showToastMessage("Đã thêm tỉnh/thành phố mới!");
	}).fail(function(){
		showToastMessage("Lỗi: không thể kết nối tới máy chủ!")
	});
	
}

function validateFormCountry(){
	formState = document.getElementById("formState");
	if(!formState.checkValidity()){
		formState.reportValidity();
			return false;
	}
	return true;
}

function selectNewlyAddedState(stateId, stateName){
	$("<option>").val(stateId).text(stateName).appendTo(dropDownStates);
	
	$("#dropDownStates option[value='" + stateId + "']").prop("selected", true);
	
	fieldStatesName.val("").focus();	
}

function changeFormStateToNew(){
	buttonAddStates.val("Add");
	lableStatesName.text("Tên tỉnh/thành phố:");
	
	buttonUpdateStates.prop("disabled", true);
	buttonDeleteStates.prop("disabled", true);
	
	fieldStatesName.val("").focus();
	
}

function changeFormStateToSelectedState(){
	buttonAddStates.prop("value", "New");
	buttonUpdateStates.prop("disabled", false);
	buttonDeleteStates.prop("disabled", false);
	
	lableStatesName.text("Tỉnh/thành phố đã chọn:")
	
	selectedStateName = $("#dropDownStates option:selected").text();
	fieldStatesName.val(selectedStateName);
	
}

function loadStates4Country(){
	selectedCountry = $("#dropDownCountriesForStates option:selected");
	countryId = selectedCountry.val();
	url = contextPath + "states/list_by_country/" + countryId;
	
	$.get(url, function(responseJSON){
		dropDownStates.empty();
		
		$.each(responseJSON, function(index, state){
			$("<option>").val(state.id).text(state.name).appendTo(dropDownStates);
		});
		
	}).done(function(){
		changeFormStateToNew();
		showToastMessage("Đã load tỉnh/thành phố " + selectedCountry.text());
	}).fail(function(){
		showToastMessage("Lỗi: không thể kết nối tới máy chủ!")
	});
}

function loadCountries4States(){
	url = contextPath + "countries/list";
	
	$.get(url, function(responseJSON){
		dropDownCountry4States.empty();
		
		$.each(responseJSON, function(index, country){
			$("<option>").val(country.id).text(country.name).appendTo(dropDownCountry4States);
		});
		
	}).done(function(){
		buttonLoad4States.val("Cập nhật danh sách quốc gia");
		showToastMessage("Đã Load danh sách quốc gia");
	}).fail(function(){
		showToastMessage("Lỗi: không thể kết nối tới máy chủ!")
	});
}
