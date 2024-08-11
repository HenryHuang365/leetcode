import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface VisualParamsState {
  params: Record<string, string[]>;  // 'params' is an object where each key is a device name and the value is an array of strings
}

const initialState: VisualParamsState = {
  params: {}, 
};

/**
 * Visualisation Parameters Slice
 * Stores the current state of the visualisation parameters
 */
export const visualParamsSlice = createSlice({
  name: "visualParams",
  initialState,
  reducers: {
    setVisualParamsDevices: (state: VisualParamsState, action: PayloadAction<Record<string, string[]>>) => {
      state.params = action.payload;
    },
  },
});


export const {
  setVisualParamsDevices,
} = visualParamsSlice.actions;

export const selectVisualParamsDevices = (state: { visualParams: VisualParamsState }) => state.visualParams.params;

export default visualParamsSlice.reducer;
