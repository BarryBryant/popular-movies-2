# Developing Android Apps Final Project

Built an app to help users discover popular and highly rated movies on the web. It displays a scrolling grid of movie trailers, launches a details screen whenever a particular movie is selected, allows users to save favorites, play trailers, and read user reviews. This app utilizes core Android user interface components and fetches movie information using themoviedb.org web API. API calls are performed asynchronously with the Retrofit library and favorited movies are stored in a Realm database.

___

![](http://i.imgur.com/yZjW1wo.gif) 

___

### Required Components

- [x] Movies are displayed in the main layout via a grid of their corresponding movie poster thumbnails
- [x] UI contains an element (e.g., a spinner or settings menu) to toggle the sort order of the movies by: most popular, highest rated, and favorites
- [x] UI contains a screen for displaying the details for a selected movie
- [x] Movie Details layout contains title, release date, movie poster, vote average, and plot synopsis.
- [x] Movie Details layout contains a section for displaying trailer videos and user reviews
- [x] Tablet UI uses a Master-Detail layout implemented using fragments. The left fragment is for discovering movies. The right fragment displays the movie details view for the currently selected movie.
- [x] When a user changes the sort criteria (most popular, highest rated, and favorites) the main view gets updated correctly.
- [x] When a movie poster thumbnail is selected, the movie details screen is launched [Phone] or displayed in a fragment [Tablet]
- [x] When a trailer is selected, app uses an Intent to launch the trailer
- [x] In the movies detail screen, a user can tap a button(for example, a star) to mark it as a Favorite
- [x] In a background thread, app queries the /discover/movies API with the query parameter for the sort criteria specified in the settings menu. (Note: Each sorting criteria is a different API call.)
- [x] This query can also be used to fetch the related metadata needed for the detail view.
- [x] App requests for related videos for a selected movie via the /movie/{id}/videos endpoint in a background thread and displays those details when the user selects a movie.
- [x] App requests for user reviews for a selected movie via the /movie/{id}/reviews endpoint in a background thread and displays those details when the user selects a movie.
- [x] App saves a “Favorited” movie to SharedPreferences or a database using the movie’s id.
- [x] When the “favorites” setting option is selected, the main view displays the entire favorites collection based on movie IDs stored in SharedPreferences or a database.

### Optional Components

- [x] App persists favorite movie details using a database
- [x] App displays favorite movie details (title, poster, synopsis, user rating, release date) even when offline
- [x] Movie Details View includes an Action Bar item that allows the user to share the first trailer video URL from the list of trailers
- [x] App uses a share Intent to expose the external youtube URL for the trailer
