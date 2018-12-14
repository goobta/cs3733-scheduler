import Vue from 'vue/dist/vue.js'
import VueRouter from 'vue-router'

// import cognitoAuth from './cognito'

import createSchedule from './components/createSchedule'
import viewSchedule from './components/viewSchedule'
import login from './components/login'
import sysAdminSearch from './components/sysAdminSearch'
import searchSchedule from './components/searchSchedule'
import searchScheduleOrganizer from './components/searchScheduleOrganizer'
import editSchedule from './components/editSchedule'
import scheduleSearch from './components/scheduleSearch'

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
    },{
      path: '/sysAdminSearch',
      component: sysAdminSearch
    },{
      path: '/searchSchedule',
      component: searchSchedule
    },{
      path: '/searchScheduleOrganizer',
      component: searchScheduleOrganizer
    },{
      path: '/editSchedule',
      component: editSchedule
    },{
      path: '/scheduleSearch',
      component: scheduleSearch
    }
  ]
});

new Vue({
  el: '#app',
  router
  //cognitoAuth
})