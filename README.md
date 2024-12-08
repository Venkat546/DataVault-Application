# DataVault Application  


## Introduction  
The DataVault Application allows you to upload, manage, and retrieve files. It uses Spring Boot with JPA for database interactions and MySQL for storing metadata about the uploaded files. The application is dockerized and can be run as a container with the necessary dependencies for file upload and MySQL database setup.  


## Features  

-Upload File: Upload files with valid file types (JPEG, PNG, PDF).  
-View All Files: Retrieve a list of all uploaded files.  
-Delete File: Delete a specific file based on its ID.  
-Update Metadata: Update the metadata of a specific file (e.g., file name).  


## Tech Stack  

-Spring Boot: Backend framework.  
-JPA (Java Persistence API): For database interaction.  
-MySQL: Database for storing file metadata.  
-Docker: Containerization of the application and database.  
-Maven: Build automation tool.


## Prerequisites  

-Docker: Ensure Docker is installed to run the application in containers.  
-MySQL: MySQL service is required for metadata storage.  


## Setup Instructions  

### Step 1: Clone the repository  
```
git clone https://github.com/Venkat546/DataVault-Application.git
cd datavault-application
```



### Step 2: Configure MySQL  

In the application.properties file:  

```
#MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/file_upload_db
spring.datasource.username=user
spring.datasource.password=mysql@01
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
Make sure MySQL is running and accessible.
```



### Step 3: Set File Upload Directory  
The application allows file uploads. The directory for storing uploaded files can be configured as:  

```
file.upload-dir=/app/upload
```
This directory will be used by the application to store files uploaded through the API.    



### Step 4: Docker Setup  
To run the application with Docker, ensure the docker-compose.yml file is correctly configured. Here's an overview:  

```
version: '3.8'
services:
  app:
    build: .
    ports:
      - "8080:8080"
    depends_on:
      - mysql
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/file_upload_db
      SPRING_DATASOURCE_USERNAME: user
      SPRING_DATASOURCE_PASSWORD: mysql@01
      FILE_UPLOAD_DIR: /app/upload
    volumes:
      - ./Vault:/app/upload  # Mount the local upload folder

  mysql:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: mysql@01
      MYSQL_DATABASE: file_upload_db
      MYSQL_USER: user
      MYSQL_PASSWORD: mysql@01
    volumes:
      - mysql-data:/var/lib/mysql
    ports:
      - "3306:3306"

volumes:
  mysql-data:
```



### Step 5: Build and Run the Application  
To build and run the application:  

Build the application using Docker:  

```
docker-compose build
```
Start the containers:  
```
docker-compose up
```
The application will be accessible on http://localhost:8080.  



### Step 6: API Endpoints    

Upload File    
URL: ``/api/files/upload``
Method: `POST` 
Body: Multipart file (JPEG, PNG, or PDF)  
Response: The uploaded file's metadata, including file name, path, and upload timestamp.  


List All Files      
URL: ``/api/files``  
Method: `GET` 
Response: A list of all files, including their metadata.      


Delete File    
URL: ``/api/files/{id}``  
Method: `DELETE` 
Response: A success message if the file is deleted successfully, or an error message if not.  


Update File Metadata  
URL: ``/api/files/{id}``  
Method: `PUT`
Body: Updated metadata (e.g., file name)  
Response: The updated file's metadata.      


### Step 7: Testing the Application
To test the application, you can use Postman or any other API testing tool. Ensure that the application is running and the MySQL database is properly configured.  


### Step 8: Build and Run Locally (Without Docker)  
If you prefer to run the application locally without Docker:  

Build the application using Maven:  
```mvn clean package```
Run the application:  
```java -jar target/datavault-application.jar```
The application will start and be accessible at `http://localhost:8080`.  


## Troubleshooting  

-File Not Found: Ensure that the file exists in the specified path and that you are using a valid file type.
-Database Connection: Verify that MySQL is running and accessible with the correct credentials.
