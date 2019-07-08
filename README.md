# PIK-predator

Predator is a website created for uni project. The main task was to create an online shop.

<h2>Starting app</h2>
In order to start the application, type this command: 

    mvn clean install
  
This will create a jar in a target directory, which you have to run using:

    java -jar target/predator-0.0.1-SNAPSHOT.jar
 
<h2>React hot reloading</h2> 
To enable hot reloading start the application using the command provided above, then
go to the frontend directory and type:

    npm start
  
<h2>Frontend test coverage</h2>
In order to run tests use this command:

    npm run test -- --coverage
  
You will be given some statistics in terminal, but you can view details opening index.html, that is created in
coverage directory.

<h2>Deployed app<h2>
    
    https://pik-predator.herokuapp.com/
