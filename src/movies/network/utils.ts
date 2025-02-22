export type SuccessResult<T> = {
  data: T;
  errorMessage: null;
};

export type ErrorResult = {
  data: null;
  errorMessage: string;
};

export type FetchResult<T = unknown> = SuccessResult<T> | ErrorResult;

export const createError = (errorMessage: string): ErrorResult => ({
  data: null,
  errorMessage,
});

export const createResult = <T>(data: T): FetchResult<T> => ({
  data,
  errorMessage: null,
});

export const isErrorResponse = (result: FetchResult): result is ErrorResult =>
  result.errorMessage !== null;

export const getErrorMessage = (error: unknown): string => {
  if (error instanceof Error) {
    return error.message;
  }
  return 'Something went wrong';
};
