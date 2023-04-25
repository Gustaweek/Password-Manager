# Password Manager

Password management web application. Unregistered users have the option to register and use the password generator. After logging in, users can add, modify, and delete their passwords.

## Password Generator

The generator offers the selection of password length and options for including specific characters. To generate a password, users need to choose the desired characters by clicking on the multiple-choice fields located below the character count slider.

## Technologies

* Java
* Spring
* Hibernate
* Thymeleaf
* HTML
* CSS
* JavaScript
* JUnit5
* Mockito
* Maven
* Materialize
* jQuery

The Spring framework was used to build the service, providing the necessary tools to create this type of software quickly and smoothly. It offers solutions that facilitate application configuration.

Hibernate was used for object-relational mapping, translating data between the relational database and objects. Thymeleaf, a server-side template engine, was used to generate the user interface.

The frontend was built using HTML, CSS, JavaScript, jQuery, and Materialize CSS framework to create a modern and user-friendly interface.

Application tests were conducted using Mockito and JUnit5. Mockito provided a programming interface for creating mock objects used in unit tests created with JUnit5.

Maven was used during the application implementation, automating the software building process using Java. Maven allows, among other things, adding project dependencies, compiling and building the project, conducting unit and integration tests, and generating test reports and project information pages.

## Password Security

To secure user account passwords, we use hashing with the bcrypt algorithm. This prevents the reconstruction of the original character string, as it is a one-way operation that involves encrypting data using a hash function. The verification of the password entered by the user is done by comparing the hash of the currently entered password with the hash stored in the database.

Passwords stored in the user's account are secured using the AES (Advanced Encryption Standard) cipher. The encrypted password is then encoded with Base64 and placed in the database along with the other information provided by the user adding the credentials. When the user decides to display the password, the selected password is decoded and then decrypted to enable password use by the user.

Authentication and authorization of the user are done using the Spring Security framework, which is responsible for protecting the application from unauthorized use.
