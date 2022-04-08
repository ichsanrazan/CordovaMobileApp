# Activity Log and PIC - REST API

## API Documentation
- Base URL for endpoints (Localhost) `https://localhost/restapi_activitylog/api`
- Base URL for endpoints (Live) `https://restapi-activitylog.000webhostapp.com/restapi_activitylog/api`

## Endpoints
- `GET /activity/read.php` Retrieve all activity list
- `POST /activity/create.php` Create new activity
- `GET /pic/read.php` Retrieve all activity PIC list
- `POST /pic/create.php` Create new activity PIC
- `GET /activity/search.php?name={keyword}` Search activity
- `GET /activity/filter.php?start_date={keyword}&end_date={keyword}&subject={keyword}&category={keyword}` Filter activity

## Notes
- For sending POST request on Postman use raw JSON on the body and set Content-Type to application/json.
- Important to note for filter you need to include the FULL address, even when you only want to filter only one of the filter options (just left the rest empty). Example: `/activity/filter.php?start_date=&end_date=&subject=Core CS, Core PS&category=`
- For `start_date` and `end_date` the keyword pattern is `yyyy-mm-dd`
- For `subject` the keyword can be a single subject `Core CS` or multiple subject (seperated by comma) `Core CS, Core PS, Datacomm`
- For `category` the keyword can be a single subject `HQ Project` or multiple subject (seperated by comma) `HQ Project, INC/IM Troubleshoot`


## Response
- For GET request the response will be an array enclosed within the `data` key.
- For successful POST request the response will be `Activity Created / Activity PIC Created`
- For unsuccessful POST request the response will be `{"message":"NOK"}`
- For unsucessfull search or filter the response will be an empty data

## Models
- `Activity_List` Activity Item
- `Activity_Pic` Activity PIC

## Database Config
To configure database host, name, username or password settings go to `config/Database.php`
