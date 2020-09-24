$(function(){
	$("input[type='submit']").button();

	$("#loginForm").submit(function(e){
		var checkSubmit = true;

//		メアドの入力チェック
		if($("#email").val().match(/^([a-zA-Z0-9])+([a-zA-Z0-9\._-])*@([a-zA-Z0-9_-])+([a-zA-Z0-9\._-]+)+$/)){
			checkSubmit = true;
		}else{
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

		return checkSubmit;
	});

});