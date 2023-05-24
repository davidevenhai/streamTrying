package org.example;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MovieLibrary {
    private List<Movie> movies;
    private List<Actor> actors;

    public MovieLibrary() {
        List<Director> directors = Utils.readFile("directors").stream().map(Director::new).toList();
        this.actors = Utils.readFile("actors").stream().map(Actor::new).toList();
        this.movies = Utils.readFile("movies")
                .stream()
                .map(item -> {
                    int id = Integer.parseInt(item.get(0));
                    String title = item.get(1);
                    Director director = directors.stream().filter(dir -> dir.getId() == Integer.parseInt(item.get(2))).findFirst().orElse(null);
                    int actor1IdFromFile = Integer.parseInt(item.get(3));
                    int actor2IdFromFile = Integer.parseInt(item.get(4));
                    List<Actor> actorsList = actors.stream().filter(act -> act.getId() == actor1IdFromFile || act.getId() == actor2IdFromFile).distinct().toList();
                    int year = Integer.parseInt(item.get(5));
                    String genre = item.get(6);
                    return new Movie(id, title, director, actorsList, year, genre);
                }).toList();
        List<String> names = findMoviesByYear(2012);
        System.out.println(names);
        System.out.println(findAverageReleaseYearForDirector("David Fincher"));
    }


    //    // 1. Find all movies released in a specific year.
//    // Return a list of movie titles.
    public List<String> findMoviesByYear(int year) {
        return this.movies.stream().filter(e -> e.getReleaseYear() == year).map(Movie::getTitle).toList();
    }

    //
//    // 2. Find all movies of a specific genre.
//    // Return a list of movie titles.
    public List<String> findMoviesByGenre(String genre) {
        return this.movies.stream().filter(e -> e.getGenre().equals(genre)).map(Movie::getTitle).toList();
    }

    //
//    // 3. Find all directors who have directed at least N movies.
//    // Return a list of director names.
    public List<String> findDirectorsWithAtLeastNMovies(int n) {
        Map<String, Long> direct = this.movies.stream().collect(Collectors.
                groupingBy(e -> e.getDirector().getName(), Collectors.counting()));
        return direct.entrySet().stream().filter(e -> e.getValue() >= n).map(Map.Entry::getKey).toList();
    }

    //
//    // 4. Find all actors who have appeared in movies of a specific genre.
//    // Return a list of actor names.
    public List<String> findActorsInGenre(String genre) {
        List<List<Actor>> actor = this.movies.stream().filter(movie -> movie.getGenre().equals(genre))
                .map(Movie::getActors).toList();
        return actor.stream().flatMap(List::stream).map(Actor::getName).toList();
    }
//
//    // 5. Find the average release year of movies for a specific director.
//    // Return a double value.
    public double findAverageReleaseYearForDirector(String directorName) {
        double number = this.movies.stream().filter(e->e.getDirector()!=null&&e.getDirector().getName().equals(directorName)
        ).map(Movie::getReleaseYear).count();
        return number;
    }
//
//    // 6. Find the top N actors who have appeared in the most movies.
//    // Return a list of actor names.
//    public List<String> findTopNActors(int n) {
//        // Implement using declarative programming
//    }
//
//    // 7. Find all movies where a specific actor and director have worked together.
//    // Return a list of movie titles.
//    public List<String> findMoviesByActorAndDirector(String actorName, String directorName) {
//        // Implement using declarative programming
//    }
//
//    // 8. Find the most common genre for each actor.
//    // Return a map with actor names as keys and the most common genre as values.
//    public Map<String, String> findMostCommonGenrePerActor() {
//        // Implement using declarative programming
//    }
//
//    // 9. Find the director with the highest average movie rating (1-5).
//    // Assume there is a method: double getMovieRating(Movie movie), which returns the rating of a movie.
//    // Return the director name.
//    public String findDirectorWithHighestAverageRating() {
//        // Implement using declarative programming
//    }


}
