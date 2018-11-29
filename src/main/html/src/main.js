import Vue from 'vue/dist/vue.js'
import VueRouter from 'vue-router'

import cognitoAuth from './cognito'

import createSchedule from './components/createSchedule'
import viewSchedule from './components/viewSchedule'
import login from './components/login'

import './style/base.scss'

Vue.use(VueRouter);

let router = new VueRouter({
  routes: [
	{
		path: '/createSchedule',
		component: createSchedule
    },{
    	path: '/viewSchedule',
    	component: viewSchedule
    },{
    	path: '/login',
    	component: login
    }
  ]
});

new Vue({
  el: '#app',
  router,
  cognitoAuth
})