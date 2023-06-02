var extraImagesCount = 0;

$(document).ready(function() {
	$("input[name='extraImage']").each(function(index){
		extraImagesCount++;
		
		$(this).change(function(){
			if(!checkFileSize(this)){
				return;
			}
			showExtraImageThubnail(this, index);
		});
	});
	
	$("a[name='linkRemoveExtraImage']").each(function(index){
		$(this).click(function(){
			removeExtraImage(index);
		});
	});
});

function showExtraImageThubnail(fileInput, index){
	var file = fileInput.files[0];
	
	fileName = file.name;
	
	imageNameHiddenField = $("#imageName" + index);
	if(imageNameHiddenField.length){
		imageNameHiddenField.val(fileName);
	}
	
	var reader = new FileReader();
	reader.onload = function(e){
		$("#extraThumbnail" + index).attr("src", e.target.result);
		
	};
	reader.readAsDataURL(file);
	
	if(index >= extraImagesCount - 1){
		addNextExtraImageSection(index + 1);
	}
}

function addNextExtraImageSection(index){
	htmlExtraImage = `
			<div class="col border m-3 p-2" id="divExtraImage${index}">
				<div id="extraImageHeader${index}"><label>Ảnh Chi tiết ${index + 1}:</label></div>
				<div class="m-2">
					<img id="extraThumbnail${index}" alt="Extra image ${index + 1} preview" class="img-fluid" width="200px" height="200px"
					 src="${defaultImageThumbnailSrc}" />
				</div>
				<div>
					<input type="file" name="extraImage"
						onchange="showExtraImageThubnail(this, ${index})"
						accept="image/png , image/jpeg"/>
				</div>
			</div>	
	`;
	
	htmlLinkRemove = `
			<a class="btn fas fa-times-circle fa-3x float-right" 
				href="javascript:removeExtraImage(${index - 1})"
				title="Xóa ảnh này"></a>
	`;
	
	$("#divProductImages").append(htmlExtraImage);
	
	$("#extraImageHeader" + (index - 1)).append(htmlLinkRemove);
	
	extraImagesCount++;
}

function removeExtraImage(index){
	$("#divExtraImage" + index).remove();
	
}

