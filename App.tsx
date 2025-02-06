import React from 'react';
import {View, StyleSheet} from 'react-native';
import {MovieCodegenType, MovieListView, MoviesState} from 'react-native-movies-list';

const onMoreMoviesRequested = () => {
  console.log('More movies requested');
};

const fakeMovies: MovieCodegenType[] = [
  {
    id: 1,
    title: 'The Shawshank Redemption',
    url: 'https://www.imdb.com/title/tt0111161/',
    movieDescription:
      'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.',
    rating: 9.3,
  },
  {
    id: 2,
    title: 'The Godfather',
    url: 'https://www.imdb.com/title/tt0068646/',
    movieDescription:
      'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.',
    rating: 9.2,
  },
  {
    id: 3,
    title: 'The Dark Knight',
    url: 'https://www.imdb.com/title/tt0468569/',
    movieDescription:
      'When the menace known as the Joker emerges from his mysterious past, he wreaks havoc and chaos on the people of Gotham.',
    rating: 9.0,
  },
  {
    id: 4,
    title: '12 Angry Men',
    url: 'https://www.imdb.com/title/tt0050083/',
    movieDescription:
      'A jury holdout attempts to prevent a miscarriage of justice by forcing his colleagues to reconsider the evidence.',
    rating: 9.0,
  },
  {
    id: 5,
    title: "Schindler's List",
    url: 'https://www.imdb.com/title/tt0108052/',
    movieDescription:
      'In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce.',
    rating: 8.9,
  },
  {
    id: 6,
    title: 'The Lord of the Rings: The Return of the King',
    url: 'https://www.imdb.com/title/tt0167260/',
    movieDescription:
      "Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.",
    rating: 8.9,
  },
  {
    id: 7,
    title: 'Pulp Fiction',
    url: 'https://www.imdb.com/title/tt0110912/',
    movieDescription:
      'The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.',
    rating: 8.9,
  },
  {
    id: 8,
    title: 'The Good, the Bad and the Ugly',
    url: 'https://www.imdb.com/title/tt0060196/',
    movieDescription:
      'A bounty hunting scam joins two men in an uneasy alliance against a third in a race to find a fortune in gold buried in a remote cemetery.',
    rating: 8.8,
  },
  {
    id: 9,
    title: 'Fight Club',
    url: 'https://www.imdb.com/title/tt0137523/',
    movieDescription:
      'An insomniac office worker and a devil-may-care soap maker form an underground fight club that evolves into much more.',
    rating: 8.8,
  },
  {
    id: 10,
    title: 'Forrest Gump',
    url: 'https://www.imdb.com/title/tt0109830/',
    movieDescription:
      'The presidencies of Kennedy and Johnson, the Vietnam War, the Watergate scandal and other historical events unfold from the perspective of an Alabama man with an IQ of 75.',
    rating: 8.8,
  },
  {
    id: 11,
    title: 'Inception',
    url: 'https://www.imdb.com/title/tt1375666/',
    movieDescription:
      'A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.',
    rating: 8.8,
  },
];

const App = () => {
  return (
    <View style={styles.container}>
      <MovieListView
        moviesState={{
          state: MoviesState.Empty,
        }}
        style={styles.viewStyle}
        onMoreMoviesRequested={onMoreMoviesRequested}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  viewStyle: {
    flex: 1,
  },
});

export default App;
