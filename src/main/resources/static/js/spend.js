$(function(){
	$("input[type='button']").button();
	$("input[type='submit']").button();
	$("button").button();
	$("select").selectmenu();
	$("#dateform input[type='text']").datepicker({
				dateformat:'yyyy-MM-dd'
			});

	$("#spendForm").submit(function(e){
		var checkSubmit = true;
		console.log("submit判定in");

		console.log("日付の状態:"+$("#date").val());

		//日付の入力チェック
		if($("#date").val() == ""){
			$("#dateform > .alert").html("日付を入力してください");//id:dateformの直下のalertクラスを指定してテキスト追加
			checkSubmit = false;
		}else{
			$("#dateform > .alert").html("");
			console.log("日付チェックOK");
		}

		//題目の入力チェック
		if($("#title").val().match(/[<(.*)>.*<\/\1>]/) || $("#title").val() == ""){//htmlコードの拒否
			$("#title").addClass("error");
			$("#titleform > .alert").html("題目を全角のひらがな、カタカナ、漢字もしくは半角英数字を入力してください");
			checkSubmit = false;
		}else{
			$("#titleform > .alert").html("");
			console.log("題目チェックOK");
		}

//		金額の入力チェック
		if($("#price").val().match(/[0-9]/)){
			$("#priceform > .alert").html("");
			console.log("金額チェックOK");
		}else{
			console.log("金額チェックNG");
			$("#price").addClass("error");
			$("#priceform > .alert").html("半角数字で入力してください。");
			checkSubmit = false;
		}

//		内容の入力チェック
		if($("#contents").val().match(/[<(.*)>.*<\/\1>]/) || $("#contents").val() == ""){
			console.log("内容チェックNG");
			$("#contents").addClass("error");
			$("#contentsform > .alert").text("内容を全角のひらがな、カタカナ、漢字もしくは半角英数字を入力してください");
			checkSubmit = false;
		}else{
			$("#contentsform > .alert").html("");
			console.log("内容チェックOK");
		}
		console.log("チェックサブミット:"+checkSubmit);
		return checkSubmit;
	});

});