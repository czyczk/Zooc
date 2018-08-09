# Zooc
An SSM back-end server program for Project Zooc.

Version 1.0 (2018-08-10)

# Minimum requirements
- Java JDK 8 and above
- Gradle 4.8 and above
- MySQL 5 and above

# Database: table definitions and sample data
The SQL files can be found in directory `sql`, where `zooc.sql` defines only the structure of the tables and `zooc_with_sample_data.sql` includes also the sample data for testing.

With the sample data, some built-in users are available for testing.


|System|Login Identity|Authentication|
|------|--------------|--------------|
|Client System|czyczk@qq.com|zzzz|
|Admin System|1|123|

# How to start the server program?
The Tomcat server is configured inside with the Gralde project. So it's as easy as to start a normal Gradle project.

```
$ gradle build
$ gradle tomcatRun
```


# MySQL Configuration
See `jdbc.properties` in `src/main/resources`.

# Redis Configuration
See `redis.properties` in `src/main/resources`.

