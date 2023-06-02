
function showModalDialog(title, message) {
	$('#modalTitle').text(title);
	$('#modalBody').text(message);
	$('#modalDialog').modal();

}
function showErrorModal(message) {
	showModalDialog("Thất bại", message);
}
function showWarningModal(message) {
	showModalDialog("Cảnh báo", message);
}