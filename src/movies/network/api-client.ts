import {API_KEY} from '@env';
import {z} from 'zod';

const TRENDING_MOVIES_URL = 'https://api.themoviedb.org/3/trending/movie/week';

const movieSchema = z.object({
  backdrop_path: z.string().nullable(),
  id: z.number(),
  title: z.string(),
  original_title: z.string(),
  overview: z.string(),
  poster_path: z.string().nullable(),
  media_type: z.string(),
  adult: z.boolean(),
  original_language: z.string(),
  genre_ids: z.array(z.number()),
  popularity: z.number(),
  release_date: z.string(),
  video: z.boolean(),
  vote_average: z.number(),
  vote_count: z.number(),
});

const responseSchema = z.object({
  page: z.number(),
  results: z.array(movieSchema),
  total_pages: z.number(),
  total_results: z.number(),
});

export type MovieResponse = z.infer<typeof responseSchema>;

export const fetchMovies = async (
  page: number,
): Promise<MovieResponse | null> => {
  const urlParams = new URLSearchParams({
    api_key: API_KEY,
    page: page.toString(),
    language: 'de-DE',
  });

  const response = await fetch(
    `${TRENDING_MOVIES_URL}?${urlParams.toString()}`,
  );

  const data = await response.json();
  const parsedData = responseSchema.safeParse(data);

  if (!parsedData.success) {
    return null;
  }

  return parsedData.data;
};
