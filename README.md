LilyBukkit-API
======

A modified Bukkit Minecraft Server API.

Website: [https://vladg24yt.github.io/LilyBukkit-API](https://vladg24yt.github.io/LilyBukkit-API)  
Bugs/Suggestions: [Issues](https://github.com/Vladg24YT/LilyBukkit-API/issues) and [Discord](https://discord.gg/qzKFJZW6bZ)  
Wiki: [https://github.com/Vladg24YT/LilyBukkit-API/wiki](https://github.com/Vladg24YT/LilyBukkit-API/wiki)

## Installation
Download the latest `jar` from [Releases](https://github.com/Vladg24YT/LilyBukkit-API/releases/latest), then follow one of the instructions below.  
**Maven**:  
Add the following code to your `pom.xml`:
```xml
<dependency>
    <groupId>ru.vladthemountain</groupId>
    <artifactId>lilybukkit</artifactId>
    <version>1.1.0</version>
    <scope>system</scope>
    <systemPath>${project.basedir}/relative/path/to/lilybukkit-1.1.1.jar</systemPath>
</dependency>
```
**Gradle**:  
Add the following code to your `build.gradle`:
```groovy
dependencies {
    implementation files('relative/path/to/lilybukkit-1.1.1.jar')
}
```
