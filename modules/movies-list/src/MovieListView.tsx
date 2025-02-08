import React, { FC } from 'react';

import MoviesListViewNativeComponent from './MoviesListViewNativeComponent';
import type { MovieCodegenType } from './MoviesListViewNativeComponent';
import { ViewProps } from 'react-native';

export enum MoviesState {
    Loading = 'Loading',
    Success = 'Success',
    MoreMoviesLoaded = 'success_more',
    LoadingMoreMovies = 'loading_more',
    Error = 'Error',
    Empty = 'Empty'
}

export type MovieListState =
    | { state: MoviesState.Loading }
    | { state: MoviesState.Success; data: MovieCodegenType[]; canLoadMore: boolean }
    | { state: MoviesState.MoreMoviesLoaded; data: MovieCodegenType[]; canLoadMore: boolean }
    | { state: MoviesState.Error; message?: string }
    | { state: MoviesState.Empty }
    | { state: MoviesState.LoadingMoreMovies }


interface MovieListProps {
  readonly moviesState: MovieListState;
  readonly onMoreMoviesRequested: () => void;
  readonly style?: ViewProps['style'];
}

export const MovieListView: FC<MovieListProps> = ({moviesState, onMoreMoviesRequested, style}) => {
  const moviesStateString = { ...moviesState, state: moviesState.state.toString() };
  return <MoviesListViewNativeComponent style={style} moviesState={moviesStateString} onMoreMoviesRequested={onMoreMoviesRequested} />;
};

