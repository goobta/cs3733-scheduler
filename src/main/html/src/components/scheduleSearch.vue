<template>
	<div class='app'>
		<p>Search open slots for {{ schedule.name }}</p>
		<div v-if='filters.dayOfTheWeek.visible' class='field is-horizontal'>
			<div class='field-label is-normal'>
				<label class="label">Day Of Week:</label>
			</div>
			<div class='field-body'>
				<div class='field'>
				  <p class='control'>
			  		<div class='select'>
				        <select v-model="filters.dayOfTheWeek.value">
							<option :value='1'>Monday</option>
							<option :value='2'>Tuesday</option>
							<option :value='3'>Wednesday</option>
							<option :value='4'>Thursday</option>
							<option :value='5'>Friday</option>
				        </select>
				    </div>
				    <a class="icon has-text-danger">
					  <i @click='toggleFilter("Day of Week")' class="fas fa-ban"></i>
					</a>
			  	  </p>
				</div>
			</div>
		</div>

		<div v-if='filters.startTime.visible' class='field is-horizontal'>
	      <div class='field-label is-normal'>
	        <label class="label">Start Time:</label>
	      </div>
	      <div class='field-body'>
	        <div class='field'>
	          <p class='control'>
          		<div class='select'>
	                <select v-model="filters.startTime.value">
						<option v-for='time in times' :value='time.minutes'>{{ time.time }}</option>
	                </select>
	            </div>
				<a class="icon has-text-danger">
				  <i @click='toggleFilter("Start Time")' class="fas fa-ban"></i>
				</a>
	          </p>
	        </div>
	      </div>
		</div>

		<div v-if='filters.endTime.visible' class='field is-horizontal'>
	      <div class='field-label is-normal'>
	        <label class="label">End Time:</label>
	      </div>
	      <div class='field-body'>
	        <div class='field'>
	          <p class='control'>
          		<div class='select'>
	                <select v-model="filters.endTime.value">
						<option v-for='time in times' :value='time.minutes'>{{ time.time }}</option>
	                </select>
	            </div>
				<a class="icon has-text-danger">
				  <i @click='toggleFilter("End Time")' class="fas fa-ban"></i>
				</a>
	          </p>
	        </div>
	      </div>
		</div>

		<div v-if='filters.year.visible' class='field is-horizontal'>
			<div class='field-label is-normal'>
				<label class="label">Year:</label>
			</div>
			<div class='field-body'>
				<div class='field'>
				  <p class='control'>
			  		<div class='select'>
				        <select v-model="filters.year.value">
							<option v-for='year in years' :value='year'>{{ year }}</option>
				        </select>
				    </div>
				    <a class="icon has-text-danger">
					  <i @click='toggleFilter("Year")' class="fas fa-ban"></i>
					</a>
				  </p>
				</div>
			</div>
		</div>

		<div v-if='filters.month.visible' class='field is-horizontal'>
			<div class='field-label is-normal'>
				<label class="label">Month:</label>
			</div>
			<div class='field-body'>
				<div class='field'>
				  <p class='control'>
			  		<div class='select'>
				        <select v-model="filters.month.value">
							<option :value='1'>January</option>
							<option :value='2'>Febuary</option>
							<option :value='3'>March</option>
							<option :value='4'>April</option>
							<option :value='5'>May</option>
							<option :value='6'>June</option>
							<option :value='7'>July</option>
							<option :value='8'>August</option>
							<option :value='9'>September</option>
							<option :value='10'>October</option>
							<option :value='11'>November</option>
							<option :value='12'>December</option>
				        </select>
				    </div>
				    <a class="icon has-text-danger">
					  <i @click='toggleFilter("Month")' class="fas fa-ban"></i>
					</a>
				  </p>
				</div>
			</div>
		</div>

		<div v-if='filters.day.visible' class='field is-horizontal'>
			<div class='field-label is-normal'>
				<label class="label">Date:</label>
			</div>
			<div class='field-body'>
				<div class='field'>
				  <p class='control'>
				  	<div class='select'>
				        <select v-model="filters.day.value">
							<option v-for='date in dates' :value='date'>{{ date }}</option>
				        </select>
				    </div>
				    <a class="icon has-text-danger">
					  <i @click='toggleFilter("Date")' class="fas fa-ban"></i>
					</a>
				  </p>
				</div>
			</div>
		</div>
		<button class='button' @click='searchSchedule()'>Search</button>

		<div class="is-divider"></div>

		<div class='field is-horizontal'>
			<div class='field-label is-normal'>
				<label class="label">Add Filter:</label>
			</div>
			<div class='field-body'>
				<div class='field'>
				  <p class='control'>
				  	<div class='select'>
				        <select v-model="filter">
							<option v-for='filter in filters' v-if='!filter.visible' :value='filter.name'>{{ filter.name }}</option>
				        </select>
				    </div>
				  </p>
				</div>
			</div>
		</div>

		<button class='button' @click='toggleFilter(filter)'>Add Filter</button>

		<div class="is-divider"></div>
		<table class='table'>
			<thead>
				<tr>
					<th>
						Date
					</th>
					<th>
						Time
					</th>
					<th>
					</th>
				</tr>
			</thead>
			<tbody>
				<tr v-for='timeslot in timeslots'>
					<td>
						{{ timeslot.date }}
					</td>
					<td>
						{{ timeslot.time }}
					</td>
					<td>
						<button @click='createMeeting(timeslot.date, timeslot.time)' class='button'>Book</button>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</template>
<script>
export default {
	name: 'scheduleSearch',
	data: function () {
		return {
			filters: {
				dayOfTheWeek: {
					visible: false,
					name: 'Day of Week'
				},
				startTime: {
					visible: false,
					name: 'Start Time'
				},
				endTime: {
					visible: false,
					name: 'End Time'
				},
				year: {
					visible: false,
					name: 'Year'
				},
				month: {
					visible: false,
					name: 'Month'
				},
				day: {
					visible: false,
					name: 'Date'
				}
			},
			filter: null,
			uuid: null,
			schedule: {},
			search: {},
			times: [],
			openTimeSlots: [],
			years: [],
			dates: [],
			newMeeting: {}
		}
	},
	computed: {
		timeslots: function () {
			let temp = [];
			for(let i = 0; i < this.openTimeSlots.length; i++){
				let d = new Date(this.openTimeSlots[i]);
				temp.push({
					date: (d.getMonth() + 1) + "/" + d.getDate() + '/' + (d.getYear() + 1900),
					time: d.getHours() + ':' + d.getMinutes().toString().padStart(2, '0')
				})
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

			let startMinutes = (startDate.getHours() * 60) + startDate.getMinutes();
			let endMinutes = (endDate.getHours() * 60) + endDate.getMinutes();

			for (let i = startMinutes; i < endMinutes; i = i + this.schedule.meetingDuration) {
				this.times.push({
					minutes: i,
					time: Math.floor(i/60) + ':' + (i%60 == 0 ? '00': (i%60 == 5 ? '05' : i%60)),
				})
			}
		},
		async createMeeting (day, time) {
			this.newMeeting.scheduleId = this.uuid;
			let name = prompt("Enter Name:")
			this.newMeeting = {
				participantName: name
			}
			this.newMeeting.startDateTime = new Date(day);
			this.newMeeting.startDateTime.setUTCHours(time.length == 4 ? time.substring(0,1) : time.substring(0,2), time.slice(-2))
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
	        //location.reload();
		},
		toggleFilter (filter) {
			for(let i in this.filters){
				if(this.filters[i].name == filter){
					this.filters[i].visible = !this.filters[i].visible;
				}
			}
		},
		async searchSchedule () {
			let body = {}
			for(let i in this.filters){
				if(this.filters[i].visible){
					if(this.filters[i].name == 'Start Time' || this.filters[i].name == 'End Time'){
						let date = new Date();
						let time = this.filters[i].value;
						date.setUTCHours(Math.floor(time/60), time%60)
						body[i] = date;
					}else{
						body[i] = this.filters[i].value;
					}
				}
			}
			await fetch('https://wasu526ybc.execute-api.us-east-2.amazonaws.com/Zeta/participantsearch?scheduleId=' + this.uuid,{
				method: 'POST',
				body: JSON.stringify(body),
				headers:{
		          'Content-Type': 'application/json'
		        }
			})
			.then(res => res.json())
	        .then(response => this.openTimeSlots = response)
	        .catch(error => console.error('Error:', error));
		}
	},
	async created () {
		this.uuid = this.$route.query.uuid;
		await this.getSchedule();
		await this.parseSchedule();
		let date = new Date();
		for(let i = date.getYear()+1900; i <= date.getYear()+2000; i++){
			this.years.push(i);
		}
		for(let i = 1; i <= 31; i++){
			this.dates.push(i);
		}
	}
}
</script>