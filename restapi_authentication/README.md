# Authentication - REST API

## API Documentation
- Base URL for endpoints (Localhost) `https://localhost/restapi_authentication`
- Base URL for endpoints (Live) `https://restapi-activitylog.000webhostapp.com/restapi_authentication`

## Endpoints
- `POST /login.php` Login
- `POST /register.php` Create new user

## Notes
- For sending POST request on Postman use raw JSON on the body
- Use username:admin and password:admin for log in

## Response
- For successful POST login request the response will be `Login successful`
- For unsuccessful POST  login request the response will be `Invalid username and password combination`
- For successful POST register request the response will be `User created`
- For unsuccessful POST  login request the response will be `User exists / Missing mandatory parameters`

## Database Config
To configure database host, name, username or password settings go to `/Connection.php`
