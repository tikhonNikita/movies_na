import React from 'react';
import {View, StyleSheet} from 'react-native';
import {MoviesListView} from 'react-native-movies-list';


const App = () => {
  return (
    <View style={styles.container}>
      <MoviesListView color="#f3a512" style={styles.viewStyle} title={''}/>
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
