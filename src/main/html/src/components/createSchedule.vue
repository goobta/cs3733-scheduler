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
            <datepicker class='field' v-model="schedule.startDateTime"></datepicker>
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
            <datepicker class='field' v-model="schedule.endDateTime"></datepicker>
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
            <vue-timepicker v-model='schedule.startTime' :minute-interval='5'></vue-timepicker>
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
            <vue-timepicker v-model='schedule.endTime' :minute-interval='15'></vue-timepicker>
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
                  <option :value='10'>10</option>
                  <option :value='15'>15</option>
                  <option :value='20'>20</option>
                  <option :value='30'>30</option>
                  <option :value='60'>60</option>
                </select>
          </p>
        </div>
      </div>
    </div>
    <div class='buttons'>
      <button @click='putSchedule()' class='button'>Create</button></br>
      <button @click='redirect()' class='button' v-if='url != null'>View Schedule</button>
    </div>
    <p v-if='url != null'>Participant URL:</p><a :href='url'>{{ url }}</a>
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
      url: null,
      uuid: null,
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
      this.schedule.startDateTime.setHours(this.schedule.startTime.HH, this.schedule.startTime.mm)
      this.schedule.endDateTime.setHours(this.schedule.endTime.HH, this.schedule.endTime.mm)

      delete this.schedule.startTime;
      delete this.schedule.endTime;

      await fetch('https://wasu526ybc.execute-api.us-east-2.amazonaws.com/Zeta/organizerSchedule', {
        method: 'PUT',
        body: JSON.stringify(this.schedule),
        headers:{
          'Content-Type': 'application/json'
        }
      }).then(res => res.json())
        .then(response => this.uuid = response.scheduleUuid)
        .catch(error => console.error('Error:', error));
      this.url = 'https://schedulerbucket2.s3.us-east-2.amazonaws.com/index.html#/viewSchedule?uuid=' + this.uuid
    },
    redirect () {
      window.location.href = 'https://schedulerbucket2.s3.us-east-2.amazonaws.com/index.html#/editSchedule?uuid=' + this.uuid;
    }
  }
}
</script>