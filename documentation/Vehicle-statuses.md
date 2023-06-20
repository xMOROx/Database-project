# Vehicles statuses

## Endpoints

```HTTP
GET /api/vehicles/statuses?page={page}&size={size}

Default: page=0, size=20
```

```HTTP
GET /api/vehicles/statuses/{id}
```

## Examples

```
curl -X GET "http://localhost:8080/api/vehicles/statuses?page=0&size=20" 
     -H "accept: application/json"
```

```JSON
{
    "_embedded": {
        "VehicleStatuses": [
            {
                "id": 1,
                "type": "AVI",
                "description": "Vehicle is available to rent"
            },
            {
                "id": 2,
                "type": "UNA",
                "description": "vehicle is unavailable to rent"
            }
        ]
    },
    "page": {
        "size": 20,
        "totalElements": 2,
        "totalPages": 1,
        "number": 0
    }
}
```

```
curl -X GET "http://localhost:8080/api/vehicles/statuses/1" 
     -H "accept: application/json"
```

```JSON
{
    "id": 1,
    "type": "AVI",
    "description": "Vehicle is available to rent",
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/v1/vehicles/statuses/1"
        },
        "statuses": {
            "href": "http://localhost:8080/api/v1/vehicles/statuses{?page,size}",
            "templated": true
        }
    }
}
```
