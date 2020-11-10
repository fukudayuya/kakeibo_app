$(function(){
	$("input[type='submit']").button();
	$("#addlink").button();
	$("#toplink").button();
	$("button").button();
	$("#tabs").tabs();



	$(window).on('load',function(){

		//年月プルダウン作成
		var todaydate = new Date();

		var year = todaydate.getFullYear();
		var month = todaydate.getMonth() + 1;

		//「年」プルダウン生成
		$('#year').html('<select name="year"></select>');

		//「年」プルダウンの中身を作成
		for(i = 2018; i <= year+3; i++){
			$('#year select').append('<option value=' + i + '>' + i +'</option>');
		}

		//id=yearの最後の要素に「年」を配置
		$('#year').append('年');

		//「月」プルダウンを作成
		$('#month').html('<select name="month"></select>');

		//「月」プルダウンの中身を作成
		for(i = 1; i <= 12; i++){
			$('#month select').append('<option value=' + i + '>'+ i +'</option>');
		}

		//id=monthの最後の要素に「月」を配置
		$('#month').append('月');

		//デフォルト表示
		$('select[name=year] option[value=' + year +']').prop('selected',true);
		$('select[name=month] option[value=' + month + ']').prop('selected',true);



		//支出合計金額動的表示
		var $totalhtml = $('.tabContents').find('.price');

		var spendTotalPrice = 0;
		var spendprice = $totalhtml;

		for(var i = 0;i < spendprice.length;i++){
			var intspend = parseInt(spendprice[i].textContent);
			spendTotalPrice += intspend;
		}
		$("#spendTotal").html('<span>'+spendTotalPrice+'円</span>');

		$("select").selectmenu();
	});



//	$(".tab a").click(function(){ タブ利用時
////	 	$(this).parent().addClass("active")でクリックしたタブにclassのactiveを付与。
////		.siblings(".active").removeClass("active");で兄弟要素のactiveのついた<li>要素を取得して、removeしている。
////		$(this).parent().addClass("active").siblings(".active").removeClass("active");
////
////		//th:href属性の値を取得(th:はattrには不要)
////		var tabContents = $(this).attr("href");
////
////		//取得したth:href属性のdiv要素にクラスactiveを付与。それ以外のactiveクラスはremove
////		$(tabContents).addClass("active").siblings(".active").removeClass("active");
//
//		var $clickhtml = $('[aria-hidden="false"]').find('.price');
//
//		var clicktotalPrice = 0;
//		var clickprice = $clickhtml;
//
//		for(var i = 0;i < clickprice.length;i++){
//
//			var clickintprice = parseInt(clickprice[i].textContent);
//			clicktotalPrice += clickintprice;
//		}
//
//
//		$('span#totalPrice').text(clicktotalPrice);
//		console.log("spendDetails:" + clicktotalPrice);
//
//	});

	$('.delete').click(function(){

		if(!confirm('削除してよろしいですか？')){
			return false;
		}else{
			return true;
		}

	});

//	$('#ui-id-3-button').change(function(){
//		console.log('ファンクションイン');
//		var val = $('select[name=genreselect]').val();
//		console.log('ファンクションval:' + val);
//		if (val == 'select') return;
//		$('section').fadeOut(300);
//		$('section#' + val ).fadeIn(300);
//		//滑らかにいかない
//	});

	$('#genremenu').selectmenu({
		change: function(event,ui){


			var val = $('select[name=genreselect]').val();
			if (val == 'select') return;
			$('section').fadeOut(0);
			$('section#' + val ).fadeIn(0);

			//合計金額動的表示
			//アクティブになっているpriceクラスをhtmlで取得
			var price = $('section#' + val).find('.price');

			console.log("アクティブ値段:" + price[0].textContent);

			var totalPrice = 0;

			//priceはリストで取得されているので、for文で一つずつ取り出し合計をだす。
			for(var i = 0;i < price.length;i++){

				//値段のテキストのみを取得しint型にキャスト
				var intprice = parseInt(price[i].textContent);
				totalPrice += intprice;

			}

			$('#totalPrice'+val).text(totalPrice);

		}
	});

});