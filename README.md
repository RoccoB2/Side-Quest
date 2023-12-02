# Side Quest

![SideQuestLogo](https://i.imgur.com/4kGQ6E4.png)

## About Side Quest

Side Quest is a stylized to-do list that is centered around completing quests for experience in order to level up. By introducing an experience system, we aim to make finishing your tasks a more enjoyable experience. Every quest you complete grants you a certain amount of experience which is tracked by the level counter on the stats page. The harder the task, the more experience you earn. 

## Key Features

* An "Add Quest" screen that allows users to customize each task they want to create
* Each quest will have a Category, a Header, and a Difficulty
* Optionally, users can enter a description as well as a due date and time
* A "Home" screen to view all of the quests that are in progress
* A "Stats Page" to view quest history as well as the user's current level
* Three levels of difficulty to award more experience for completion
* There is no limit to the kind of quest you can make


## Recommended Android Emulator

We recommend an android emulator of API 30

## Dependencies

Room Dependencies

* val room_version = ("2.6.0") 
* implementation ("androidx.room:room-ktx:$room_version") 
* implementation ("androidx.room:room-runtime:$room_version") 
* annotationProcessor ("androidx.room:room-compiler:$room_version")

To use Kotlin annotation processing tool (kapt)

* kapt ("androidx.room:room-compiler:$room_version")

Added to assist with making http requests

* implementation("com.squareup.okhttp3:okhttp:4.11.0")

Added to assist with parsing json

* implementation ("com.google.code.gson:gson:2.10.1")