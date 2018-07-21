# factlin

experimental

## how to use

this is gradle plugin

todo


## how to develop

build

```
./gradlew clean build
```

run postgres sample (using build result)

```
cd sample-postgres
docker-compose up
./gradlew factlinGen
```

```
 ./gradlew build && (cd ./sample-postgres/ && ./gradlew --stacktrace --refresh-dependencies factlinGen)
 ./gradlew build && (cd ./sample-mariadb/ && ./gradlew --stacktrace --refresh-dependencies factlinGen)
```

publish

```
// .env is secret
source .env
./gradlew build bintrayUpload --info
```
