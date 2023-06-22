# Locations

## Json format - entity

```JSON
{
    "id": "integer",
    "Country": "string",
    "City": "string",
    "Address": "string",
    "Email": "string",
    "PhoneNumber": "string",
    "OpeningHours": "string",
    "ClosingHours": "string",
    "PostalCode": "string",
    "PhotoURL": "string"
}
```

## Validation - for post and put methods

```JSON
{
    "Country": {
        "required": true,
        "minLength": 1,
        "maxLength": 100
    },
    "City": {
        "required": true,
        "minLength": 1,
        "maxLength": 100
    },
    "Address": {
        "required": true,
        "minLength": 1,
        "maxLength": 100
    },
    "Email": {
        "required": true,
        "minLength": 1,
        "maxLength": 100
    },
    "PhoneNumber": {
        "required": true,
        "minLength": 9,
        "maxLength": 20
    },
    "OpeningHours": {
        "required": true,
        "minLength": 5,
        "maxLength": 5,
        "Pattern": "^([0-1][0-9]|[2][0-3]):([0-5][0-9])$"
    },
    "ClosingHours": {
        "required": true,
        "minLength": 5,
        "maxLength": 5,
        "Pattern": "^([0-1][0-9]|[2][0-3]):([0-5][0-9])$"
    },
    "PostalCode": {
        "required": true,
        "minLength": 6,
        "maxLength": 6,
        "Pattern": "^[0-9]{2}-[0-9]{3}$"
    },
    "PhotoURL": {
        "required": false
    }
}
```

## Endpoints

```HTTP
GET /api/v1/locations?page={page}&size={size}

Default: page=0, size=20
```

```HTTP
GET /api/v1/locations/{id}
```

```HTTP
POST /api/v1/locations
```

```HTTP
PUT /api/v1/locations/{id}
```

```HTTP
DELETE /api/v1/locations/{id}
```

```HTTP
GET /api/v1/locations/{id}/available-vehicles?page={page}&size={size}&startDate={startDate}&endDate={endDate}

Default: page=0, size=20, startDate=now, endDate=max of mssql database
```

```HTTP
GET /api/v1/locations/cities?page={page}&size={size}

Default: page=0, size=20
```

```HTTP
GET /api/v1/locations/cities/{city}?page={page}&size={size}

Default: page=0, size=20
```

## Examples

```
curl -X GET "http://localhost:8080/api/v1/locations?page=0&size=20" 
    -H "accept: application/json"
```

```JSON
{
    "_embedded": {
        "Locations": [
            {
                "id": 1,
                "country": "Poland",
                "city": "Kraków",
                "address": "ul. Kawiory 45/2",
                "email": "test@gmail.com",
                "phoneNumber": "+48 123456789",
                "openingHours": "8:00",
                "closingHours": "12:30",
                "postalCode": "30-023"
            },
            {
                "id": 2,
                "country": "Poland",
                "city": "Warszawa",
                "address": "ul. Nawojki 5/2",
                "email": "test2@gmail.com",
                "phoneNumber": "+48 323456789",
                "openingHours": "8:00",
                "closingHours": "12:30",
                "postalCode": "31-023"
            },
            {
                "id": 3,
                "country": "Poland",
                "city": "Warszawa",
                "address": "ul. Nawojki 5/2",
                "email": "test3@gmail.com",
                "phoneNumber": "+48 323416789",
                "openingHours": "8:00",
                "closingHours": "12:30",
                "postalCode": "31-023"
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/v1/locations?page=0&size=20"
        }
    },
    "page": {
        "size": 20,
        "totalElements": 3,
        "totalPages": 1,
        "number": 0
    }
}
```

```
curl -X GET "http://localhost:8080/api/v1/locations/1" 
    -H "accept: application/json"
```

```JSON
{
    "id": 1,
    "country": "Poland",
    "city": "Kraków",
    "address": "ul. Kawiory 45/2",
    "email": "test@gmail.com",
    "phoneNumber": "+48 123456789",
    "openingHours": "8:00",
    "closingHours": "12:30",
    "postalCode": "30-023"
}
```

```
curl -X POST "http://localhost:8080/api/v1/locations" 
    -H "Content-Type: application/json" 
    -d '
    {
        "Country": "Poland",
        "City": "Zielona Góra",
        "Address": "ul. Długa",
        "Email": "zielone@gmail.com",
        "PhoneNumber": "+48 125346456",
        "OpeningHours": "06:22",
        "ClosingHours": "20:52",
        "PostalCode": "44-513",
        "PhotoURL": "https://ancacars.pl/images/oddzialy/warszawa/warszawa-wynajem-srednioterminowy.jpg"
    }
    '
```

```JSON
    201 - Created
```

```
curl -X GET 
"http://localhost:8080/api/v1/locations/cities?page=0&size=20" 
    -H "accept: application/json"
```

```JSON
{
    "content": [
        "Warszawa",
        "Kraków",
        "Zielona Góra"
    ],
    "pageable": {
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "offset": 0,
        "pageSize": 20,
        "pageNumber": 0,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalElements": 3,
    "totalPages": 1,
    "size": 20,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "numberOfElements": 3,
    "first": true,
    "empty": false
}
```

```
curl -X GET "http://localhost:8080/api/v1/locations/cities/Warszawa?page=0&size=20" 
    -H "accept: application/json"
```

```JSON
{
    "_embedded": {
        "Locations": [
            {
                "id": 2,
                "country": "Poland",
                "city": "Warszawa",
                "address": "ul. Nawojki 5/2",
                "email": "test2@gmail.com",
                "phoneNumber": "+48 323456789",
                "openingHours": "8:00",
                "closingHours": "12:30",
                "postalCode": "31-023",
                "photoURL": "https://thumbs.rynekpierwotny.pl/3e79b87d:Fsc4GRv3H7CbQE68KlSTegaivQg/1500x0/filters:format(jpg)/kaviory-41_hgHXJLp.jpg"
            },
            {
                "id": 3,
                "country": "Poland",
                "city": "Warszawa",
                "address": "ul. Nawojki 5/2",
                "email": "test3@gmail.com",
                "phoneNumber": "+48 323416789",
                "openingHours": "8:00",
                "closingHours": "12:30",
                "postalCode": "31-023",
                "photoURL": "https://thumbs.rynekpierwotny.pl/3e79b87d:Fsc4GRv3H7CbQE68KlSTegaivQg/1500x0/filters:format(jpg)/kaviory-41_hgHXJLp.jpg"
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/v1/locations?page=0&size=20"
        }
    },
    "page": {
        "size": 20,
        "totalElements": 2,
        "totalPages": 1,
        "number": 0
    }
}
```