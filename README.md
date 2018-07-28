# factlin

factlin is code generator (gradle plugin) for db connection test of Kotlin project.

It Generate test fixture class and db insert helper methods (currentlly supported only dbsetup) from your existing database schema.

generated code is like this

```kotlin
data class UsersFixture (
    val id: Int = 0, // primary key
    val name: String = "", // name
    val job: String = "", // job name
    val status: String = "", // activate status
    val age: Int = 30, // age
    val nick_name: String? = null // nick name
)

fun DbSetupBuilder.insertUsersFixture(f: UsersFixture) {
    insertInto("users") {
        mappedValues(
                "id" to f.id,
                "name" to f.name,
                "job" to f.job,
                "status" to f.status,
                "age" to f.age,
                "nick_name" to f.nick_name
        )
    }
}
```

usage of generated code

```kotlin
dbSetup(dest) {
    deleteAllFrom(listOf("users")) 
    
    // using generated codes. this codes insert datas to your database
    insertUsersFixture(UsersFixture(name = "foo"))
    insertUsersFixture(UsersFixture(name = "bar"))
}.launch()
```

## benefit

- type and null safe db test fixture generation (no more excel and insert into...)
- help to generate similar test fixtures from another fixture (generated fixture class is Kotlin data class)

## how to use

create tables in your db

```sql
CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  name VARCHAR(256) NOT NULL,
  job VARCHAR(256) NOT NULL DEFAULT 'engineer',
  status VARCHAR(256) NOT NULL DEFAULT 'ACTIVE',
  age INTEGER NOT NULL DEFAULT 30,
  nick_name VARCHAR(256)
);

COMMENT ON TABLE users IS 'user table';
COMMENT ON COLUMN users.id IS 'primary key';
COMMENT ON COLUMN users.name IS 'name';
COMMENT ON COLUMN users.job IS 'job name';
COMMENT ON COLUMN users.status IS 'activate status';
COMMENT ON COLUMN users.age IS 'age';
COMMENT ON COLUMN users.nick_name IS 'nick name';
```

build.gradle

```gradle
buildscript {
    ext.kotlin_version = '1.2.31'

    repositories {
        mavenCentral()

        // add
        maven {
            url "https://dl.bintray.com/maeharin/factlin"
        }
    }
    dependencies {
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"

        // add
        classpath 'com.maeharin:factlin:0.0.1'
        classpath "org.postgresql:postgresql:42.1.4"
    }
}

group 'com.maeharin'
version '1.0-SNAPSHOT'

apply plugin: 'kotlin'

// add
apply plugin: 'factlin'
factlinGen {
    dbUrl = "jdbc:postgresql://{DB_HOST}/{DB_NAME}"
    dbUser = "{DB_USER}"
    dbPassword = "{DB_PASS}"
    dbDialect = "postgres"
}

repositories {
    mavenCentral()
}

dependencies {
    compile "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version"

    // add
    compile "org.postgresql:postgresql:42.1.4"
    testCompile 'com.ninja-squad:DbSetup-kotlin:2.1.0'
    testCompile('org.junit.jupiter:junit-jupiter-api:5.2.0')
    testRuntime('org.junit.jupiter:junit-jupiter-engine:5.2.0')
}

// add
test {
    useJUnitPlatform()
}

compileKotlin {
    kotlinOptions.jvmTarget = "1.8"
}
compileTestKotlin {
    kotlinOptions.jvmTarget = "1.8"
}
```

generate codes

```
./gradlew factlinGen
```

use generated codes for your db connection test

```kotlin
class SampleTest {
    val dest = DriverManagerDestination("jdbc:postgresql://{DB_HOST}/{DB_NAME}", "DB_USER", "DB_PASS")

    init {
        Class.forName("org.postgresql.Driver")
    }

    @Test
    fun testInsertUser() {
        dbSetup(dest) {
            deleteAllFrom(listOf("users"))
            insertUsersFixture(UsersFixture())
        }.launch()
        
        val stmt = dest.connection.createStatement()
        val rs = stmt.executeQuery("select * from users")
        
        assertTrue(rs.next())
        assertEquals("", rs.getString("name"))
    }
}
```

run test

```
./gradlew test
```

done!

## supported databases

- PostgreSQL
- MariaDB

## how to develop

unit test

```
./gradlew test
```

integration test

```
docker-compose up

./gradlew build && (cd ./sample-postgres/ && ./gradlew --stacktrace factlinGen && ./gradlew test)
./gradlew build && (cd ./sample-mariadb/ && ./gradlew --stacktrace factlinGen && ./gradlew test)
```

## publish

```
// .env is secret
source .env
./gradlew build bintrayUpload --info
```
