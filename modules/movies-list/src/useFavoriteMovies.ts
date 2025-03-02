import {useState, useCallback, useEffect} from 'react';
import FavoriteMoviesModule from './NativeFavoriteMoviesModule';
import type {MovieCodegenType} from './MoviesListViewNativeComponent';

export type UseFavoriteMoviesResult = {
  favoriteMovies: MovieCodegenType[];
  isLoading: boolean;
  error: string | null;
  addToFavorites: (movie: MovieCodegenType) => Promise<void>;
  removeFromFavorites: (movieId: number) => Promise<void>;
  isFavorite: (movieId: number) => Promise<boolean>;
  refreshFavorites: () => Promise<void>;
};

export const useFavoriteMovies = (): UseFavoriteMoviesResult => {
  const [favoriteMovies, setFavoriteMovies] = useState<MovieCodegenType[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const fetchFavoriteMovies = useCallback(async () => {
    try {
      setIsLoading(true);
      setError(null);
      const movies = await FavoriteMoviesModule.getFavoriteMovies();
      setFavoriteMovies(movies);
    } catch (err) {
      const errorMessage =
        err instanceof Error ? err.message : 'Failed to fetch favorite movies';
      setError(errorMessage);
      console.error('Error fetching favorites:', errorMessage);
    } finally {
      setIsLoading(false);
    }
  }, []);

  const addToFavorites = useCallback(
    async (movie: MovieCodegenType) => {
      try {
        setError(null);
        await FavoriteMoviesModule.addMovieToFavorites(movie);
        await fetchFavoriteMovies();
      } catch (err) {
        const errorMessage =
          err instanceof Error
            ? err.message
            : 'Failed to add movie to favorites';
        setError(errorMessage);
      }
    },
    [fetchFavoriteMovies],
  );

  const removeFromFavorites = useCallback(async (movieId: number) => {
    try {
      setError(null);
      const success = await FavoriteMoviesModule.removeMovieFromFavorites(
        movieId,
      );
      if (success) {
        setFavoriteMovies(prev => prev.filter(movie => movie.id !== movieId));
      } else {
        throw new Error('Failed to remove movie from favorites');
      }
    } catch (err) {
      const errorMessage =
        err instanceof Error
          ? err.message
          : 'Failed to remove movie from favorites';
      setError(errorMessage);
      console.error('Error removing from favorites:', errorMessage);
    }
  }, []);

  const isFavorite = useCallback(async (movieId: number): Promise<boolean> => {
    try {
      setError(null);
      return await FavoriteMoviesModule.isMovieFavorite(movieId);
    } catch (err) {
      const errorMessage =
        err instanceof Error ? err.message : 'Failed to check favorite status';
      setError(errorMessage);
      console.error('Error checking favorite status:', errorMessage);
      return false;
    }
  }, []);

  useEffect(() => {
    fetchFavoriteMovies();
  }, [fetchFavoriteMovies]);

  return {
    favoriteMovies,
    isLoading,
    error,
    addToFavorites,
    removeFromFavorites,
    isFavorite,
    refreshFavorites: fetchFavoriteMovies,
  };
};
