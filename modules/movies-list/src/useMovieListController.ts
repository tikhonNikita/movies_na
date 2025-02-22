import {useCallback, useReducer} from 'react';
import {MovieCodegenType} from './MoviesListViewNativeComponent';
import {MovieListState, MoviesState} from './MovieListView';

const initialState: MovieListState = {
  state: MoviesState.Empty,
};

type MovieListAction =
  | {type: MoviesState.Loading}
  | {
      type: MoviesState.Success;
      payload: {data: MovieCodegenType[]; canLoadMore: boolean};
    }
  | {type: MoviesState.Error; payload: {message: string}}
  | {
      type: MoviesState.MoreMoviesLoaded;
      payload: {data: MovieCodegenType[]; canLoadMore: boolean};
    }
  | {type: MoviesState.LoadingMoreMovies};

const createSuccessData = (
  action: Extract<
    MovieListAction,
    {type: MoviesState.Success | MoviesState.MoreMoviesLoaded}
  >,
) => {
  return {
    data: action.payload.data,
    canLoadMore: action.payload.canLoadMore,
  };
};

const moviesReducer = (
  state: MovieListState,
  action: MovieListAction,
): MovieListState => {
  switch (action.type) {
    case MoviesState.Loading:
      return {state: MoviesState.Loading};
    case MoviesState.Success:
      return {
        state: MoviesState.Success,
        ...createSuccessData(action),
      };
    case MoviesState.MoreMoviesLoaded:
      return {
        state: MoviesState.MoreMoviesLoaded,
        ...createSuccessData(action),
      };
    case MoviesState.Error:
      return {state: MoviesState.Error, message: action.payload.message};
    case MoviesState.LoadingMoreMovies:
      return {state: MoviesState.LoadingMoreMovies};
    default:
      return state;
  }
};

type FetcherResponse = {
  data: MovieCodegenType[] | null;
  canLoadMore: boolean;
  errorMessage: string | null;
};

export type MoviesFetcher = (page: number) => Promise<FetcherResponse>;

type MovieListController = {
  moviesState: MovieListState;
  loadMoreMovies: (page: number) => Promise<void>;
  loadInitialMovies: () => void;
};

export const useMovieListController = (
  moviesFetcher: MoviesFetcher,
): MovieListController => {
  const [moviesState, dispatch] = useReducer(moviesReducer, initialState);

  const loadInitialMovies = useCallback(async () => {
    dispatch({type: MoviesState.Loading});

    const {data, canLoadMore, errorMessage} = await moviesFetcher(1);

    if (data !== null) {
      dispatch({type: MoviesState.Success, payload: {data, canLoadMore}});
    } else if (errorMessage !== null) {
      dispatch({type: MoviesState.Error, payload: {message: errorMessage}});
    }
  }, [moviesFetcher]);

  const loadMoreMovies = useCallback(
    async (page: number) => {
      dispatch({type: MoviesState.LoadingMoreMovies});
      const {data, canLoadMore, errorMessage} = await moviesFetcher(page);
      if (data !== null) {
        dispatch({
          type: MoviesState.MoreMoviesLoaded,
          payload: {data, canLoadMore},
        });
      } else if (errorMessage !== null) {
        dispatch({type: MoviesState.Error, payload: {message: errorMessage}});
      }
    },
    [moviesFetcher],
  );

  return {
    moviesState,
    loadMoreMovies,
    loadInitialMovies,
  };
};

export default useMovieListController;
