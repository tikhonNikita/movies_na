import React from 'react';
import {View, StyleSheet} from 'react-native';
import {MovieListView, MoviesState} from 'react-native-movies-list';
import fakeMovies from './fakeMovies';

const onMoreMoviesRequested = () => {
  console.log('More movies requested');
};

const App = () => {
  return (
    <View style={styles.container}>
      <MovieListView
        moviesState={{
          state: MoviesState.Success,
          data: fakeMovies,
          canLoadMore: true,
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
