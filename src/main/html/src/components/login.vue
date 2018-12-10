<template>
	<div id='app'>
		<div class='field is-horizontal'>
	      <div class='field-label is-normal'>
	        <label class="label">Username</label>
	      </div>
	      <div class='field-body'>
	        <div class='field'>
	          <p class='controll'>
	            <input v-model="user.name" class="input" type="text">
	          </p>
	        </div>
	      </div>
	    </div>

		<div v-if='creating && !confirmAccount' class='field is-horizontal'>
	      <div class='field-label is-normal'>
	        <label class="label">Email</label>
	      </div>
	      <div class='field-body'>
	        <div class='field'>
	          <p class='control'>
	            <input v-model="user.email" class="input" type="text">
	          </p>
	        </div>
	      </div>
	    </div>

	    <div v-if='!confirmAccount' class='field is-horizontal'>
	      <div class='field-label is-normal'>
	        <label class="label">Password</label>
	      </div>
	      <div class='field-body'>
	        <div class='field'>
	          <p class='control'>
	            <input v-model="user.password" class="input" type="text">
	          </p>
	        </div>
	      </div>
	    </div>

	    <div class='field is-horizontal' v-if='confirmAccount'>
	      <div class='field-label is-normal'>
	        <label class="label">Confimation code</label>
	      </div>
	      <div class='field-body'>
	        <div class='field'>
	          <p class='control'>
	            <input v-model="user.code" class="input" type="text">
	          </p>
	        </div>
	      </div>
	    </div>

	    <button v-if='!creating && !confirmAccount' class='button' @click='login()'>Login</button>
	    <button v-if='!creating && !confirmAccount' class='button' @click='toggleCreating()'>Sign up</button>
	    <button v-if='creating && !confirmAccount' class='button' @click='signup()'>Create Account</button>
	   	<button v-if='creating && !confirmAccount' class='button' @click='toggleCreating()'>Cancel</button>
	   	<button v-if='confirmAccount' class=button @click='confirm()'>confirmAccount</button>
	</div>
</template>
<script>

import cognitoAuth from '../cognito'

export default {
	name: 'login',
	data: function () {
		return {
			user: {
				name: 'bryt12',
				email: 'brysontang@gmail.com',
				password: 'Password123'
			},
			creating: false,
			confirmAccount: false,
			error: null,
			organizerObject: null
		}
	},
	methods: {
		toggleCreating () {
			this.creating = !this.creating;
		},
		async signup () {
			let res = await cognitoAuth.signup(this.user.name, this.user.email, this.user.password);
			this.confirmAccount = true;
		},
		async confirm () {
			await cognitoAuth.confirmAccount(this.user.name, this.user.code);
			this.creating = false;
			this.confirmAccount = false;
		},
		async login () {
			let res = await cognitoAuth.login(this.user.name, this.user.password);
			this.$router.push({name:'createSchedule', params: {organizerObject: res}});
		}
	}
}

</script>