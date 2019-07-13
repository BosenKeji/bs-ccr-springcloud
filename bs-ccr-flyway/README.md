[官方参考地址](https://flywaydb.org/getstarted/firststeps/maven#adding-a-second-migration, "我是标题")

####1、init
```bash
$ mvn flyway:migrate -Dflyway.url=... -Dflyway.user=... -Dflyway.password=...
```

####2、Creating the project
```bash
$ mvn archetype:generate -B \
    -DarchetypeGroupId=org.apache.maven.archetypes \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DarchetypeVersion=1.1 \
    -DgroupId=foo \
    -DartifactId=bar \
    -Dversion=1.0-SNAPSHOT \
    -Dpackage=foobar
```

####3、Creating migration
- directory : src/main/resources/db/migration
- file name : src/main/resources/db/migration/V1__Create_person_table.sql
```bash
    create table PERSON (
        ID int not null,
        NAME varchar(100) not null
    );
```
####4、Migrating the database
```bash
bar$ mvn flyway:migrate
```