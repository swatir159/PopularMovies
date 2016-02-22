# PopularMovies
Udacity Nanodegree Popular Movies Coursework ( P2)

Implemented in P2 Submission

User Interface - Layout
Movies are displayed in the main layout via a grid of their corresponding movie poster thumbnails - 
UI contains an element (e.g., a spinner or settings menu) to toggle the sort order of the movies by: most popular, highest rated, and favorites - done
UI contains a screen for displaying the details for a selected movie
Movie Details layout contains title, release date, movie poster, vote average, and plot synopsis. -
Movie Details layout contains a section for displaying trailer videos and user reviews - done
Tablet UI uses a Master-Detail layout implemented using fragments. The left fragment is for discovering movies. The right fragment displays the movie details view for the currently selected movie. - done

User Interface - Function
When a user changes the sort criteria (most popular, highest rated, and favorites) the main view gets updated correctly.
When a movie poster thumbnail is selected, the movie details screen is launched [Phone] or displayed in a fragment [Tablet] 
When a trailer is selected, app uses an Intent to launch the trailer
In the movies detail screen, a user can tap a button(for example, a star) to mark it as a Favorite 

Network API Implementation
In a background thread, app queries the /discover/movies API with the query parameter for the sort criteria specified in the settings menu. (Note: Each sorting criteria is a different API call.)
This query can also be used to fetch the related metadata needed for the detail view.
App requests for related videos for a selected movie via the /movie/{id}/videos endpoint in a background thread and displays those details when the user selects a movie. 
App requests for user reviews for a selected movie via the /movie/{id}/reviews endpoint in a background thread and displays those details when the user selects a movie.

Data Persistence
App saves a “Favorited” movie to SharedPreferences or a database using the movie’s id.
When the “favorites” setting option is selected, the main view displays the entire favorites collection based on movie IDs stored in SharedPreferences or a database.

ContentProvider
App persists favorite movie details using a database - done
App displays favorite movie details even when offline  
App uses a ContentProvider* to populate favorite movie details



Sharing Functionality
Movie Details View includes an Action Bar item that allows the user to share the first trailer video URL from the list of trailers
App uses a share Intent to expose the external youtube URL for the trailer

help taken from :
Project Sunshine
https://github.com/frank-tan/Popular-Movies
stackoverflow.com
http://android-er.blogspot.in/2014/04/example-of-viewpager-with-custom.html
https://github.com/BoD/android-contentprovider-generator ( for content provider generator)
https://futurestud.io/blog/retrofit-getting-started-and-android-client


#TODO : 
Code cleaning 
Code optimization to enhance performance
TestCase writing for unit & integration test

