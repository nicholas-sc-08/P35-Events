# 🎉 Event SpringBoot application

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=black) ![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white) ![SpringBoot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white) ![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)

This is a repository with contains a back-end project with those tecnologies: Java, Spring, SpringBoot and Maven but for Database is MySQL; This project is for events, where you can subscribe a user to a event, create a event and on this event you can have a indication too by subscribing somebody in a existing event;

## 🧮 Routes

| Method | Requisition Path | Description |
|--------|-------|-------------|
| **GET** | ```/events``` | This method with this route return all the events on the system |
| **GET** | ```/events/{prettyName}``` | Here it returns a specific event by receiving a prettyName of the vent |
| **POST** | ```/events``` | With this method and this path you can create a event on the system |
| **POST** | ```/subscription/{prettyName}/{userId}``` | This method is where you subscribe some user on the event but with a indication, passing the id of the indicator at the path and the pretty name of the event to know which event will be |
| **POST** | ```/subscription/{prettyName}``` | Here it's if you want to subscribe some user without indication, so only pass the pretty name of the event at the path |
| **GET** | ```/subscription/{prettyName}/ranking``` | This method returns a ranking of the users who have indicated more people to those events |
| **GET** | ```/subscription/{prettyName}/ranking/{userId}``` | This method returns a specific user who have indicated more people to the events, return it's position at the podium too |

## 📚 Configuring DataBase

So now we will configure the databse which you will be creating the tables, the url connections it's pressent at the file ```application.properties```, you will see something like: 

```java
spring.application.name=events
spring.datasource.username=root
spring.datasource.password=Mysql
spring.datasource.url= jdbc:mysql://localhost:3306/db_events
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.show-sql=true
```

Change the url of ```datasource.url``` on to your database configurations. 

After that, create the tables inside of your database:

```sql
create table tbl_user (
user_id	int	primary key,
user_name	varchar(255),
user_email	varchar(255)
);

create table tbl_subscription(
subscription_number int	primary key,
subscribed_user_id	int,
indication_user_id int,
event_id    int
);

create table tbl_event(
event_id	int	auto_increment primary key,
title	varchar(255),
pretty_name	varchar(50),
location	varchar(255),
price	double,
start_date	date,
end_date	date,
start_time	time,
end_time	time
);
```

# 💻 Running Project

Now to run your project go to the terminal and do this command at the project folder ```mvn spring-boot:run``` or ```npx mvn spring-boot:run```