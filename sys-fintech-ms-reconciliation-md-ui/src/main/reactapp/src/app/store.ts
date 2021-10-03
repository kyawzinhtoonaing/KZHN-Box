import { configureStore, ThunkAction, Action } from '@reduxjs/toolkit';
import rcReducer from '../features/rc/rcSlice';

export const store = configureStore({
  reducer: {
    rc: rcReducer
  },
});

export type AppDispatch = typeof store.dispatch;
export type RootState = ReturnType<typeof store.getState>;
export type AppThunk<ReturnType = void> = ThunkAction<
  ReturnType,
  RootState,
  unknown,
  Action<string>
>;
