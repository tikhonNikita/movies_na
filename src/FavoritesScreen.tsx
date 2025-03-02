import React, {FC, useCallback} from 'react';
import {
  View,
  Text,
  StyleSheet,
  FlatList,
  Image,
  TouchableOpacity,
  ActivityIndicator,
  RefreshControl,
  Alert,
  ListRenderItem,
} from 'react-native';
import {useFavoriteMovies, MovieCodegenType} from 'react-native-movies-list';

const handleRemoveConfirmation = (
  movie: MovieCodegenType,
  onRemove: (id: number) => void,
): void => {
  Alert.alert(
    'Remove from Favorites',
    `Are you sure you want to remove "${movie.title}" from your favorites?`,
    [
      {text: 'Cancel', style: 'cancel'},
      {
        text: 'Remove',
        style: 'destructive',
        onPress: () => onRemove(movie.id),
      },
    ],
  );
};

type MovieRatingProps = {
  rating: number;
};

const MovieRating: FC<MovieRatingProps> = ({rating}) => (
  <View style={styles.ratingContainer}>
    <Text style={styles.ratingLabel}>Rating:</Text>
    <Text style={styles.rating}>★ {rating.toFixed(1)}</Text>
  </View>
);

type MovieHeaderProps = {
  title: string;
  onRemove: () => void;
};

const MovieHeader: FC<MovieHeaderProps> = ({title, onRemove}) => (
  <View style={styles.movieHeader}>
    <Text style={styles.movieTitle} numberOfLines={1}>
      {title}
    </Text>
    <TouchableOpacity
      onPress={onRemove}
      style={styles.removeButton}
      hitSlop={{top: 10, right: 10, bottom: 10, left: 10}}>
      <Text style={styles.removeButtonText}>✕</Text>
    </TouchableOpacity>
  </View>
);

type MovieItemProps = {
  movie: MovieCodegenType;
  onRemove: (id: number) => void;
};

const MovieItem: FC<MovieItemProps> = ({movie, onRemove}) => {
  const handleRemove = useCallback(() => {
    handleRemoveConfirmation(movie, onRemove);
  }, [movie, onRemove]);

  return (
    <View style={styles.movieCard}>
      <View style={styles.movieImageContainer}>
        <Image
          source={{uri: movie.url || 'https://via.placeholder.com/150'}}
          style={styles.movieImage}
        />
      </View>
      <View style={styles.movieDetails}>
        <MovieHeader title={movie.title} onRemove={handleRemove} />
        <Text style={styles.movieDescription} numberOfLines={3}>
          {movie.movieDescription}
        </Text>
        <MovieRating rating={movie.rating} />
      </View>
    </View>
  );
};

const Separator: FC = () => <View style={styles.separator} />;

const LoadingState: FC = () => (
  <View style={styles.centerContainer}>
    <ActivityIndicator size="large" color="#6750A4" />
  </View>
);

const EmptyState: FC = () => (
  <View style={styles.centerContainer}>
    <Text style={styles.emptyText}>No favorite movies yet</Text>
    <Text style={styles.emptySubtext}>
      Add movies to your favorites to see them here
    </Text>
  </View>
);

type ErrorStateProps = {
  error: string | null;
  onRetry: () => void;
};

const ErrorState: FC<ErrorStateProps> = ({error, onRetry}) => (
  <View style={styles.centerContainer}>
    <Text style={styles.errorText}>{error}</Text>
    <TouchableOpacity style={styles.retryButton} onPress={onRetry}>
      <Text style={styles.retryButtonText}>Retry</Text>
    </TouchableOpacity>
  </View>
);

type EmptyListComponentProps = {
  isLoading: boolean;
};

const EmptyListComponent: FC<EmptyListComponentProps> = ({isLoading}) => {
  if (isLoading) {
    return <LoadingState />;
  }
  return <EmptyState />;
};

export const FavoritesScreen: FC = () => {
  const {
    favoriteMovies,
    isLoading,
    error,
    removeFromFavorites,
    refreshFavorites,
  } = useFavoriteMovies();

  const handleRemoveMovie = useCallback(
    async (id: number) => {
      try {
        await removeFromFavorites(id);
      } catch (e) {
        Alert.alert('Error', 'Failed to remove movie from favorites');
      }
    },
    [removeFromFavorites],
  );

  const renderItem: ListRenderItem<MovieCodegenType> = ({item}) => (
    <MovieItem movie={item} onRemove={handleRemoveMovie} />
  );

  if (error) {
    return <ErrorState error={error} onRetry={refreshFavorites} />;
  }

  return (
    <View style={styles.container}>
      <FlatList
        data={favoriteMovies}
        keyExtractor={item => item.id.toString()}
        renderItem={renderItem}
        contentContainerStyle={
          favoriteMovies.length === 0 ? styles.flatListEmpty : styles.flatList
        }
        ListEmptyComponent={<EmptyListComponent isLoading={isLoading} />}
        ItemSeparatorComponent={Separator}
        refreshControl={
          <RefreshControl
            refreshing={isLoading}
            onRefresh={refreshFavorites}
            colors={['#6750A4']}
          />
        }
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#FEFBFF',
  },
  flatList: {
    padding: 16,
  },
  flatListEmpty: {
    flexGrow: 1,
  },
  centerContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 20,
  },
  movieCard: {
    flex: 1,
    flexDirection: 'row',
    backgroundColor: '#FFFFFF',
    marginBottom: 16,
    borderRadius: 12,
    boxShadow: '0px 4px 10px rgba(0, 0, 0, 0.1)',
    shadowRadius: 50,
    overflow: 'hidden',
  },
  movieImageContainer: {
    borderTopLeftRadius: 12,
    borderBottomLeftRadius: 12,
  },
  movieImage: {
    width: 100,
    height: 150,
    backgroundColor: '#E7E0EC',
  },
  movieDetails: {
    flex: 1,
    padding: 16,
    justifyContent: 'space-between',
  },
  movieHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'flex-start',
    marginBottom: 8,
  },
  movieTitle: {
    flex: 1,
    fontSize: 18,
    fontWeight: 'bold',
    color: '#1B1B1F',
    marginRight: 8,
  },
  movieDescription: {
    fontSize: 14,
    color: '#49454E',
    marginBottom: 12,
    lineHeight: 20,
    flex: 1,
  },
  ratingContainer: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  ratingLabel: {
    fontSize: 14,
    fontWeight: '500',
    color: '#49454E',
    marginRight: 4,
  },
  rating: {
    fontSize: 16,
    fontWeight: '500',
    color: '#8C4A8E',
  },
  removeButton: {
    padding: 4,
    justifyContent: 'center',
    alignItems: 'center',
  },
  removeButtonText: {
    fontSize: 16,
    color: '#BA1A1A',
    fontWeight: 'bold',
  },
  emptyText: {
    fontSize: 20,
    fontWeight: 'bold',
    color: '#1B1B1F',
    marginBottom: 8,
  },
  emptySubtext: {
    fontSize: 14,
    color: '#49454E',
    textAlign: 'center',
  },
  errorText: {
    fontSize: 16,
    color: '#BA1A1A',
    marginBottom: 16,
    textAlign: 'center',
  },
  retryButton: {
    backgroundColor: '#6750A4',
    paddingVertical: 10,
    paddingHorizontal: 24,
    borderRadius: 20,
  },
  retryButtonText: {
    color: '#FFFFFF',
    fontWeight: '500',
  },
  separator: {
    height: 8,
  },
});
