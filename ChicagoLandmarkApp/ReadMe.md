# Chicago Landmark App

## Project Overview

The Chicago Landmark App is an Android application designed to showcase a list of Chicago landmarks and provide additional details through web pages. The app is built to handle both portrait and landscape orientations and utilizes fragments and ViewModel for efficient data management and user interaction.

## Features

1. **Landmark List**:
    - Displays a list of Chicago landmarks using a `ListView`.
    - Uses `ArrayAdapter` to populate the list with landmark data.
    - Handles item selection and communicates with the `ViewModel` to update the selected item.

2. **Web Page Display**:
    - Displays web pages with detailed information about the selected landmark using a `WebView`.
    - Observes the `ViewModel` for changes and updates the web page accordingly.

3. **Orientation Handling**:
    - Supports both portrait and landscape orientations.
    - Adjusts the layout and fragment visibility based on the orientation.

4. **Options Menu**:
    - Provides options to exit the app or launch a new app (Activity 2).
    - Includes a menu item for launching the second activity from another application.

## Key Components

### LandmarkFragment

- **Purpose**: Displays a list of Chicago landmarks using a `ListView`.
- **Functionality**:
  - Uses `ArrayAdapter` to populate the list with landmark data.
  - Handles item selection and communicates with the `ViewModel` to update the selected item.
- **Key Methods**:
  - `onViewCreated()`: Sets up the `ListView` and its adapter.
  - `onSaveInstanceState()`: Saves and restores the selected item state.

### ListViewModel

- **Purpose**: Manages the data related to the selected landmark.
- **Functionality**:
  - Uses `MutableLiveData` to track the currently selected landmark.
  - Provides methods for selecting an item and observing changes.
- **Key Methods**:
  - `selectItem(Integer item)`: Updates the selected item.
  - `getSelectedItem()`: Provides `LiveData` for observing selected item changes.

### MainActivity

- **Purpose**: Controls the layout and fragment interactions.
- **Functionality**:
  - Configures the layout based on device orientation.
  - Manages fragment transactions and ViewModel interactions.
  - Handles menu options for additional actions.
- **Key Methods**:
  - `onCreate()`: Initializes the layout and fragments based on orientation.
  - `landscapeMode()`: Configures layout for landscape orientation.
  - `portraitMode()`: Configures layout for portrait orientation.
  - `setLayout()`: Adjusts layout parameters based on fragment visibility.
  - `onCreateOptionsMenu()`: Inflates the options menu.
  - `onOptionsItemSelected()`: Handles options menu item selections.

### WebPageFragment

- **Purpose**: Displays a web page with information about the selected landmark.
- **Functionality**:
  - Uses `WebView` to load and display web content.
  - Observes the `ViewModel` for changes and updates the web page accordingly.
- **Key Methods**:
  - `onCreateView()`: Inflates the fragment layout.
  - `onViewCreated()`: Initializes the `WebView` and sets up the `ViewModel`.
  - `isValidPageIndex()`: Checks if the page index is within a valid range.

### Installation Instructions

- **Android Studio**: IDE for Android development.
- **Android SDK**: Required for building and testing Android apps.
