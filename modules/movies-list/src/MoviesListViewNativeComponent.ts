import codegenNativeComponent from 'react-native/Libraries/Utilities/codegenNativeComponent';
import type {ViewProps} from 'react-native';
import {
  DirectEventHandler,
  Int32,
  Double,
} from 'react-native/Libraries/Types/CodegenTypes';


export type MovieCodegenType = {
  readonly id: Int32;
  readonly title: string;
  readonly url: string;
  readonly movieDescription: string;
  readonly rating: Double;
};

export type MovieListStateCodegenType = {
  state?: string;
  data?: MovieCodegenType[];
  canLoadMore?: boolean;
  message?: string;
};

interface NativeProps extends ViewProps {
  readonly moviesState?: MovieListStateCodegenType;
  readonly onMoreMoviesRequested: DirectEventHandler<null>;
}

export default codegenNativeComponent<NativeProps>('MoviesListView', {
  excludedPlatforms: ['iOS'],
});
