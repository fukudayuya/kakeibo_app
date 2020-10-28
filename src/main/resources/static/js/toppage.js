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

	$("#fortuneButton").click(function(){
		console.log("占いボタン押下");
		var sign = $("#selectsign").val();

		var dt = new Date();
		var year = dt.getFullYear();
		var month = ('00'+(dt.getMonth()+1)).slice(-2);
		var day = ('00'+(dt.getDate()+1)).slice(-2);

		var date = year + '/' + month + '/' + day;

		$.ajax({
			url:'//api.jugemkey.jp/api/horoscope/free/jsonp/'+date,
			dataType:'jsonp',
			jsonpCallback:"callback"

		})
		.done(function(data){
//			console.log('jsondata:'+JSON.stringify(data.horoscope[date][sign].content));
			var contentdata = JSON.stringify(data.horoscope[date][sign].content);
			var itemdata = JSON.stringify(data.horoscope[date][sign].item);
			$("#fortuneArea").html('<p>' + contentdata + '</p>' + '<p>ラッキーアイテム:' + itemdata );
//			alert('jsondata:'+data.horoscope.);
		});

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