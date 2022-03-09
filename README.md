# vocabulary-board-backend 
![GitHub Workflow Status](https://img.shields.io/github/workflow/status/gabrielduessmann/vocabulary-board-backend/maven-build)

# Backend from vocabulary board app.

## Requirements
* [vocabulary-rabbitmq](https://github.com/gabrielduessmann/vocabulary-rabbitmq) 

## Installation

### Build
```console
$ mvn clean install -DskipTests
```

### Generate Q classes for querydsl
```console
$ mvn generate-sources
```````````

### Running

```console
$ mvn spring-boot:run
```

### Running debug mode
* Go to Maven tab > Lifecycle > Choose "Clean" and then "Install"
* Configure a configuration Remote JVM Debug, it can be used the default port 5005
* Open the target folder in the terminal
* Type the following command: <br>
`java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 -jar .\vocabulary-0.0.1-SNAPSHOT.jar`
* Run debug

## First time Configuration

* Create a PotgreSQL database and configure your credentials in the `src/main/resources/application.properties` file. 
It is mandatory to create a database as well, for Hibernate to map and create all tables, I suggest `vocabulary-board` for its name.


* As the column interface is not implemented yet, it will be necessary to create it manually by the API.

API: <br>
POST `localhost:8080/column` 

I highly suggest following the next steps to create all the columns necessary to run the application. Execute the requests one by one.

Body: <br>
```console
{
    "title": "Pool",
    "sprintOrder": 0,
    "status": "POOL"
}
```

```console
{
    "title": "Backlog",
    "sprintOrder": 0,
    "status": "BACKLOG"
}
```

```console
{
    "title": "Week 1",
    "sprintOrder": 1,
    "status": "IN_PROGRESS"
}
```

```console
{
    "title": "Week 2",
    "sprintOrder": 2,
    "status": "PAUSED"
}
```

```console
{
    "title": "Week 3",
    "sprintOrder": 3,
    "status": "IN_PROGRESS"
}
```

```console
{
    "title": "Done",
    "sprintOrder": 0,
    "status": "DONE"
}
```

* You need to run `vocabulary-board-frontend` after build this project. Make sure to run this project in the `http://localhost:8080` url.

## RabbitMQ configuration
* https://www.youtube.com/watch?v=V9DWKbalbWQ
