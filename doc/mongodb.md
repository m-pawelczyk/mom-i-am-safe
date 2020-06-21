# MongoDB

## Database schema draft

This sketch describes a very initial concept of the database shape. The field names represent the intention. They are not database fields. The document was created for Issue#23 <https://github.com/m-pawelczyk/mom-i-am-safe/issues/23>. As long as this note exists, remember that the current database model is described in code.

#### User (in Users):
- ID
- user UUID
- login
- password
- phone number
- twitter account
- whatsapp number
- last update
- current trip
- free spots (not associated with any trips)
- trip list

#### Trip (in Trips):
- ID
- name
- owner UUID
- start date
- end date
- gps sorted list

#### GPS (in GPSes):
- ID
- long
- lat
- altitude
- timestamp
- is it real
- publish time (for fake gps, for real time stamp is OK)

#### ActiveUser (in ActiveUsers):
- ID
- publish time stamp (redundant with GPS?)
- UUID
- GPS

#### TwitterMessage (in TwitterMessages):
- ID
- twitter account
- user UUID
- anonymous message
- twitter url ref
- publish date


#### PhoneNumber or SenderId (in PhoneNumbers or SendersId):
- ID
- PhoneNumber
- user UUID
