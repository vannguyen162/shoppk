decimalSeparator = decimalPointType == 'COMMA' ? ',' : '.';
thousandsSeparator = thousandsPointType == 'COMMA' ? ',' : '.';

$(document).ready(function(){
	$(".linkMinus").on("click", function(evt){
		evt.preventDefault();
		decreaseQuanttity($(this));
		
	});
	$(".linkPlus").on("click", function(evt){
		evt.preventDefault();
		increaseQuanttity($(this));
	});
	$(".linkRemove").on("click", function(evt){
		evt.preventDefault();
		removeProduct($(this));
	});
});

function decreaseQuanttity(link){
	productId = link.attr("pid");
	quantityInput = $("#quantity" + productId);
	newQuantity = parseInt(quantityInput.val()) - 1;

	if (newQuantity > 0) {
		quantityInput.val(newQuantity);
		updateQuantity(productId, newQuantity);
	} else {
		showWarningModal('Số lượng không được nhỏ hơn 1');
	}
}
function increaseQuanttity(link) {
	productId = link.attr("pid");
	quantityInput = $("#quantity" + productId);
	newQuantity = parseInt(quantityInput.val()) + 1;
	
	if (newQuantity <= 5) {
		quantityInput.val(newQuantity);
		updateQuantity(productId, newQuantity);
	} else {
		showWarningModal('Số lượng không vượt quá 5');
	}
}

function updateQuantity(productId, quantity) {
	url = contextPath + "cart/update/" + productId + "/" + quantity;
	$.ajax({
		type: "POST",
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		}
	}).done(function(updatedSubtotal) {
		updateSubtotal(updatedSubtotal, productId);
		updateTotal();
	}).fail(function() {
		showErrorModal("Đã vượt quá sản phẩm hiện có trong kho!");
	});
}

function updateSubtotal(updatedSubtotal, productId) {
	$("#subtotal" + productId).text(formatCurrency(updatedSubtotal));
}
function updateTotal() {
	estimateToltal = 0.0;
	productCount = 0;
	
	$(".subtotal").each(function(index, element) {
		productCount++;
		estimateToltal += parseFloat(clearCurrencyFormat(element.innerHTML));
	});

	if(productCount < 1){
		showEmptyShoppingCart();
	} else {
		$("#estimateToltal").text(formatCurrency(estimateToltal));
	}
}

function showEmptyShoppingCart(){
	$("#sectionTotal").hide();
	$("#sectionEmptyCartMessage").removeClass("d-none");
	$("#sectionEmptyCartMessage1").removeClass("d-none1");
}

function removeProduct(link){
	url = link.attr("href");
	$.ajax({
		type: "DELETE",
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		}
	}).done(function(response) {
		rowNumber = link.attr("rowNumber");
		removeProductHTML(rowNumber);
		updateTotal();
		updateCountNumbers();
		showModalDialog("Giỏ hàng", response);
	}).fail(function() {
		showErrorModal("Không thể xóa sản phẩm khỏi giỏ hàng!")
	});
}
function removeProductHTML(rowNumber){
	$("#row" + rowNumber).remove();
}
function updateCountNumbers(){
	$(".divCount").each(function(index, element){
		element.innerHTML = "" + (index + 1);
	});
}

function formatCurrency(amount){
	return $.number(amount, decimalDigits, decimalSeparator, thousandsSeparator);
}

function clearCurrencyFormat(numberString){
	result = numberString.replaceAll(thousandsSeparator, "");
	return result.replaceAll(decimalSeparator, ".");
}

