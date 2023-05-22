# OOP Project - Video Store CMS
![Test](https://github.com/catouberos/video-store/actions/workflows/test.yml/badge.svg)
![Build](https://github.com/catouberos/video-store/actions/workflows/build.yml/badge.svg)

## Getting started
### Prerequisite
- Java >= 17

### Contribute
This project was made with the [Gradle Build Tool](https://gradle.org/), which you can build with the follow command:
```shell
./gradlew build
```
...or on Windows with:
```
gradlew.bat build
```

When running `build`, Gradle will also runs `spotlessCheck`, which will run a check on the codebase for formatting. Therefore, you can also apply codebase format with:
```shell
./gradlew spotlessApply
```

Then, the project is ready to be run with:
```shell
./gradlew run
```

### Details
There are some provided users/items in `data/`, (some of) their credentials are as below:

| username   | password | role    | notes                           |
|------------|----------|---------|---------------------------------|
| admin      | catou    | Admin   |                                 |
| minh dinh  | catou    | VIP     |                                 |
| dylanc     | catou    | Regular | this user has one borrowed item |
| abookworm  | catou    | Guest   |                                 |


### Notes
Please follow [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/) specification while commit.
