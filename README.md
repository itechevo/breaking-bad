# breaking-bad

Breaking Bad character info app showcasing MVVM and clean architecture

This application fetches breaking bad characters and details from backend and displays it on a recycle view.

Tech and libraries used: Kotlin, Coroutine, Koin, Retrofit, GSON, Glide, Mockk

MVVM design pattern and utilised Android Architecture Components, such as ViewModel and LiveData.

Package modularisation for clean code structure, DATA, DOMAIN and APP(UI).

Kept UI to minimal.

Added unit and UI tests using Mockk and Espresso.

## TODO

Due to time constrain, not done the following:

* Network availability check
* Remove few hardcoded dimens values
* Custom error handling and show error using error screen, currently using simple Snackbar.
* Caching responses to have offline mode
* Shared animation transition between main and detail UI
* Optimise recycle view with DiffUtil