# EfficientLogs

Android logs which have no performance impact in release builds.

# Setup

build.gradle (app)

```groovy
dependencies {
    debugImplementation 'com.github.mrizyver.EfficientLogs:debug:1.2.1'
    releaseImplementation 'com.github.mrizyver.EfficientLogs:release:1.2.1'
}
```

build.gradle (.)

```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

# Using

```kotlin
class ClassName {
    fun methodName() {
        logv { "Verbose" }  //prints 'ClassName: methodName(): Verbose'
        logd { "Debug" }    //prints 'ClassName: methodName(): Debug'
        logi { "Info" }     //prints 'ClassName: methodName(): Info'
        logw { "Warning" }  //prints 'ClassName: methodName(): Warning'
        loge { "Error" }    //prints 'ClassName: methodName(): Error'
    }
}
```

## Save logs to a file

```kotlin
class Application {
    override fun onCreate() {
        logfile(File(cacheDir, "log.txt"))  //start writing to a log file
        logfile(null)                       //stop writing to a log file
    }
}
```

## Add a prefix to get an ability to filter only logs written in your project

```kotlin
class Application {
    override fun onCreate() {
        logprefix("my_prefix ")
    }
}

class ClassName {
    fun methodName() {
        logd { "Debug" }    //prints 'my_prefix ClassName: methodName(): Debug'
    }
}
```
