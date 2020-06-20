# Mom, I am safe

Do you like to travel? I do. During my last long trip, I used the SPOT solution [https://www.findmespot.com]. I wasn't quite happy with it and I know that it's not for everyone. A few years ago I thing about simple solution to send a location via SMS and post this position on Twitter. I visited very often places where there was a mobile signal, but mobile internet was not available. This is an argument why Twitter app is not enough ;)

## Keep calm & remember, this is pet project

This project is intended for me to test different programming approaches and ways. I agree that similar functionality can be achieved in a different architecture, language or framework. I'm not taking up this discussion right now. Maybe someday there will be a LITE version that will actually do what it should with right tools and performance. Now is the time to learn and experiment.

## General view

![Picture with general view draft](general.draft.svg?raw=true "General view draft")

It is simple, service "Mum, I am safe" is to publish information about the last position of the user when he has GSM coverage, but no Internet. The functionality can be used for the whole journey for convenience.

- The user sends an SMS to our system's number and the message is published on his Twitter account associated to his phone number.
- The user sends an SMS with the GPS location and this is accordingly processed and published on Twitter (e.g. message + link to the position on the Google map).

## Technology stack

- Linux
- Java 11 (Spring 5)
- RabbitMQ
- MongoDB
- Docker
- Maven/Gradle
- Bash
