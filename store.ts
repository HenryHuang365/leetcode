import { refreshSlice } from "@/lib/refresh/refreshSlice";
import { counterSlice } from "@/lib/counter/counterSlice";
import type { Action, ThunkAction } from "@reduxjs/toolkit";
import { combineReducers, configureStore } from "@reduxjs/toolkit";
import {
  FLUSH,
  PAUSE,
  PERSIST,
  PURGE,
  REGISTER,
  REHYDRATE,
  persistStore,
} from "redux-persist";
import persistReducer from "redux-persist/lib/persistReducer";
import storage from "redux-persist/lib/storage";
import { navbarSlice } from "@/lib/navbar/navbarSlice";
import { timePeriodSlice } from "@/lib/timePeriodSlice";
import { apiSlice } from "@/lib/api/apiSlice";
import { authSlice } from "@/lib/auth/authSlice";
import { exportSlice } from "@/lib/export/exportSlice";
import { mockDataSlice } from "@/lib/api/mocks/mockDataSlice";
import { keycloakSlice } from "@/lib/auth/keycloakSlice";
import { siteSlice } from "@/lib/site/siteSlice";
import { selectedDevicesSlice } from "@/lib/selectedDevicesSlice";
import { visualParamsSlice } from "@/lib/visualParamsSlice";
import { overviewSlice } from "@/lib/overview/overviewSlice";

export const persistConfig = {
  key: "root",
  storage,
  blacklist: [
    "counter",
    "refresh",
    apiSlice.reducerPath,
    mockDataSlice.reducerPath,
    keycloakSlice.reducerPath,
    selectedDevicesSlice.reducerPath,
  ],
};

const rootReducer = combineReducers({
  [counterSlice.reducerPath]: persistReducer(
    {
      key: "counter",
      storage,
      blacklist: ["fetchStatus"],
    },
    counterSlice.reducer
  ),
  [refreshSlice.reducerPath]: persistReducer(
    {
      key: "refresh",
      storage,
      blacklist: ["last_refreshed_at", "refresh_tasks"],
    },
    refreshSlice.reducer
  ),
  [keycloakSlice.reducerPath]: persistReducer(
    {
      key: "keycloak",
      storage,
      blacklist: ["keycloakInitialised"],
    },
    keycloakSlice.reducer
  ),
  [exportSlice.reducerPath]: exportSlice.reducer,
  [siteSlice.reducerPath]: siteSlice.reducer,
  [navbarSlice.reducerPath]: navbarSlice.reducer,
  [overviewSlice.reducerPath]: overviewSlice.reducer,
  [timePeriodSlice.reducerPath]: timePeriodSlice.reducer,
  [authSlice.reducerPath]: authSlice.reducer,
  [apiSlice.reducerPath]: apiSlice.reducer,
  [mockDataSlice.reducerPath]: mockDataSlice.reducer,
  [selectedDevicesSlice.reducerPath]: selectedDevicesSlice.reducer,
  [visualParamsSlice.reducerPath]: visualParamsSlice.reducer,
});

const persistedReducer = persistReducer(persistConfig, rootReducer);
// Infer the `RootState` type from the root reducer
export type RootState = ReturnType<typeof persistedReducer>;

// `makeStore` encapsulates the store configuration to allow
// creating unique store instances, which is particularly important for
// server-side rendering (SSR) scenarios. In SSR, separate store instances
// are needed for each request to prevent cross-request state pollution.
export const makeStore = () => {
  const store = configureStore({
    reducer: persistedReducer,
    devTools: process.env.NODE_ENV !== "production",
    // Adding the api middleware enables caching, invalidation, polling,
    // and other useful features of `rtk-query`.
    middleware: (getDefaultMiddleware) => {
      return getDefaultMiddleware({
        serializableCheck: {
          ignoredActions: [FLUSH, REHYDRATE, PAUSE, PERSIST, PURGE, REGISTER],
        },
      }).concat(apiSlice.middleware);
    },
  });
  return {
    store: store,
    persistor: persistStore(store),
  };
};

// Infer the return type of `makeStore`
export type AppStore = ReturnType<typeof makeStore>["store"];
// Infer the `AppDispatch` type from the store itself
export type AppDispatch = AppStore["dispatch"];
export type AppThunk<ThunkReturnType = void> = ThunkAction<
  ThunkReturnType,
  RootState,
  unknown,
  Action
>;
