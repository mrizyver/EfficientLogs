# EfficientLogs

Android logs which have no performance impact in release builds.

# Setup

build.gradle (app)

```groovy
dependencies {
    debugImplementation 'com.github.mrizyver.EfficientLogs:debug:1.1.1'
    releaseImplementation 'com.github.mrizyver.EfficientLogs:release:1.1.1'
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
        logv { "Verbose" }  //prints ClassName: methodName(): Verbose
        logd { "Debug" }    //prints ClassName: methodName(): Debug
        logi { "Info" }     //prints ClassName: methodName(): Info
        logw { "Warning" }  //prints ClassName: methodName(): Warning
        loge { "Error" }    //prints ClassName: methodName(): Error
    }
}
```
