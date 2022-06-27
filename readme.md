# Posterr application
Author: Adriano de Mello Rodrigues

Posterr is a social media application very similar to twitter, but with far fewer features.
This is a backend solution built in Java 17 to allow the following features:

* Create posts - Three post types are allowed, regular posts, quote posts and reposts.
* Search posts with pagination - Default search will return 10 posts, 
but page size can be adjusted. This search can be filtered by begin date, end date and 
only user posts
* User profile - Returns user data with posts and daily posts count.

## Premises

Some assumptions have been made in order to develop this application. Here is the list:

* As authentication should not be implemented, it is expected to an API Gateway be in
the middle of frontend and backend applications, authenticating requests, and adding
user info in the header.
* As quote posts can quote a repost post, and reposts posts can repost a quote post,
this could end up in an infinity loop. As a quote post can add a comment, the size
of the post content could be too big. To limit this, a quote post will only quote
child post content. And comment size can be same size as a regular post content size.

## Technical solutions

This section will explain technical solutions developed into this project.

### Quote Posts and Reposts

Data of quoted and reposted posts will be duplicated into the quote post and the repost 
post. This may increase database size and post creation duration. But will also speed
up posts search, once it does not have to make a join to fetch child post data.

### User posts and daily posts count

Posts and daily posts count was stored into users table, during post creation.
The reason is the same as in quote and repost posts section. It will speed up
the search for user profile data, once it won't have to query all posts records
to summarize posts and daily posts count.

### Software Architecture

Software architecture follows a Clean Architecture approach. Packages structure 
is organized in the following way:

* Adapter - Connects use cases with external components. And are divided into two types:
  * Application - Input port, were applications receive requests (rest, grpc, queue consumers)
  * Infra - Output port, were application send requests (db queries, rest client, grpc client,
  queue producer)
* Application - Were applications receive requests (rest, grpc, queue consumers)
* Domain - Enterprise business rules
* Enums - enumerations shared between layers
* Exceptions - Application exceptions
* Infra - were application send requests (db queries, rest client, grpc client, queue producer)
* Mapper - Mappers to transform data from a dto to a domain object, or from a domain object to
a dto.
* Usecase - Application business rules

#### Clean code

This application was developed with efforts to follow clean code practices. A few practices
followed are described bellow:

* SOLID Principles - All classes try to follow SOLID principles
* Cyclomatic complexity - It was used CodeMetrics intellij plugin to measure complexity 
during development. The goal was to keep complexity as low as possible.
* SonarLint - It was used sonar lint plugin to track and solve lint problems during development
* Clean code - all classes and methods try to follow clean code principles

#### Tests

This application was developed with integration, unit tests and e2e tests. For integration
and e2e tests, a postgres database is initialized during tests initialization. The goal is to
detect migration problems and simulate an environment as close to production as possible
during tests phase. Project lines coverage is approximately 92% (Measured with intellij).

## Critique

This sections will bring insides on what could be done to improve the project.

### Quota

Daily quota could be parametrized as a env var

### Tests

More unit and integration tests could be added, although the coverage is high. Testing layers
is important to find problems faster during tests.

### Scaling

Scaling a project can be difficult sometimes. If this project grows and have many users, posts
and more features are added, a few problems could occur, such as: 
* database problems (slow queries, database unavailability due to high usage, etc)
* memory, cpu and thread problems - application could start to use too much memory, cpu or threads

To solve these problems, it is essential to be able to monitor the application performance.
So first thing is to identify were the problem is. A few tools can be used to that, such as:
Grafana, Prometeus, New Relic, etc. With that in mind, a few approaches could
be done to help scale the application. They are listed bellow:

#### Early phase (Monolith)

This application was developed as a monolith application. Until it is to complex to maintain,
and to scale the monolith application, best option is to keep as it is. To better scalability
of this monolith application, actions can be made according to the performance problem.

* Application performance problems - can be solved by upgrading machine memory, cpu, 
threads available. This application can also be deployed into a cluster to solve scalability
problems
* Database performance - can be solved by analysing and improving queries performance, and also
preventing and removing duplicated queries. Also, can be solved with other database solutions 
and cache solutions.
* Both - cache solutions can help with both, application and database performances. Also,
database performance can be generating application performances, and when fixed, application
performance problems can be solved also.

#### Advanced phase (Microservices)

When it is too hard to scale the monolith application, it is time to change to a microservices
architecture with database segregation. This approach will help to scale only the needed 
services. Example: A user will probably use more post features than user profile features.
So, services related to posts will have a higher demand, and can be escalated alone, without
impacting user services in a microservices architecture. Also, database segregation
would ease database usage, once a request to user profile data won't need to fetch
data from posts database.

A CQRS approach could also be used with event sourcing and eventual consistency, 
to prevent that micorservices lose data and have performance issues, once events 
can be processed asynchronously, according to each microservice capability. And, if
an event can't be processed because a service failure, it can be stored into a DLQ
to be investigated by engineering teams.

## Run application

To test application with postman, there is a collection in project root directory with
a few request examples.

### Locally

To run the application locally the only requirement is to have docker and docker-compose 
installed. Then run make start in the project root directory, or access Makefile,
copy the command and run it on terminal, into project root directory.

### Dev environment

To develop using IDE, it is required that java 17, maven, docker and docker-compose are
installed. It is also required that IDE is installed and configured. Then, it is possible
to run make start-dev-env in the project root directory. And run the project using the IDE.

## Swagger

Swagger is available under: http://localhost:8080/swagger-ui/#/