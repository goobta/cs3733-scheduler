<template>
	<div id='app'>
		<h1>{{ schedule.name }}</h1>
		<button :disabled='page == 0' class='button' @click='page--'>Previous Page</button>
		<button :disabled='atEnd' class='button' @click='page++'>Next Page</button>
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
				<tr v-for='time in currentWeek'>
					<td>
						{{ time.time }}
					</td>
					<td v-for='timeSlot in time.days' v-bind:class="{ unavailable: !timeSlot.inBounds }">
						<button v-if='timeSlot.available' class='button' @click='createMeeting(timeSlot.day, timeSlot.time)'>Free</button>
						<div v-if='timeSlot.inBounds && timeSlot.name'>
							<p class='has-text-centered'>{{ timeSlot.name }}</p>
							<button @click='cancelMeeting(timeSlot)'>cancel</button>
						</div>
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
			newMeeting: {},
			page: 0
		}
	},
	computed: {
		currentWeek: function () {
			//localhost:8080/#/viewSchedule?uuid=41eea1a1-dcf1-4e77-ae6e-049a2c0650c3
			let currentWeek = [];
			for (let i = 0; i < this.times.length; i ++) {
				currentWeek.push({
					days: this.times[i].days.slice(this.page*5,this.page*5 + 5),
					time: this.times[i].time
				});
			}
			if(currentWeek.length > 0){
				let startDate = new Date(currentWeek[0].days[0].day);
				let endDate = new Date(currentWeek[0].days[4].day);
				this.dateRange = '(' + (startDate.getMonth() + 1) + '/' + startDate.getDate() + '-' + (endDate.getMonth() + 1) + '/' + endDate.getDate() + ')';
			}
			return currentWeek;
		},
		atEnd: function () {
			if(this.times.length != 0){
				return this.page + 1 == this.times[0].days.length/5;
			} else {
				return false;
			}
		},
		meetings: function () {
			let temp = {};
			for(let i = 0; i < this.schedule.meetings.length; i++){
				temp[this.schedule.meetings[i].startTime] = this.schedule.meetings[i].participantName;
			}
			return temp;
		},
		notAviable: function () {
			let temp = {};
			for(let i = 0; i < this.schedule.timesNotAvailable.length; i++){
				temp[this.schedule.timesNotAvailable[i]] = true;
			}
			return temp;
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
			let startDate = new Date(this.schedule.startDateTime);
			let endDate = new Date(this.schedule.endDateTime);

			console.log(startDate);
			console.log(endDate);

			let startMinutes = (startDate.getHours() * 60) + startDate.getMinutes();
			let endMinutes = (endDate.getHours() * 60) + endDate.getMinutes();

			for (let i = startMinutes; i < endMinutes; i = i + this.schedule.meetingDuration) {
				let temp = [];
				let cursorDate = new Date(this.schedule.startDateTime);
				let lastDate = new Date(this.schedule.endDateTime);
				console.log(cursorDate);
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
					let dateString = (cursorDate.getYear()+1900) + '-' + ('0' + (cursorDate.getMonth()+1)).slice(-2) + '-' + String("0" + cursorDate.getDate()).slice(-2) + 'T' + String('0' + Math.floor(i/60)).slice(-2) + ':' + (i%60 == 0 ? '00': (i%60 == 5 ? '05' : i%60)) + ':' + String('0' + cursorDate.getSeconds()).slice(-2) + '.000' + 'Z';
					if (cursorDate >= new Date(startDate).setHours(0,0,0) && cursorDate <= new Date(endDate.setHours(23,59,59))) {
						if(this.meetings[dateString]){
							temp.push({
								day: cursorDate.toString(),
								time: i,
								inBounds: true,
								available: false,
								name: this.meetings[dateString]
							})
						} else if (this.notAviable[dateString]){
							temp.push({
								day: cursorDate.toString(),
								time: i,
								inBounds: false,
								available: false
							})
						} else {
							temp.push({
								day: cursorDate.toString(),
								time: i,
								inBounds: true,
								available: true
							})	
						}
					} else {
						temp.push({
							day: cursorDate.toString(),
							inBounds: false,
							available: false
						});
					}

					if (cursorDate.getDay() == 5){
						cursorDate.setDate(cursorDate.getDate() + 3);
					} else {
						cursorDate.setDate(cursorDate.getDate() + 1);
					}
				}

				this.times.push({
					time: Math.floor(i/60) + ':' + (i%60 == 0 ? '00': (i%60 == 5 ? '05' : i%60)),
					days: temp
				});
			}
		},
		async createMeeting (day, time) {
			this.newMeeting.scheduleId = this.uuid;
			let name = prompt("Enter Name:")
			this.newMeeting = {
				participantName: name,
			}
			this.newMeeting.startDateTime = new Date(day);
			this.newMeeting.startDateTime.setUTCHours(Math.floor(time/60), time%60)
			await fetch('https://wasu526ybc.execute-api.us-east-2.amazonaws.com/Zeta/createMeeting?scheduleId=' + this.uuid,{
				method: 'PUT',
				body: JSON.stringify(this.newMeeting),
				headers:{
		          'Content-Type': 'application/json'
		        },
			})
			.then(res => res.json())
	        .then(response => alert("Secret code: " + response.meetingUuid))
	        .catch(error => console.error('Error:', error));
	        location.reload();
		},
		async cancelMeeting (timeSlot) {
			let code = prompt("Meeting secret code")
			if (code == timeSlot.meetingId) {
				alert('correct');
			}
			await fetch('https://wasu526ybc.execute-api.us-east-2.amazonaws.com/Zeta/createMeeting?scheduleId=' + this.uuid + "&meetingId=" + code, {
		        method: 'DELETE',
		        headers:{
		          'Content-Type': 'application/json'
		        }
		      })
		        .catch(error => console.error('Error:', error));
		    location.reload();
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