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
						<button v-if='timeSlot.open' class='button' @click='createMeeting(timeSlot.day, timeSlot.time)'>Free</button>
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
			dateRange: null,
			newMeeting: {}
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

			this.dateRange = '(' + (startDate.getMonth() + 1) + '/' + startDate.getDate() + '-' + (endDate.getMonth() + 1) + '/' + endDate.getDate() + ')';
			for (let i = startMinutes; i < endMinutes; i = i + this.schedule.meetingDuration) {
				let temp = [];
				let cursorDate = new Date(this.schedule.startDateTime);
				let lastDate = new Date(this.schedule.endDateTime);
				cursorDate.setHours(0,0);
				lastDate.setHours(23,59);

				//Sets cursor to Monday of first week
				var day = cursorDate.getDay() || 7;
				if ( day !== 1 ) {             
				    cursorDate.setHours(-24 * (day - 1));
				}

				//Sets last date to Friday of last week
				var day = lastDate.getDay() || 7;
				if ( day !== 5 ) {             
				    lastDate.setHours(24 * (5-day));
				}
				while (cursorDate <= lastDate) {
					if (cursorDate >= new Date(startDate).setHours(0,0,0) && cursorDate <= new Date(endDate.setHours(23,59,59))) {
						temp.push({
							day: cursorDate.toString(),
							time: i,
							open: true
						})
					} else {
						temp.push({
							open: false
						});
					}
					//TODO: Weekend handling, if it's friday add 3 instead
					cursorDate.setDate(cursorDate.getDate() + 1)
				}

				this.times.push({
					time: Math.floor(i/60) + ':' + (i%60 == 0 ? '00': (i%60 == 5 ? '05' : i%60)),
					days: temp
				});
			}
		},
		async createMeeting (day, time) {
			this.newMeeting.scheduleId = this.uuid;
			let participantName = prompt("Enter Name:")
			this.newMeeting = {
				name: participantName,
			}
			this.newMeeting.startTime = new Date(day);
			this.newMeeting.startTime.setHours(Math.floor(time/60), time%60)
			await fetch('https://wasu526ybc.execute-api.us-east-2.amazonaws.com/Zeta/createMeeting?scheduleId=' + this.uuid,{
				method: PUT,
				body: JSON.stringify(this.newMeeting),
				headers:{
		          'Content-Type': 'application/json'
		        },
			})
			.then(res => res.json())
	        .then(response => alert("Secret code: " + response.meetingUuid))
	        .catch(error => console.error('Error:', error));
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