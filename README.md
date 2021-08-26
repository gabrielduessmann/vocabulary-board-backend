# vocabulary-board-backend

> Backend from vocabulary board app.

## Installation

### Build
```console
$ mvn clean install
```

### Generate Q classes for querydsl
```console
$ mvn generate-sources
```````````

### Running

```console
$ mvn spring-boot:run
```

## First time Configutation

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

