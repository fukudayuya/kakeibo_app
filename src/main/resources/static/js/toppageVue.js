import Vue from 'vue'
import App from './App'
import router from './router'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
import 'material-design-icons-iconfont/dist/material-design-icons.css'
import '@mdi/font/css/materialdesignicons.css'

Vue.use(Vuetify);
Vue.config.productionTip = false;

document.addEventListener("DOMContentLoaded", function(event) {
	ELEMENT.locale(ELEMENT.lang.ja)
	new Vue({
	       el: "#app",
	       data: {
	         msg: "Welcome"
	       },
	       methods: {
	         sayHello(){
	           this.msg = "Hello!"
	         }
	       },
	       mounted(){
	         //表示後にやりたいことはここに書ける
	       }
	     });



});

