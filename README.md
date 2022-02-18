# Rover Android Coding Project

The goal of this project to create an Android app that persistently stores and displays a collection of dogs and related data.

You can use any external frameworks you'd like to complete this project, however, modern Android SDKs and tools should be used. That said, feel free to write your sample in either Kotlin or Java.

The work you create here should be representative of code that we'd expect to receive from you if you were hired tomorrow. Our expectation is that you'll write production quality code including tests.

Update the existing README to help us understand your approach and thought process, e.g. design choices, trade-offs, dependencies, etc.

There is no time limit, although we don't want to take too much of your time, so we advise people to not spend more than 8 hours on the project.

Finally, this is not a trick project, so if you have any questions, don't hesitate to ask.


### Requirements

1. A user should be able to create a dog with the dog's name and a photo of the dog.

2. The newly created dog should display in a list of all dogs.

3. Each dog in the list should show its name and a thumbnail photo.

4. When a dog is selected, display a view that shows:
  - The dog's name
  - A larger version of the dog's photo

5. Minimum API target should be 19, but UI should follow basic Material patterns.


Complete this project and push your solution back to this repository.
When you're finished, please email your Rover contact and let them know.

Be aware that we will have you use and modify this project for the in-person portion of the interview.

### Candidate Comments

## My environment

 - Android Studio Version: BumbleBee | 2021.1.1
 - Kotlin Version: 1.5.0
 - Gradle Version: 7.2
 - Gradle Plugin Version: 7.0.1

## Architecture

I implemented MVVM in this project, I use ViewModel to have live data that serves the dog list, that
way as soon as a dog is added the list will display it in the activity, I'm using Room for data
persistence, the ViewModel requests the data from the DB and the DB serves it with LiveData which is
observed in the View/Activity, this architecture is very popular and I find it very useful.

## Thought Process

At the beginning I started thinking at the UX required to meet this challenge, I thought of a clean
UI with easy controls and actions, I went with two main screens for presenting the UI, I knew the
user had to create a dog to display in the list, so, I decided to plan what data I was going to need,
I created a couple of models of data that I thought was essential for a dog object to have, a dog has
an owner so I decided to create a database in Room with two tables for Owners and Dogs in a one to many
relationship, these data models have the most basic info for this demo with the purpose of making it
simple.

## Image Storage

At the beginning I decided to use Room to store the images of the dogs as byte arrays, the purpose for
this was to avoid requesting permissions for storage to the user and also to avoid the case in which
the picture taken is deleted by the user from the gallery app, then I realized that I could use the
internal storage to save the photos so I ended up using Glide for the loading of images.

## Dog Details

For presenting the detail of the dog I used a DialogFragment in order to display the detail in the
same screen as the list of dogs to avoid having to call a new activity for displaying the detail, I
think it's a little bit more elegant that way.

## Testing

Finally I made DB testing to assert that the queries and CRUD methods used can deliver the expected result.
I've validated DB data insertion and also validated that the queries used are returning the data correctly,
I wanted to make also a little bit of Instrumented tests but honestly that will take me more time and
effort, however, I think the testing done to the DB is well done and covers the scenarios required.

## Final Thoughts

Overall it's a simple app built on the premise of minimizing time to value, giving the time that I
had I created a basic app with the goal of solving the needs of this challenge that can be developed
further driven by the requirements of the users and client.