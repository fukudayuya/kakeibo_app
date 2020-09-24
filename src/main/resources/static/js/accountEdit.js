$(function(){
	$("button").button();
	$("#form").submit(function(e){
		var checkSubmit = true;

//		名前の入力チェック
		if($("#name").val() == ""){
			$("#name").addClass("error");
			$("#name+div").text("名前を入力してください");
			checkSubmit = false;
		}else{
		}

//		メアドの入力チェック
		if($("#email").val().match(/^([a-zA-Z0-9])+([a-zA-Z0-9\._-])*@([a-zA-Z0-9_-])+([a-zA-Z0-9\._-]+)+$/)){
		}else{
			$("#email").addClass("error");
			$("#email+div").text("メールアドレスを入力してください");
			checkSubmit = false;
		}

//		パスワードの入力チェック
		if($("#password").val().match(/^[a-z\d]{8,12}$/i)){
			$("#password").removeClass("error");
			$("#password+div").text("");
			console.log("password:true");
		}else{
			console.log($("#password").val());
			$("#password").addClass("error");
			$("#password+div").text("半角英数字、8桁以上12桁未満で入力してください。");
			checkSubmit = false;
			console.log("password:false");
		}

//		確認用パスワードの入力チェック
		if($("#checkpassword").val().match(/^[a-z\d]{8,12}$/i)){
			$("#checkpassword").removeClass("error");
			$("#checkpassword+div").text("");
			console.log("checkpassword:true");
		}else{
			console.log($("#checkpassword").val());
			$("#checkpassword").addClass("error");
			$("#checkpassword+div").text("半角英数字、8桁以上12桁未満で入力してください。");
			checkSubmit = false;
			console.log("checkpassword:false");
		}

		return checkSubmit;
	});

});