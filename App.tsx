import React, {useEffect, useState} from 'react';
import {View, StyleSheet, Button} from 'react-native';
import {
  MovieCodegenType,
  MovieListView,
  MoviesFetcher,
  useMovieListController,
} from 'react-native-movies-list';
import {ApiMovie, fetchMovies} from './src/movies/network/api-client';
import {isErrorResponse} from './src/movies/network/utils';

const convertMovieToCodegenType = (movie: ApiMovie): MovieCodegenType => {
  return {
    id: movie.id,
    title: movie.title,
    url: movie.poster_path
      ? `https://image.tmdb.org/t/p/w500${movie.poster_path}`
      : '',
    movieDescription: movie.overview,
    rating: movie.vote_average,
  };
};

const fetchMoviesAdapter: MoviesFetcher = async (page: number) => {
  const response = await fetchMovies(page);
  if (isErrorResponse(response)) {
    return {
      data: null,
      canLoadMore: false,
      errorMessage: response.errorMessage,
    };
  }

  return {
    data: response.data.results.map(convertMovieToCodegenType),
    canLoadMore: response.data.page < response.data.total_pages,
    errorMessage: null,
  };
};

const App = () => {
  const [currentPage, setCurrentPage] = useState(1);
  const {moviesState, loadInitialMovies, loadMoreMovies} =
    useMovieListController(fetchMoviesAdapter);

  const requestMoreMovies = () => {
    setCurrentPage(prev => prev + 1);
  };

  useEffect(() => {
    loadInitialMovies();
  }, [loadInitialMovies]);

  useEffect(() => {
    if (currentPage > 1) {
      loadMoreMovies(currentPage);
    }
  }, [currentPage, loadMoreMovies]);

  return (
    <View style={styles.container}>
      <MovieListView
        moviesState={moviesState}
        style={styles.viewStyle}
        onMoreMoviesRequested={requestMoreMovies}
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
