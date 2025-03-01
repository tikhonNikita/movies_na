import {TurboModuleRegistry} from 'react-native';
import type {TurboModule} from 'react-native';

import {Double, Int32} from 'react-native/Libraries/Types/CodegenTypes';

export type MovieCodegenType = {
  readonly id: Int32;
  readonly title: string;
  readonly url: string;
  readonly movieDescription: string;
  readonly rating: Double;
};

export interface Spec extends TurboModule {
  /**
   * Add a movie to favorites
   */
  addMovieToFavorites(movie: MovieCodegenType): Promise<number>;

  /**
   * Remove a movie from favorites
   */
  removeMovieFromFavorites(movieId: number): Promise<boolean>;

  /**
   * Check if a movie is in favorites
   */
  isMovieFavorite(movieId: number): Promise<boolean>;

  /**
   * Get all favorite movies
   */
  getFavoriteMovies(): Promise<MovieCodegenType[]>;
}

export default TurboModuleRegistry.getEnforcing<Spec>('FavoriteMoviesModule');
