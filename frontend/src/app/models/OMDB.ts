export interface OMDBSearchResult {
  Search: OMDBMovie[]
  totalResults: number
  Response: string
}

export interface OMDBMovie {
  Title: string;
  Year: string;
  Rated: string;
  Released: string;
  Runtime: string;
  Genre: string;
  Director: string;
  Writer: string;
  Actors: string;
  Plot: string;
  Language: string;
  Country: string;
  Awards: string;
  Poster: string;
  Ratings: Rating[];
  Metascore: string;
  imdbRating: string;
  imdbVotes: string;
  imdbID: string;
  Type: string;
  totalSeasons: number;
  Response: string;
  BoxOffice: string;
  Production: string;
  DVD: string;
}

export interface Rating {
  Source: string;
  Value: string;
}

export interface JSONMovie {
  Title: string;
  Year: string;
  imdbID: string;
  Poster: string;
}
