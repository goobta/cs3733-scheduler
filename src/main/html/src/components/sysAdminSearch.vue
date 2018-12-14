<template>
	<div>
		<p>I want all schedules that are 
			<input v-model='value' type='number'></input>
			<select v-model='interval' class='select'>
				<option>Select Inveral</option>
				<option value='day'>day(s)</option>
				<option value='hour'>hour(s)</option>
			</select>
		 old</p>
		 <button class='button' @click='search()'>Search</button>
		 <button v-if='interval == "day" && schedules.length > 0' class='button' @click='deleteDays()'>Delete Results</button>
		 <table class='table'>
		 	<tbody>
		 		<tr>
			 		<th>
			 			Results
			 		</th>
			 	</tr>
			 	<tr v-for='schedule in schedules'>
			 		<td>
			 			{{ schedule.name }}
			 		</td>
			 		<td>
			 			<button @click='deleteSchedule(schedule.id)' class='button'>Delete</button>
			 		</td>
			 	</tr>
		 	</tbody>
		 </table>
	</div>
</template>
<script>

export default {
	name: 'sysAdminSearch',
	data: function () {
		return {
			interval: null,
			value: 0,
			schedules: []
		}
	},
	methods: {
		async search () {
			if(this.interval == 'day'){
				await fetch('https://wasu526ybc.execute-api.us-east-2.amazonaws.com/Zeta/sysadmindaysearch?days='+this.value,{
					method: 'GET',
					headers:{
			          'Content-Type': 'application/json'
			        }
				})
				.then(res => res.json())
		        .then(response => this.schedules = response)
		        .catch(error => console.error('Error:', error));
			}else if(this.interval == 'hour'){
				await fetch('https://wasu526ybc.execute-api.us-east-2.amazonaws.com/Zeta/sysAdminHourSearch?hours='+this.value,{
					method: 'GET',
					headers:{
			          'Content-Type': 'application/json'
			        }
				})
				.then(res => res.json())
		        .then(response => this.schedules = response)
		        .catch(error => console.error('Error:', error));
			}
		},
		async deleteDays () {
			if(this.interval == 'day'){
				await fetch('https://wasu526ybc.execute-api.us-east-2.amazonaws.com/Zeta/sysadmindaysearch?days='+this.value,{
					method: 'DELETE',
					headers:{
			          'Content-Type': 'application/json'
			        }
				})
				.then(res => res.json())
		        .then(response => this.schedules = response)
		        .catch(error => console.error('Error:', error));
			}
		},
		async deleteSchedule (id) {
			await fetch('https://wasu526ybc.execute-api.us-east-2.amazonaws.com/Zeta/organizerSchedule?scheduleId=' + id,{
				method: 'DELETE',
				headers:{
		          'Content-Type': 'application/json'
		        }
			})
			.then(res => res.json())
	        .catch(error => console.error('Error:', error));
	        location.reload();
		}
	}
}
</script>