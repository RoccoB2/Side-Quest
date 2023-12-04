# Side Quest

![SideQuestLogo](https://i.imgur.com/4kGQ6E4.png)

## About Side Quest

Side Quest is a stylized to-do list that is centered around completing quests for experience in order to level up. By introducing an experience system, we aim to make finishing your tasks a more enjoyable experience. Every quest you complete grants you a certain amount of experience which is tracked by the level counter on the stats page. The harder the task, the more experience you earn. 

## Key Features

* An "Add Quest" screen that allows users to customize each task they want to create
* Each quest will have a Category, a Header, and a Difficulty
* Users can also select a custom due date and time for each quest
* Additionally, users can enter a description that they can view on the Home page by pressing the quest itself
* Three levels of difficulty to award more experience for completion
* A "Home" screen to view all of the quests that are in progress
* A "Stats Page" to view the user's stats, quest history, and level
* The Stats show how many of each quest you've passed and failed, as well as your current and longest streak
* Are you struggling to find motivation? Simply hit the "Get Inspirational Quote" button to generate a quote
* There is no limit to the kind of quest you can make


## Recommended Android Emulator

Side Quest requires an Android API of 33 with Google Play installed. 

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

Other Dependencies

* implementation("androidx.core:core-ktx:1.9.0")
* implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
* implementation("androidx.activity:activity-compose:1.8.0")
* implementation(platform("androidx.compose:compose-bom:2023.03.00"))
* implementation("androidx.compose.ui:ui")
* implementation("androidx.compose.ui:ui-graphics")
* implementation("androidx.compose.ui:ui-tooling-preview")
* implementation("androidx.compose.material3:material3")
* testImplementation("junit:junit:4.13.2")
* androidTestImplementation("androidx.test.ext:junit:1.1.5")
* androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
* androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
* androidTestImplementation("androidx.compose.ui:ui-test-junit4")
* debugImplementation("androidx.compose.ui:ui-tooling")
* debugImplementation("androidx.compose.ui:ui-test-manifest")
* implementation ("androidx.navigation:navigation-compose:2.6.0")
* implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
* implementation ("androidx.activity:activity-compose:1.5.1")