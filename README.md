## Getting started
### Prerequisite
- Java >= 19 (preferrably [OpenJDK](https://openjdk.org/))

### Contribute
This project was made with the [Gradle Build Tool](https://gradle.org/), which you can build with the follow command:
```sh
./gradlew build
```
...or on Windows with
```batch
gradlew.bat build
```

When running `build`, Gradle will also runs `spotlessCheck`, which will run a check on the codebase for formatting. Therefore, you can also apply codebase format with:
```sh
./gradlew spotlessApply
```

Please follow [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/) specification while commit-ing
