function clearFilter(){
	window.location = moduleURL;
}
function showDeleteConfirmModal(link, entityName){
	entityId = link.attr("entityId");
	
	$("#yesButton").attr("href", link.attr("href"));
	$("#confirmText").text("Bạn muốn xóa " + entityName + "?");
	$("#confirmModal").modal();
}

function showDeleteConfirmUserModal(link, entityName){
	entityId = link.attr("entityId");
	
	$("#yesButton").attr("href", link.attr("href"));
	$("#confirmText").text("Bạn muốn xóa " + entityName + "?");
	$("#confirmModal").modal();
}
