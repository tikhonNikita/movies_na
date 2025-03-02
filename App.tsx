import React from 'react';
import 'react-native-gesture-handler';
import {createStaticNavigation} from '@react-navigation/native';
import {MoviesListScreen} from './src/MoviesListScreen';
import {FavoritesScreen} from './src/FavoritesScreen';
import {createDrawerNavigator} from '@react-navigation/drawer';

const NavigationDrawer = createDrawerNavigator({
  screens: {
    Home: MoviesListScreen,
    Profile: FavoritesScreen,
  },
});

const Navigation = createStaticNavigation(NavigationDrawer);

const App = () => {
  return <Navigation />;
};

export default App;
