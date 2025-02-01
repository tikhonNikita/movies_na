import codegenNativeComponent from 'react-native/Libraries/Utilities/codegenNativeComponent';
import type { ViewProps } from 'react-native';

interface NativeProps extends ViewProps {
  color?: string;
  title: string;
}

export default codegenNativeComponent<NativeProps>('MoviesListView');
