# Twitter Hashtags Tracker

Hashtags tracker is a simple application to track Twitter hashtags. The app architecture folows MVP pattern. It is based on Dagger 2 for dependency injection and it use RxJava.

### Tested on
  - Nexus 6P, Android 6.0.1

### Future imrovements (todo's)

Currently the app uses Rx unsubscription for API calls interruption (when user delete hashtag). It makes impossible to cache data. It should be refactored to provide caching mechanizm with reset option and interrupt API calls using Retofit.





