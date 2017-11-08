
This is an Android application that allow to find random Pokemons, catch them in a backpack (persisten) and see the captured list of pokemon.

## Compilation
There are two buildTypes (_debug_ and _release_) and two product flavors (_product_ and _dev_). This would generate 4 variants, but the useless have been removed (line 55 app/build.gradle).

- **devDebug:** intended for development, latest android SDK (for faster builds), code not minified and debuggable.
- **productRelase:** used for releases, with Android 16 for minSdk. The code is minified with proguard, and the final apk is signed using a keystore.


## Design
There was no predefined design. Only some specific data was required to be displayed for captured or new pokemon. Also, for the backpack, was specified that _As there are many Pokemons, think of ​a ​design ​that ​can ​fit ​as ​many ​Pokemons ​as ​possible ​on ​screen_. Thus, a Grid Layout with 3 columns has been chosen for the backpack.

Most actions are executed with Floating Action Button: catch pokemon, dismiss pokemon, clear backpack, remove pokemon from backpack. All irreversible actions must be confirmed (so, all except catching a pokemon).

In the main view (the one where new pokemons appear), there is an actionbar button to show the backpack.

## Architecture
The whole project is implemented following a clean architecture. The solution has been over-engineered just to show how an Android application should be designed.

Different modules have been used to separate each layer. This is not only for the sake of separation of concerns, it's also because modularisation may improve the compilation times ([read this article for more info][modularisation]).

The four modules are:

- **model**: All the classes holding the information used by our app.
- **server**: all the networking is doing here. Each response from the server has its own data classes different than those in the **model** package, so both can be changed independently. Currently there is only one request to get a random Pokemon from the server.
- **database**: all the persistency layer is here. Current version is just a temporary solution to show how to manage the data with the Shared Preferences, but a real solution with SQLite or any other valid solution should be implemented.
- **provider**: It's the only thing known by the view and contains the business part. Connects to the server or the database to fetch the info, transform it into the **model** data types, and return the data asynchronously.
- **app**: all the activities and view related code.

If we required storing info in a local database, a new module could be created and the provider would decide whether fetch the info from the server or from the DB. This way the view doesn't care about where the data comes from.

Different classes are defined for server objects and local objects. In this example app, it almost doesn't make sense, since the responses are pretty simple, but in real projects this is quite useful. For example, some limited types could be sent as integer or string from the server. In our local model we could use enums instead. In this case


## Libraries
- Retrofit, for networkig
- Glide for image loading
- ButtnerKnife, for view injection
- JUnit and Mockito, for testing
- Stetho for debugging requests and DBs

## Testing
A project this simple doesn't allow many possibilities for testing, but some unit testing has been added. UI testing would have required more time to implement it.

Manual testing has been done on physical Nexus 5X device 8.0 and on emulator Nexus S 4.1.

## Keystore

The fake release keystore has been created with:

```
	keytool -genkey -alias pokemon \

    -keyalg RSA -keystore mykey.keystore \
    
    -dname "CN=Roldán Galán, OU=freelance, O=, L=Barcelona, S=Cataluña, C=ES" \
    
    -storepass android -keypass android
```


[modularisation]: https://medium.freecodecamp.org/how-modularisation-affects-build-time-of-an-android-application-43a984ce9968