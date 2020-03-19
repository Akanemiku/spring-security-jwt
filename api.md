# spring-security-jwt API

| URL                                   | Restful | param                                                        | type      | tip   |
| ------------------------------------- | ------- | ------------------------------------------------------------ | --------- | ----- |
| localhost:9000/auth/login             | post    | {"username": "Akane", "password": "123456","rememberMe":true} | json      |       |
| localhost:9000/auth/register          | post    | {"username":"test","password":"123456"}                      | json      |       |
| localhost:9000/api/users              | get     |                                                              |           |       |
| localhost:9000/api/user?username=test | delete  | username=                                                    | ?username | admin |

