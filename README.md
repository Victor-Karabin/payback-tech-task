# payback-tech_task

# Getting Started

# Build and Test
* Before build the project add pixabay api key to local.properties file. Like this:

```
 pixabay.key=2626....
```

* To build the project, from the commandline, run ./gradlew clean build
* For running Unit Tests from the commandline, run ./gradlew test
* For running Android Tests from the commandline, run ./gradlew cAT
* For debugging TransactionTooLargeExceptions, run adb logcat -s TooLargeTool
* For debugging memory leaks, run adb logcat -s LeakCanary

# Test coverage
Quite a lot of functionality and code. Covering it completely with tests would take too long.
Only several tests have been added.

# Pagination
It should be nice to add pagination for the project (pixabay API supports it). But there are no requirements for it.
A simplified way was chosen.

# Modularization
Multi modular approach is overhead for simple projects like this. But for real project it's preferable in my opinion.

# Clean Architecture
There are only data, domain, presentation layers. Auxiliary layers are overhead for simple projects like this.
A simplified way was chosen.