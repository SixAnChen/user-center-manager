import Vue from 'vue'
import App from './App.vue'
import router from './router'

import ElementUI from 'element-ui'; // ElementUI
import 'element-ui/lib/theme-chalk/index.css'; // ElementUi css
import "@/assets/iconfont/iconfont.css" // 自定义icon
import './assets/global.css' // 全局css
import 'highlight.js/styles/monokai-sublime.css'

Vue.use(ElementUI, {size: 'small'});
Vue.config.productionTip = false

new Vue({
    router,
    render: h => h(App)
}).$mount('#app')
