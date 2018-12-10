<template>
	<div class='app'>
		<input class="input" v-model='uuid' placeholder="Secret Code"></input>
		<button @click='loadPage()' class='button'>GO!</button>
	</div>
</template>
<script>
export default {
	name: 'searchScheduleOrganizer',
	data: function () {
		return {
			uuid: null
		}
	},
	methods: {
		loadPage() {
			window.location.href = 'https://schedulerbucket2.s3.us-east-2.amazonaws.com/index.html#/editSchedule?uuid=' + this.uuid;
		}
	}
}
</script>