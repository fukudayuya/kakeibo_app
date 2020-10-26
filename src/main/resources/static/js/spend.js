$(function(){
	$("input[type='button']").button();
	$("input[type='submit']").button();
	$("button").button();
	$("select").selectmenu();
	$("#dateform input[type='text']").datepicker({
				dateformat:'yyyy-MM-dd'
			});

	////////////////１つ目//////////////////////
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
			$("#titleform > .alert").html("題目を全角のひらがな、カタカナ、<p>漢字もしくは半角英数字を入力してください");
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
			$("#contentsform > .alert").html("内容に全角のひらがな、カタカナ、<p>漢字もしくは半角英数字を入力してください");
			checkSubmit = false;
		}else{
			$("#contentsform > .alert").html("");
			console.log("内容チェックOK");
		}
		console.log("チェックサブミット:"+checkSubmit);
		return checkSubmit;
	});

	////////////////2つ目//////////////////////
	$("#spendForm2").submit(function(e){
		var checkSubmit = true;
		console.log("submit判定in");

		console.log("日付の状態:"+$("#date").val());

		//題目の入力チェック
		if($("#title2").val().match(/[<(.*)>.*<\/\1>]/) || $("#title2").val() == ""){//htmlコードの拒否
			$("#title2").addClass("error");
			$("#titleform2 > .alert").html("題目を全角のひらがな、カタカナ、<p>漢字もしくは半角英数字を入力してください");
			checkSubmit = false;
		}else{
			$("#titleform2 > .alert").html("");
			console.log("題目チェックOK");
		}

//		金額の入力チェック
		if($("#price2").val().match(/[0-9]/)){
			$("#priceform2 > .alert").html("");
			console.log("金額チェックOK");
		}else{
			console.log("金額チェックNG");
			$("#price2").addClass("error");
			$("#priceform2 > .alert").html("半角数字で入力してください。");
			checkSubmit = false;
		}

//		内容の入力チェック
		if($("#contents2").val().match(/[<(.*)>.*<\/\1>]/) || $("#contents2").val() == ""){
			console.log("内容チェックNG");
			$("#contents2").addClass("error");
			$("#contentsform2 > .alert").html("内容に全角のひらがな、カタカナ、<p>漢字もしくは半角英数字を入力してください");
			checkSubmit = false;
		}else{
			$("#contentsform2 > .alert").html("");
			console.log("内容チェックOK");
		}
		console.log("チェックサブミット:"+checkSubmit);
		return checkSubmit;
	});

	////////////////3つ目//////////////////////
	$("#spendForm3").submit(function(e){
		var checkSubmit = true;
		console.log("submit判定in");

		console.log("日付の状態:"+$("#date").val());

		//題目の入力チェック
		if($("#title3").val().match(/[<(.*)>.*<\/\1>]/) || $("#title3").val() == ""){//htmlコードの拒否
			$("#title3").addClass("error");
			$("#titleform3 > .alert").html("題目を全角のひらがな、カタカナ、<p>漢字もしくは半角英数字を入力してください");
			checkSubmit = false;
		}else{
			$("#titleform3 > .alert").html("");
			console.log("題目チェックOK");
		}

//		金額の入力チェック
		if($("#price3").val().match(/[0-9]/)){
			$("#priceform3 > .alert").html("");
			console.log("金額チェックOK");
		}else{
			console.log("金額チェックNG");
			$("#price3").addClass("error");
			$("#priceform3 > .alert").html("半角数字で入力してください。");
			checkSubmit = false;
		}

//		内容の入力チェック
		if($("#contents3").val().match(/[<(.*)>.*<\/\1>]/) || $("#contents3").val() == ""){
			console.log("内容チェックNG");
			$("#contents3").addClass("error");
			$("#contentsform3 > .alert").html("内容に全角のひらがな、カタカナ、<p>漢字もしくは半角英数字を入力してください");
			checkSubmit = false;
		}else{
			$("#contentsform3 > .alert").html("");
			console.log("内容チェックOK");
		}
		console.log("チェックサブミット:"+checkSubmit);
		return checkSubmit;
	});

	////////////////4つ目//////////////////////
	$("#spendForm4").submit(function(e){
		var checkSubmit = true;
		console.log("submit判定in");

		console.log("日付の状態:"+$("#date").val());

		//題目の入力チェック
		if($("#title4").val().match(/[<(.*)>.*<\/\1>]/) || $("#title4").val() == ""){//htmlコードの拒否
			$("#title4").addClass("error");
			$("#titleform4 > .alert").html("題目を全角のひらがな、カタカナ、<p>漢字もしくは半角英数字を入力してください");
			checkSubmit = false;
		}else{
			$("#titleform4 > .alert").html("");
			console.log("題目チェックOK");
		}

//		金額の入力チェック
		if($("#price4").val().match(/[0-9]/)){
			$("#priceform4 > .alert").html("");
			console.log("金額チェックOK");
		}else{
			console.log("金額チェックNG");
			$("#price4").addClass("error");
			$("#priceform4 > .alert").html("半角数字で入力してください。");
			checkSubmit = false;
		}

//		内容の入力チェック
		if($("#contents4").val().match(/[<(.*)>.*<\/\1>]/) || $("#contents4").val() == ""){
			console.log("内容チェックNG");
			$("#contents4").addClass("error");
			$("#contentsform4 > .alert").html("内容に全角のひらがな、カタカナ、<p>漢字もしくは半角英数字を入力してください");
			checkSubmit = false;
		}else{
			$("#contentsform4 > .alert").html("");
			console.log("内容チェックOK");
		}
		console.log("チェックサブミット:"+checkSubmit);
		return checkSubmit;
	});

	////////////////5つ目//////////////////////
	$("#spendForm5").submit(function(e){
		var checkSubmit = true;
		console.log("submit判定in");

		console.log("日付の状態:"+$("#date").val());

		//題目の入力チェック
		if($("#title5").val().match(/[<(.*)>.*<\/\1>]/) || $("#title5").val() == ""){//htmlコードの拒否
			$("#title5").addClass("error");
			$("#titleform5 > .alert").html("題目を全角のひらがな、カタカナ、<p>漢字もしくは半角英数字を入力してください");
			checkSubmit = false;
		}else{
			$("#titleform5 > .alert").html("");
			console.log("題目チェックOK");
		}

//		金額の入力チェック
		if($("#price5").val().match(/[0-9]/)){
			$("#priceform5 > .alert").html("");
			console.log("金額チェックOK");
		}else{
			console.log("金額チェックNG");
			$("#price5").addClass("error");
			$("#priceform5 > .alert").html("半角数字で入力してください。");
			checkSubmit = false;
		}

//		内容の入力チェック
		if($("#contents5").val().match(/[<(.*)>.*<\/\1>]/) || $("#contents4").val() == ""){
			console.log("内容チェックNG");
			$("#contents5").addClass("error");
			$("#contentsform5 > .alert").html("内容に全角のひらがな、カタカナ、<p>漢字もしくは半角英数字を入力してください");
			checkSubmit = false;
		}else{
			$("#contentsform5 > .alert").html("");
			console.log("内容チェックOK");
		}
		console.log("チェックサブミット:"+checkSubmit);
		return checkSubmit;
	});

	//ダイアログ表示
	$("#autobutton").click(function(){
		console.log("dialogへイン");
		$("#autodialog").dialog({
			modal: true,
			width: 700,
			buttons:{
				"閉じる":function(){
					$(this).dialog("close");
				}
			}
		});
	});

	//ダイアログの値を取得
	$(".autopush").on("click",function(){
		console.log("プッシュ");
		var genre = $(this).closest('tr').children("td").children("select").children("option:selected").val();
		var title = $(this).closest('tr').children("td")[1].innerText;
		var price = $(this).closest('tr').children("td")[2].innerText;
		var contents = $(this).closest('tr').children("td")[3].innerText;
		$("#genreid").val(genre);
		$("#genreid").selectmenu("refresh");
		$("input[name=title]").val(title);
		$("input[name=price]").val(price);
		$("textarea").val(contents);
		$(".ui-dialog-content").dialog("close");

	});

});
