# Project 2 - CodePath To Do List
"Golden Ticket" is an app the takes movie data from a MovieDB open API and displays it for users. Data includes title, overview, posters, movie trailer, release date, and genres

Time spent: 25 hours total

## User Stories

The following **required** functionality is completed:

* [X] User can **view a list of movies (title, poster image, and overview) currently playing in theaters from the Movie Database API**
* [X] Display a **nice default placeholder graphic** for each image during loading
* [X] Views should be responsive for **both landscape/portrait mode**


The following **stretch** features are implemented:

* [X] Add rounded corners for the images using the Glide transformations
* [X] Improve the user interface through styling and coloring
* [X] Apply the View Binding library to reduce view boilerplate
* [X] Allow video posts to be played in full-screen using the YouTubePlayerView


The following **additional** features are implemented:

* [X] Custom application icon
* [X] Include genres and release date


## Video Walkthrough

Shows all of the features of the application, except for the View Binding library, which will be apparent in the code


![App Demo Link](moviesApplication/screenshots/movie_app.gif)



## Notes

For my second application, I feel that I have done much better. There is much more I can improve on,
but I have a stronger view of RecyclerView, Adapters, ViewHolders, the bind method, and the "new" operator
which saved me in the last few minutes of this assignment

Some problems I encountered:
	- Was passing object lists without initializing with "new", kept passing null when dealing with functions .size(),
		halting the development

	- Trouble passing JSON data for YouTube url through MainActivity, realized it would be better to pass in the 
		MovieDetailsActivity

	- Getting onClick() to work, need more practice with this. Decided to use the .xml Design onClick function instead


## License

    Copyright 2020 Nicholas Powell

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
