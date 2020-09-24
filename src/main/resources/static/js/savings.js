$(function(){
	$("button").button();
	$("input[type='button']").button();
	$("input[type='submit']").button();
	$("#savingForm").submit(function(e){
		var checkSubmit = true;

//		目標金額の入力チェック
		if($("#targetSaving").val().match(/^\d+$/)){
			console.log("targetSaving:true");
		}else{
			console.log($("#targetSaving").val());
			$("#targetSaving").addClass("error");
			$("#alert").text("半角数字で、かつ9桁以下で入力してください。");
			checkSubmit = false;
			console.log("targetSaving:false");
		}

		return checkSubmit;
	});

});