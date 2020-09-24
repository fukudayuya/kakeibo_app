$(function(){

	$("button").button();
	$("input[type='checkbox']").checkboxradio({icon:true});
	$("select").selectmenu();

	var balance = $("#balanceplice").text();
	if (balance < 0){
		console.log("収支が赤字");
		$("#balanceplice").addClass("colorred");
	}

	$("#select").ready(function(e){
		console.log("select");
		var year = 2020;
		var month = 1;

		for(var i = year; i <= 2050; i++){
			console.log("select2");
			$('#selectYear').append('<option value ="'+ i +'">'+ i + '</option>');
		}

		for(var i = month;i <=12; i++){
			console.log("select3");
			$("#selectMonth").append('<option value ="'+ i +'">'+ i + '</option>');
		}
	});

	$("#serchsubmit").submit(function(){
		console.log("年数チェックNG");
		var result = true;

		if($("#selectYear").val() == "---"){
			$("#select > .alert").html("検索対象年月を入力してください");
			console.log("年数チェックNG");
			result = false;
		}

		if($("#selectMonth").val() == "---"){
			$("#select > .alert").html("検索対象年月を入力してください");
			console.log("月チェックNG");
			result = false;
		}

		return result;

	});

});