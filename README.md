# cookie
cookie project:

technologies: spring-boot, gradle, micro-services, java, REST, hibernate, mongo-db, lombok

Starting point: https://spring.io/guides/gs/spring-boot/

Install mongo db:

- https://spring.io/guides/gs/accessing-data-mongodb/
- It is required to create a dir data/db (as root): http://stackoverflow.com/questions/7948789/mongodb-mongod-complains-that-there-is-no-data-db-folder
- Change the owner of "db" to your standard user: it is not recommended to be root
- To start the db, run "mongod"

Lombok: https://projectlombok.org/
- Nice handy too for beans, setters and getters
- To setup on eclipse, dont forget to do this: http://stackoverflow.com/questions/3418865/cannot-make-project-lombok-work-on-eclipse-helios


To run the application from the IDE (eclipse, intellij), find UserApplication.java and run as "Java Application". By default, it will run on port 8080 and will be access via: http://localhost:8080. To see if the app is running in a browser, go to http://localhost:8080/users/check
