$(function(){

	$("button").button();
	$("input[type='checkbox']").checkboxradio({icon:true});
	$("select").selectmenu();

	var balance = $("#balanceplice").text();
	if (balance < 0){
		$("#balanceplice").addClass("colorred");
	}


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




	//前月比較ダイアログ表示
	$("#comparisionbutton").click(function(){
		$("#comparisiondialog").dialog({
			modal: true,
			width: 1050,
			buttons:{
				"閉じる":function(){
					$(this).dialog("close");
				}
			}
		});
	});

});