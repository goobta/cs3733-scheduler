import Vue from 'vue/dist/vue.js'
import VueRouter from 'vue-router'

import createSchedule from './components/createSchedule'
import viewSchedule from './components/viewSchedule'

import './style/base.scss'

Vue.use(VueRouter)

let router = new VueRouter({
  routes: [
	{
		path: '/createSchedule',
		component: createSchedule
    },{
    	path: '/viewSchedule',
    	component: viewSchedule
    }

  ]
});

new Vue({
  el: '#app',
  router
})