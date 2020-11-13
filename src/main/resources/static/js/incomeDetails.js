$(function(){

	$("input[type='submit']").button();
	$("#addlink").button();
	$("#toplink").button();
	$("button").button();
	$("#tabs").tabs();

	$(window).on('load',function(){


		//合計金額動的表示
		//アクティブになっているpriceクラスをhtmlで取得
		var $html = $('[aria-hidden="false"]').find('.price');

		var totalPrice = 0;
		var price = $html;

		//priceはリストで取得されているので、for文で一つずつ取り出し合計をだす。
		for(var i = 0;i < price.length;i++){

			//値段のテキストのみを取得しint型にキャスト
			var intprice = parseInt(price[i].textContent);
			totalPrice += intprice;

		}

		$('#totalPrice').text(totalPrice);

		//収入合計金額動的表示
		//アクティブになっているpriceクラスをhtmlで取得
		var $totalhtml = $('.tabContents').find('.price');


		var incomeTotalPrice = 0;
		var incomeprice = $totalhtml;

		//priceはリストで取得されているので、for文で一つずつ取り出し合計をだす。
		for(var i = 0;i < incomeprice.length;i++){

			//値段のテキストのみを取得しint型にキャスト
			var intprice = parseInt(incomeprice[i].textContent);
			incomeTotalPrice += intprice;

		}

		$('#incomeTotal').html('<span>'+incomeTotalPrice+'円</span>');
		$("select").selectmenu();

	});

	$(".tab a").click(function(){
//	 	$(this).parent().addClass("active")でクリックしたタブにclassのactiveを付与。
//		.siblings(".active").removeClass("active");で兄弟要素のactiveのついた<li>要素を取得して、removeしている。
//		$(this).parent().addClass("active").siblings(".active").removeClass("active");
//
//		//th:href属性の値を取得(th:はattrには不要)
//		var tabContents = $(this).attr("href");
//
//		//取得したth:href属性のdiv要素にクラスactiveを付与。それ以外のactiveクラスはremove
//		$(tabContents).addClass("active").siblings(".active").removeClass("active");


		var $clickhtml = $('[aria-hidden="false"]').find('.price');

		var clicktotalPrice = 0;
		var clickprice = $clickhtml;

		for(var i = 0;i < clickprice.length;i++){

			var clickintprice = parseInt(clickprice[i].textContent);
			clicktotalPrice += clickintprice;
		}

		console.log("incomeDetail:"+clicktotalPrice);

		$('span#totalPrice').text(clicktotalPrice);
	});

	$('.delete').click(function(){

		if(!confirm('削除してよろしいですか？')){
			return false;
		}else{
			return true;
		}

	})

});