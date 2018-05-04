
# Udacity Popular Movies App

This App was developed in context of the Udacity Android Developer Nanodegree Program.  

Most of us can relate to kicking back on the couch and enjoying a movie with friends and family. In this project, you’ll build an app to allow users to discover the most popular movies playing.

You’ll build the complete functionality of this app in two stages which you will submit separately.

## Getting Started
For retrieving Movie Data, this Project use the TMDB API. To run the App an API key is required.
You can retrieve one by creating an account and requesting it [here](https://www.themoviedb.org/).</br>
Add your API Key in res/values/api_keys.xml

## Used Resources
- Filter Icon (res/drawable/ic_filter.png) made by [Bogdan Rosu](https://www.flaticon.com/authors/bogdan-rosu) from [Flaticon](https://www.flaticon.com/) is licensed by [Creative Commons BY 3.0](http://creativecommons.org/licenses/by/3.0/).
- Play Icon (res/drawable/ic_play.png) made by [Freepik](https://www.flaticon.com/authors/freepik) from [Flaticon](https://www.flaticon.com/) is licensed by [Flaticon Basic License](https://file000.flaticon.com/downloads/license/license.pdf).
- Favorite Icon (res/drawable/ic_filter.png) made by [Google](https://www.flaticon.com/authors/google) from [Flaticon](https://www.flaticon.com/) is licensed by [Creative Commons BY 3.0](http://creativecommons.org/licenses/by/3.0/).

## Stages
### Stage 1: Main Discovery Screen, A Details View, and Settings
In this stage you’ll build the core experience of your movies app.

Your app will:

- Upon launch, present the user with an grid arrangement of movie posters.
- Allow your user to change sort order via a setting:
  - The sort order can be by most popular, or by top rated
- Allow the user to tap on a movie poster and transition to a details screen with additional information such as: 
    - original title
    - movie poster image thumbnail
    - A plot synopsis (called overview in the api)
    - user rating (called vote_average in the api)
    - release date
    
### Stage 2: Trailers, Reviews, and Favorites
In this stage you’ll add additional functionality to the app you built in Stage 1.


You’ll add more information to your movie details view:
- You’ll allow users to view and play trailers ( either in the youtube app or a web browser).
- You’ll allow users to read reviews of a selected movie.
- You’ll also allow users to mark a movie as a favorite in the details view by tapping a button(star). This is for a local movies collection that you will maintain and does not require an API request*.
- You’ll modify the existing sorting criteria for the main view to include an additional pivot to show their favorites collection.
