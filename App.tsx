import React from 'react';
import {View, StyleSheet} from 'react-native';
import {MoviesListView} from 'react-native-movies-list';

const App = () => {
  return (
    <View style={styles.container}>
      <MoviesListView
        color="#f5f512"
        style={{
          width: 300,
          height: 300,
        }}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
});

export default App;
