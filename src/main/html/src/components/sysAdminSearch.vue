<template>
	<div>
		<p>I want all schedules that are 
			<input v-model='value' type='number'></input>
			<select v-model='interval' class='select'>
				<option>Select Inveral</option>
				<option>day(s)</option>
				<option>hour(s)</option>
			</select>
		 old</p>
		 <button class='button' @click='search()'>Search</button>
		 <table class='table'>
		 	<tbody>
		 		<tr>
			 		<th>
			 			Results
			 		</th>
			 	</tr>
			 	<tr>
			 		<td>

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
			value: 0
		}
	},
	methods: {
		search () {
			
		}
	}
}
</script>