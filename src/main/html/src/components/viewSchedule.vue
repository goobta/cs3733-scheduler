<template>
	<div id='app'>
		<h1>{{ schedule.name }}</h1>
		<table class='table is-bordered'>
			<tbody>
				<tr>
					<th>
						{{ dateRange }}
					</th>
					<th>
						Monday
					</th>
					<th>
						Tuesday
					</th>
					<th>
						Wednesday
					</th>
					<th>
						Thursday
					</th>
					<th>
						Friday
					</th>
				</tr>
				<tr v-for='time in times'>
					<td>
						{{ time.time }}
					</td>
					<td v-for='timeSlot in time.days' v-bind:class="{ unavailable: !timeSlot.open }">
						<button v-if='timeSlot.open' class='button'>Free</button>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</template>
<script>

export default {
	data: function() {
		return {
			schedule: {},
			times: [],
			uuid: null,
			dateRange: null
		}
	},
	methods: {
		async getSchedule () {
			await fetch('https://wasu526ybc.execute-api.us-east-2.amazonaws.com/Zeta/participantSchedule?uuid=' + this.uuid)
			.then(res => res.json())
        	.then(response => this.schedule = response)
        	.catch(error => console.error('Error:', error));
		},
		parseSchedule () {
			let startMinutes = (this.schedule.daily_start_time.hour * 60) + this.schedule.daily_start_time.minute
			let endMinutes = (this.schedule.daily_end_time.hour * 60) + this.schedule.daily_end_time.minute

			let startDate = new Date(this.schedule.startDateTime);
			let endDate = new Date(this.schedule.endDateTime);

			this.dateRange = '(' + startDate.getMonth() + '/' + startDate.getDate() + '-' + endDate.getMonth() + '/' + endDate.getDate() + ')';

			for (let i = startMinutes; i < endMinutes; i = i + this.schedule.meetingDuration) {
				let temp = [];
				for (let j = 0; j <= 4; j ++) {
					if (j >= startDate.getDay() - 1 && j < endDate.getDay()) {
						temp.push({
							open: true
						});
					} else {
						temp.push({
							open: false
						});
					}
				}
				this.times.push({
					time: (Math.floor(i/60) - 5) + ':' + (i%60 == 0 ? '00': i%60),
					days: temp
				});
			}
			console.log(this.times);
		}
	},
	async created () {
		this.uuid = this.$route.query.uuid;
		await this.getSchedule();
		this.parseSchedule();
	}
}
</script>
<style>
.unavailable {
	background-color: gray
}
</style>