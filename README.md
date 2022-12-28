# spring-jwt-realization
In this project I realize full mechanism of user login and registration. Also Implement Role Based Action Control.

To implement it, I used `Spring Boot`, `Spring Data JPA` and `Postgres` to store data, `Spring Security 5` to implement security issues, `JWT` to communicate between `Server` and `Client`. 

# Running
To run the application enter in the command line: `mvn spring-boot:run`

After project running, schema in the Database created. First of all, you need some `Roles`:

```
INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
```

# Signup
`POST localhost:8080/api/auth/signup`
```
{
    "username": "admin",
    "email": "admin@gmail.com",
    "password": "12345678",
    "role": ["admin, moderator"]
}
```

# Get JWT Token
`POST localhost:8080/api/auth/signin`
```
{
    "username": "admin",
    "password": "1234"
}
```


   # message add
        `POST localhost:8080/message/2`
        ```
       {
            "text":  "Text5"
        }
        ```
        
        # comments add
        `POST localhost:8080/comments/4/polzovatel/1`
        ```
       {
            "text": "Memsas2323d4"
        }
        ``` 
        
          # user
        `GET localhost:8080/api/user/1`
        
        
          #  add friends
        `POST localhost:8080/you/2/add/4`
        
         # all message
        `POST localhost:8080/messageAll`
      
      
   
        
        



# Make Request to Secure API
`localhost:8080/api/test/admin`

```Bearer {jwt}```

# Make Request to Public API
`localhost:8080/api/test/all`
