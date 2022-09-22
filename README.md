LilyBukkit-API
======

A modified Bukkit Minecraft Server API.

Website: [https://lilybukkit.github.io](https://lilybukkit.github.io)  
Bugs/Suggestions: [Issues](https://github.com/LilyBukkit/LilyBukkit-API/issues) and [Discord](https://discord.gg/qzKFJZW6bZ)  
Wiki: [Wiki](https://github.com/LilyBukkit/LilyBUkkit-API/wiki)

## Installation
Download the latest `jar` from [Releases](https://github.com/LilyBukkit/LilyBukkit-API/releases/latest), then follow one of the instructions below.  
**Maven**:  
Add the following code to your `pom.xml`:
```xml
<dependency>
    <groupId>ru.vladthemountain</groupId>
    <artifactId>lilybukkit-api</artifactId>
    <version>1.1.2</version>
    <scope>system</scope>
    <systemPath>${project.basedir}/relative/path/to/lilybukkit-1.1.2.jar</systemPath>
</dependency>
```
**Gradle**:  
Add the following code to your `build.gradle`:
```groovy
dependencies {
    implementation files('relative/path/to/lilybukkit-1.1.2.jar')
}
```
