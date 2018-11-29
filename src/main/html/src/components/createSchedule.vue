<template>
  <div id='app'>
    <h1>
      Create New Schedule
    </h1>
    <div class='field is-horizontal'>
      <div class='field-label is-normal'>
        <label class="label">Schedule Name</label>
      </div>
      <div class='field-body'>
        <div class='field'>
          <p class='controll'>
            <input v-model="schedule.name" class="input" type="text">
          </p>
        </div>
      </div>
    </div>

    <div class='field is-horizontal'>
      <div class='field-label is-normal'>
        <label class="label">Start Date</label>
      </div>
      <div class='field-body'>
        <div class='field'>
          <p class='controll'>
            <datepicker class='field' v-model="schedule.startDate"></datepicker>
          </p>
        </div>
      </div>
    </div>

    <div class='field is-horizontal'>
      <div class='field-label is-normal'>
        <label class="label">End Date</label>
      </div>
      <div class='field-body'>
        <div class='field'>
          <p class='controll'>
            <datepicker class='field' v-model="schedule.endDate"></datepicker>
          </p>
        </div>
      </div>
    </div>

    <div class='field is-horizontal'>
      <div class='field-label is-normal'>
        <label class="label">Start Time</label>
      </div>
      <div class='field-body'>
        <div class='field'>
          <p class='controll'>
            <vue-timepicker :minute-interval='5' v-model='schedule.startTime'></vue-timepicker>
          </p>
        </div>
      </div>
    </div>

    <div class='field is-horizontal'>
      <div class='field-label is-normal'>
        <label class="label">End Time</label>
      </div>
      <div class='field-body'>
        <div class='field'>
          <p class='controll'>
            <vue-timepicker minute-interval='15' v-model='schedule.endTime'></vue-timepicker>
          </p>
        </div>
      </div>
    </div>

    <div class='field is-horizontal'>
      <div class='field-label is-normal'>
        <label class="label">Meeting Length</label>
      </div>
      <div class='field-body'>
        <div class='field select'>
          <p class='controll'>
                <select v-model="schedule.meetingLength">
                  <option value='10'>10</option>
                  <option value='15'>15</option>
                  <option value='20'>20</option>
                  <option value='30'>30</option>
                  <option value='60'>60</option>
                </select>
          </p>
        </div>
      </div>
    </div>
    <button @click='putSchedule()' class='button'>Create</button>
  </div>
</template>

<script>
import Datepicker from 'vuejs-datepicker';
import VueTimepicker from 'vue2-timepicker';

import { api } from '../util/config';

export default {
  name: 'createSchedule',
  components: {
    Datepicker,
    VueTimepicker
  },
  data: function() {
    return {
      schedule: {
        startTime: {
          HH: "08",
          mm: "00"
        },
        endTime: {
          HH: "17",
          mm: "00"
        }
      }
    }
  },
  methods: {
    async putSchedule () {
      let tempStart = this.schedule.startTime.HH + ':' + this.schedule.startTime.mm;
      let tempEnd = this.schedule.endTime.HH + ':' + this.schedule.endTime.mm;

      delete this.schedule.startTime;
      delete this.schedule.endTime;

      this.schedule.startTime = tempStart
      this.schedule.endTime = tempEnd;

      let res = await fetch('/organizer/schedule', {
        method: 'PUT',
        body: JSON.stringify(this.schedule),
        headers:{
          'Content-Type': 'application/json'
        }
      })
    }
  }
}
</script>