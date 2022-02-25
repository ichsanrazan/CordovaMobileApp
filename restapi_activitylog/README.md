# Activity Log and PIC - REST API

## API Documentation
Base URL for endpoints `https://localhost/restapi_activitylog/api`

## Endpoints
- `GET /activity/read.php` Retrieve all activity list
- `POST /activity/create.php` Create new activity
- `GET /pic/read.php` Retrieve all activity PIC list
- `POST /pic/create.php` Create new activity PIC

For sending POST request on Postman use raw JSON on the body and set Content-Type to application/json.

## Response
- For GET request the response will be an array enclosed within the `data` key.
- For successful POST request the response will be `Activity Created / Activity PIC Created`
- For unsuccessful POST request the response will be `{"message":"NOK"}`

## Models
- `Activity_List` Activity Item
- `Activity_Pic` Activity PIC

## Database Config
To configure database host, name, username or password settings go to `config/Database.php`
