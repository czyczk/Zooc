# Zooc
An SSM back-end server program for Project Zooc.

Version 0.8 (2018-07-15)

# Minimum requirements
- Java JDK 8
- Gradle 4.8

# How to start the server program?
The Tomcat server is configured inside with the Gralde project. So it's as easy as to start a normal Gradle project.

```
$ gradle build
$ gradle tomcatRun
```

# Database: table definitions and sample data
The SQL files can be found in directory `sql`, where `zooc.sql` defines only the structure of the tables and `zooc_with_sample_data.sql` includes also the sample data for testing.
