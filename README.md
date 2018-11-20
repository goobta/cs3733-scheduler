# Scheduler - Lesath

This is our term project for cs3733 (Software Engineering)

## API Routes & Use Cases
The following are the routes and the associated use cases.

### /organizer/schedule
#### put
* Create Schedule - One thing to note is that we merged the StartDay, EndDay,
  StartTime, and EndTime into just StartDayTime and EndDayTime. This is just for
	conciseness more than anything.

#### get
There are no use cases associated with this endpoint, however we have it because
we have a view for the Organizer to view all schedules


### /organizer/schedule/{scheduleId}
#### post
* Extend End Date
* Extend Start Date

#### delete
* Delete Schedule


### /organizer/schedule/{scheduleId}/timeslot
#### post
* Close Individual Timeslot
* Open Individual Timeslot


### /organizer/schedule/{scheduleId}/range
#### post
* Close Time Range
* Open Time Range


### /organizer/schedule/{scheduleId}/day
#### post
* Close day
* Open day

### /participant/schedule/{scheduleId}/meeting
#### put
*  Create Meeting


### /participant/schedule/{scheduleId}/meeting/{meetingId}
#### delete
* Cancel Participant Meeting


### /participant/schedule/{scheduleId}/search
#### post
* Search Timeslots


### /systemAdministrator/schedule
#### post
* Retrieve Days Schedule
* Retrieve Hours Schedule

### delete
* Delete Schedules


### /schedule/{scheduleId}
#### get
* Review Weekly Schedule
* Review Schedule


### /schedule/{scheduleId}/meeting/{meetingId}
* Cancel Meeting
