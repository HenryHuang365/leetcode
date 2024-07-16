import { createSlice } from "@reduxjs/toolkit";

/**
 * Selected Devices Slice
 * Stores the current state of the selected devices
 */
export const selectedDevicesSlice = createSlice({
  name: "selectedDevices",
  initialState: {
    devices: [] as string[][],
  },
  selectors: {
    selectSelectedDevices: (state) => state.devices,
    selectIsActiveSelection: (state) => state.devices.length > 0,
    selectIsMultigraph: (state) =>
      state.devices.length > 0 && state.devices[0].length > 1,
  },
  reducers: {
    setSelectedDevices: (state, action) => {
      state.devices = action.payload;
    },
    toggleDeviceSelection: (state, action) => {
      const deviceId = action.payload;
      const deviceIndex = state.devices.findIndex((device) =>
        device.includes(deviceId)
      );

      if (deviceIndex === -1) {
        state.devices.push([deviceId]);
      } else {
        state.devices[deviceIndex] = state.devices[deviceIndex].filter(
          (id) => id !== deviceId
        );
        if (state.devices[deviceIndex].length === 0) {
          state.devices.splice(deviceIndex, 1);
        }
      }
    },
    resetSelectedDevices: (state) => {
      console.log("Resetting selected devices");
      state.devices = [];
    },
  },
});

export const { setSelectedDevices, toggleDeviceSelection, resetSelectedDevices } =
  selectedDevicesSlice.actions;
export const {
  selectSelectedDevices,
  selectIsActiveSelection,
  selectIsMultigraph,
} = selectedDevicesSlice.selectors;

export default selectedDevicesSlice.reducer;
