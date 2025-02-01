import React from 'react';
import {View, StyleSheet, Button} from 'react-native';
import {MoviesListView} from 'react-native-movies-list';


const generateRandomSting = () => {
  return Math.random().toString(36).substring(7);
};

const App = () => {
  const [title, setTitle] = React.useState('smthing');
  return (
    <View style={styles.container}>
      <MoviesListView color="#f3a512" style={styles.viewStyle} title={title}/>
      <Button title="Press me" onPress={() => setTitle(generateRandomSting())} />
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
