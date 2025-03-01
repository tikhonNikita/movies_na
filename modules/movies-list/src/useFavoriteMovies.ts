import { useState, useCallback, useEffect } from 'react';
import FavoriteMoviesModule from './NativeFavoriteMoviesModule';
import type { MovieCodegenType } from './MoviesListViewNativeComponent';

type UseFavoriteMoviesResult = {
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
      setError(err instanceof Error ? err.message : 'Failed to fetch favorite movies');
    } finally {
      setIsLoading(false);
    }
  }, []);

  const addToFavorites = useCallback(async (movie: MovieCodegenType) => {
    try {
      await FavoriteMoviesModule.addMovieToFavorites(movie);
      await fetchFavoriteMovies();
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Failed to add movie to favorites');
    }
  }, [fetchFavoriteMovies]);

  const removeFromFavorites = useCallback(async (movieId: number) => {
    try {
      const success = await FavoriteMoviesModule.removeMovieFromFavorites(movieId);
      if (success) {
        setFavoriteMovies(prev => prev.filter(movie => movie.id !== movieId));
      }
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Failed to remove movie from favorites');
    }
  }, []);

  const isFavorite = useCallback(async (movieId: number): Promise<boolean> => {
    try {
      return await FavoriteMoviesModule.isMovieFavorite(movieId);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Failed to check favorite status');
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
