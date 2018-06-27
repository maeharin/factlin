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

publish

```
// .env is secret
source .env
./gradlew build bintrayUpload --info
```