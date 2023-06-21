# Documentation

## Table of Contents

- [Documentation](#documentation)
  - [Table of Contents](#table-of-contents)
  - [Authors](#authors)
  - [Technologies](#technologies)
  - [Link to remote repository](#link-to-remote-repository)
  - [Users](#users)
    - [Endpoints](#endpoints)
  - [Auth](#auth)
    - [Login](#login)
    - [Register](#register)
    - [Endpoints](#endpoints-1)
  - [Vehicles](#vehicles)
    - [Json format - entity](#json-format---entity)
    - [Validation - for post and put methods](#validation---for-post-and-put-methods)
    - [Endpoints](#endpoints-2)
    - [Examples](#examples)
  - [Vehicles statuses](#vehicles-statuses)
    - [Endpoints](#endpoints-3)
    - [Examples](#examples-1)
  - [Locations](#locations)
    - [Json format - entity](#json-format---entity-1)
    - [Validation - for post and put methods](#validation---for-post-and-put-methods-1)
    - [Endpoints](#endpoints-4)
    - [Examples](#examples-2)
  - [Bookings](#bookings)
    - [Reserve vehicle - JSON format](#reserve-vehicle---json-format)
    - [Endpoints](#endpoints-5)
    - [Example](#example)
  - [Search](#search)
    - [Request body](#request-body)
    - [Operator - enum](#operator---enum)
    - [FieldType - enum](#fieldtype---enum)
    - [SortDirection - enum](#sortdirection---enum)
    - [Filters - validation](#filters---validation)
    - [Sorts - validation](#sorts---validation)
    - [Page - validation](#page---validation)
    - [Size - validation](#size---validation)
    - [StartDate - validation](#startdate---validation)
    - [EndDate - validation](#enddate---validation)
    - [Endpoints](#endpoints-6)
    - [Examples](#examples-3)

## Authors

- Zajdel Patryk
- Puz Dominik
- Bielaszka Daniel

## Technologies

- Backend: Java, Spring, Spring Boot, Spring MVC, Spring Security, Spring Data
- Frontend: Angular, Material design, scss
- Database: MS SQL

## Link to remote repository

[Github](https://github.com/xMOROx/Database-project)

## Users

### Endpoints

```HTTP
GET /api/v1/users
```

```HTTP
GET /api/v1/users/{id}
```

```HTTP
GET /api/v1/users/{id}/bookings
```

```HTTP
GET /api/v1/users/{id}/bookings/active
```

```HTTP
GET /api/v1/users/{id}/bookings/reserved
```

```HTTP
GET /api/v1/users/{id}/bookings/rented
```

```HTTP
GET /api/v1/users/{id}/roles
```

```HTTP
GET /api/v1/users/{id}/available-roles
```

```HTTP
PUT /api/v1/users/{id}
```

```HTTP
DELETE /api/v1/users/{id}
```

## Auth

### Login

```JSON
{
  "Email": "string",
  "Password": "string"
}
```

Return JWT access token

### Register

```JSON
{
    "Pesel": "string",
    "Email": "string",
    "FirstName": "string",
    "PhoneNumber": "string",
    "SurName": "string",
    "Password": "string"
}
```

### Endpoints

- [Login](#login)

```HTTP
POST /api/v1/auth/login
```

- [Register](#register)

```HTTP
POST /api/v1/auth/register
```

## Vehicles

### Json format - entity

```JSON
{
    "id": "integer",
    "Registration": "string",
    "Brand": "string",
    "Model": "string",
    "PhotoURL": "string",
    "BodyType": "string",
    "FuelType": "string",
    "Power": "integer",
    "Gearbox": "string",
    "SeatsNumber": "integer",
    "DoorsNumber": "integer",
    "Color": "string",
    "Metallic": "boolean",
    "Description": "string",
    "DailyFee": "double",
    "BestOffer": "boolean",
    "FrontWheelDrive": "boolean",
    "ProductionYear": "integer",
}
```

### Validation - for post and put methods

```JSON
"Registration":
{
    "Required": true,
    "MinLength": 6,
    "MaxLength": 10,
    "Pattern": "^[A-Z][A-Z0-9]+$"
},
"Brand":
{
    "Required": true,
    "MinLength": 1,
    "MaxLength": 255,
},
"Model":
{
    "Required": true,
    "MinLength": 1,
    "MaxLength": 255,
},
"PhotoURL":
{
    "Required": false,
},
"BodyType":
{
    "Required": true,
    "MinLength": 1,
    "MaxLength": 100,
},
"FuelType":
{
    "Required": true,
    "MinLength": 1,
    "MaxLength": 30,
},
"Power":
{
    "Required": true
},
"Gearbox":
{
    "Required": true,
    "MinLength": 1,
    "MaxLength": 50,
},
"SeatsNumber":
{
    "Required": false, 
    "Min": 2,
    "Max": 9, 
    "Default": 5
},
"DoorsNumber":
{
    "Required": true, 
    "Min": 3,
    "Max": 5
},
"Color":
{
    "Required": true,
    "MinLength": 1,
    "MaxLength": 50,
},
"Metallic":
{
    "Required": false,
    "Default": false
},
"Description":
{
    "Required": false
},
"DailyFee":
{
    "Required": true,
},
"BestOffer":
{
    "Required": false,
    "Default": false
}, 
"FrontWheelDrive":
{
    "Required": false,
    "Default": false
},
"ProductionYear":
{
    "Required": true,
    "Min": 1900
},
"LocationId":
{
    "Required": true
},
"StatusId":
{
    "Required": true
},
```

### Endpoints

```HTTP
GET /api/v1/vehicles?page={page}&size={size}

Default: page=0, size=20
```

```HTTP
GET /api/v1/best-offers?page={page}&size={size}

Default: page=0, size=20
```

```HTTP
GET /api/v1/vehicles/{id}
```

```HTTP
POST /api/v1/vehicles
```

```HTTP
PUT /api/v1/vehicles/{id}
```

```HTTP
DELETE /api/v1/vehicles/{id}
```

```HTTP
GET /api/v1/vehicles/{id}/location
```

```HTTP
POST /api/v1/vehicles/{id}/location

Example:
RequestBody: 
{
    1
}
```

```HTTP
```

```HTTP
POST /api/v1/vehicles/{id}/equipment

Example:
RequestBody: 
{
    1
}
```

```HTTP
DELETE /api/v1/vehicles/{id}/equipment

Example:
RequestBody: 
{
    1
}
```

```HTTP
POST /api/v1/vehicles/{id}/status

Example:
RequestBody: 
{
    1
}
```

### Examples

```
curl -X GET "http://localhost:8080/api/v1/vehicles"
    -H "accept: application/json"
```

```JSON
{
    "_embedded": {
        "Vehicles": [
            {
                "id": 1,
                "registration": "KR45043",
                "brand": "Toyota",
                "model": "Yaris",
                "dailyFee": 20.34,
                "bestOffer": true,
                "bodyType": "monocoque",
                "productionYear": 1900,
                "fuelType": "benzyna",
                "power": 87,
                "gearbox": "manual",
                "frontWheelDrive": true,
                "doorsNumber": 3,
                "seatsNumber": 5,
                "color": "silver",
                "metalic": true,
                "description": "toyota yaris to najlepsze auto świata!",
            },
            {
                "id": 2,
                "registration": "W45043",
                "brand": "Toyota",
                "model": "Yaris",
                "dailyFee": 20.34,
                "bestOffer": false,
                "bodyType": "wielokok",
                "productionYear": 1900,
                "fuelType": "benzyna",
                "power": 87,
                "gearbox": "manual",
                "frontWheelDrive": true,
                "doorsNumber": 3,
                "seatsNumber": 5,
                "color": "silver",
                "metalic": true,
                "description": "toyota yaris to najlepsze auto świata!",
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/v1/vehicles?page=0&size=20"
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

```
curl -X GET "http://localhost:8080/api/v1/vehicles/1" 
    -H "accept: application/json"
```

```JSON
{
    "id": 1,
    "registration": "KR45043",
    "brand": "Toyota",
    "model": "Yaris",
    "dailyFee": 20.34,
    "bestOffer": true,
    "bodyType": "monocoque",
    "productionYear": 1900,
    "fuelType": "benzyna",
    "power": 87,
    "gearbox": "manual",
    "frontWheelDrive": true,
    "doorsNumber": 3,
    "seatsNumber": 5,
    "color": "silver",
    "metalic": true,
    "description": "toyota yaris to najlepsze auto świata!",

    "_links": {
        "self": {
            "href": "http://localhost:8080/api/v1/vehicles/1"
        },
        "all": {
            "href": "http://localhost:8080/api/v1/vehicles{?page,size}",
            "templated": true
        }
    }
}
```

```
curl -X GET "http://localhost:8080/api/v1/vehicles/{id}/location"
    -H "accept: application/json"
```

```JSON
    1
```

```
curl -X POST "http://localhost:8080/api/v1/vehicles"
   -H "Content-Type: application/json"
   -d 
'{
    "Registration": "KR45051",
    "Brand": "Maserati",
    "Model": "Levante",
    "DailyFee": 120.34,
    "BestOffer": true,
    "BodyType": "monocoque",
    "ProductionYear": 2021,
    "FuelType": "benzyna",
    "Power": 387,
    "Gearbox": "automat",
    "FrontWheelDrive": false,
    "DoorsNumber": 3,
    "SeatsNumber": 5,
    "Color": "silver",
    "Metalic": true,
    "PhotoURL": "https://mycompanypolska.pl/image/w960h560/dMDNtm1c9vuGXvZ1O1J8.jpg",
    "Description": "brak",
    "VehicleLocationId": 2,
    "VehicleStatusId": 1
}'
```

```JSON
    201 - Created
```

## Vehicles statuses

### Endpoints

```HTTP
GET /api/vehicles/statuses?page={page}&size={size}

Default: page=0, size=20
```

```HTTP
GET /api/vehicles/statuses/{id}
```

### Examples

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

## Locations

### Json format - entity

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

### Validation - for post and put methods

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

### Endpoints

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

### Examples

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

## Bookings

### Reserve vehicle - JSON format

```JSON
{
    "UserID": "integer",
    "VehicleID": "integer",
    "LocationID": "integer",
    "ReceiptDate": "string",
    "ReturnDate": "string",
    "TotalCost": "integer"
}
```

### Endpoints

```HTTP
POST /api/v1/bookings/reserve
```

```HTTP
GET /api/v1/bookings?page={page}&size={size}

Default: page=0, size=20
```

```HTTP
GET /api/v1/bookings/{id}
```

```HTTP
GET /api/v1/bookings/returned?page={page}&size={size}

Default: page=0, size=20
```

```HTTP
GET /api/v1/bookings/reserved?page={page}&size={size}

Default: page=0, size=20
```

```HTTP
GET /api/v1/bookings/rented?page={page}&size={size}

Default: page=0, size=20
```

```HTTP
GET /api/v1/bookings/canceled?page={page}&size={size}

Default: page=0, size=20
```

```HTTP
GET /api/v1/bookings/active?page={page}&size={size}

Default: page=0, size=20
```

```HTTP
POST /api/v1/bookings/cancel 

Example: 
{
    1
}
```

```HTTP
POST /api/v1/bookings/return
```

```HTTP
POST /api/v1/bookings/rent
```

```HTTP
POST /api/v1/bookings/reserve
```

### Example

```
curl -X GET "http://localhost:8080/api/v1/bookings"
     -H "accept: application/json"
```

```JSON
{
    "_embedded": {
        "Bookings": [
            {
                "id": 1,
                "user": {
                    "id": 1,
                    "firstName": "Patryk",
                    "surName": "Zajdel",
                    "email": "zajdel0202@gmail.com",
                    "phoneNumber": "+48661689029",
                    "pesel": "01220202391"
                },
                "vehicle": {
                    "id": 1,
                    "registration": "KR45043",
                    "brand": "Toyota",
                    "model": "Yaris",
                    "dailyFee": 20.34,
                    "bestOffer": true,
                    "bodyType": "monocoque",
                    "productionYear": 1900,
                    "fuelType": "benzyna",
                    "power": 87,
                    "gearbox": "manual",
                    "frontWheelDrive": true,
                    "doorsNumber": 3,
                    "seatsNumber": 5,
                    "color": "silver",
                    "metalic": true,
                    "description": "toyota yaris to najlepsze auto świata!",
                    "photoURL": "https://kong-proxy-aws.toyota-europe.com/c1-images/resize/ccis/680x680/zip/pl/product-token/ba4ecbf7-1049-49c6-b93d-65b75c27bf98/vehicle/867d8a62-bdb6-4e74-add7-7493d9536b5c/padding/50,50,50,50/image-quality/70/day-exterior-4_040.png"
                },
                "pickupLocation": {
                    "id": 1,
                    "country": "Poland",
                    "city": "Kraków",
                    "address": "ul. Kawiory 45/2",
                    "email": "test@gmail.com",
                    "phoneNumber": "+48 123456789",
                    "openingHours": "8:00",
                    "closingHours": "12:30",
                    "postalCode": "30-023",
                    "photoURL": "https://thumbs.rynekpierwotny.pl/3e79b87d:Fsc4GRv3H7CbQE68KlSTegaivQg/1500x0/filters:format(jpg)/kaviory-41_hgHXJLp.jpg"
                },
                "bookingStateCode": {
                    "id": 1,
                    "bookingCode": "RES",
                    "description": "vehicles is reserved"
                },
                "receiptDate": "2023-06-16T12:13:12",
                "returnDate": "2023-06-22T10:00:00",
                "totalCost": 123.45
            },
            {
                "id": 2,
                "user": {
                    "id": 1,
                    "firstName": "Patryk",
                    "surName": "Zajdel",
                    "email": "zajdel0202@gmail.com",
                    "phoneNumber": "+48661689029",
                    "pesel": "01220202391"
                },
                "vehicle": {
                    "id": 2,
                    "registration": "W45043",
                    "brand": "Toyota",
                    "model": "Yaris",
                    "dailyFee": 20.34,
                    "bestOffer": false,
                    "bodyType": "wielokok",
                    "productionYear": 1900,
                    "fuelType": "benzyna",
                    "power": 87,
                    "gearbox": "manual",
                    "frontWheelDrive": true,
                    "doorsNumber": 3,
                    "seatsNumber": 5,
                    "color": "silver",
                    "metalic": true,
                    "description": "toyota yaris to najlepsze auto świata!",
                    "photoURL": "https://kong-proxy-aws.toyota-europe.com/c1-images/resize/ccis/680x680/zip/pl/product-token/ba4ecbf7-1049-49c6-b93d-65b75c27bf98/vehicle/867d8a62-bdb6-4e74-add7-7493d9536b5c/padding/50,50,50,50/image-quality/70/day-exterior-4_040.png"
                },
                "pickupLocation": {
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
                "bookingStateCode": {
                    "id": 3,
                    "bookingCode": "REN",
                    "description": "vehicle is rented"
                },
                "receiptDate": "2023-08-16T23:59:59",
                "returnDate": "2023-08-24T23:59:59",
                "totalCost": 162.72
            },
            {
                "id": 3,
                "user": {
                    "id": 1,
                    "firstName": "Patryk",
                    "surName": "Zajdel",
                    "email": "zajdel0202@gmail.com",
                    "phoneNumber": "+48661689029",
                    "pesel": "01220202391"
                },
                "vehicle": {
                    "id": 2,
                    "registration": "W45043",
                    "brand": "Toyota",
                    "model": "Yaris",
                    "dailyFee": 20.34,
                    "bestOffer": false,
                    "bodyType": "wielokok",
                    "productionYear": 1900,
                    "fuelType": "benzyna",
                    "power": 87,
                    "gearbox": "manual",
                    "frontWheelDrive": true,
                    "doorsNumber": 3,
                    "seatsNumber": 5,
                    "color": "silver",
                    "metalic": true,
                    "description": "toyota yaris to najlepsze auto świata!",
                    "photoURL": "https://kong-proxy-aws.toyota-europe.com/c1-images/resize/ccis/680x680/zip/pl/product-token/ba4ecbf7-1049-49c6-b93d-65b75c27bf98/vehicle/867d8a62-bdb6-4e74-add7-7493d9536b5c/padding/50,50,50,50/image-quality/70/day-exterior-4_040.png"
                },
                "pickupLocation": {
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
                "bookingStateCode": {
                    "id": 3,
                    "bookingCode": "REN",
                    "description": "vehicle is rented"
                },
                "receiptDate": "2023-06-15T23:59:59",
                "returnDate": "2023-06-18T23:59:59",
                "totalCost": 162.72
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/v1/bookings?page=0&size=20"
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
curl -X GET "http://localhost:8080/api/v1/bookings/active"
     -H "accept: application/json"
```

```JSON
{
    "_embedded": {
        "Bookings": [
            {
                "id": 2,
                "user": {
                    "id": 1,
                    "firstName": "Patryk",
                    "surName": "Zajdel",
                    "email": "zajdel0202@gmail.com",
                    "phoneNumber": "+48661689029",
                    "pesel": "01220202391"
                },
                "vehicle": {
                    "id": 2,
                    "registration": "W45043",
                    "brand": "Toyota",
                    "model": "Yaris",
                    "dailyFee": 20.34,
                    "bestOffer": false,
                    "bodyType": "wielokok",
                    "productionYear": 1900,
                    "fuelType": "benzyna",
                    "power": 87,
                    "gearbox": "manual",
                    "frontWheelDrive": true,
                    "doorsNumber": 3,
                    "seatsNumber": 5,
                    "color": "silver",
                    "metalic": true,
                    "description": "toyota yaris to najlepsze auto świata!",
                    "photoURL": "https://kong-proxy-aws.toyota-europe.com/c1-images/resize/ccis/680x680/zip/pl/product-token/ba4ecbf7-1049-49c6-b93d-65b75c27bf98/vehicle/867d8a62-bdb6-4e74-add7-7493d9536b5c/padding/50,50,50,50/image-quality/70/day-exterior-4_040.png"
                },
                "pickupLocation": {
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
                "bookingStateCode": {
                    "id": 3,
                    "bookingCode": "REN",
                    "description": "vehicle is rented"
                },
                "receiptDate": "2023-08-16T23:59:59",
                "returnDate": "2023-08-24T23:59:59",
                "totalCost": 162.72
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/v1/bookings?page=0&size=20"
        }
    },
    "page": {
        "size": 20,
        "totalElements": 1,
        "totalPages": 1,
        "number": 0
    }
}
```

## Search

### Request body

```JSON
{
    "Filters": [
      {
        "Key": "string",
        "Operator": "string",
        "FieldType": "string",
        "Value": "valid object",
        "ValueTo": "valid object",
        "Values": "Array of valid objects"
      }
    ],
    "Sorts": [
      {
        "Key": "string",
        "Direction": "string"
      }
    ],
    "Page": 0,
    "Size": 0,
    "StartDate": "string",
    "EndDate": "string"
}
```

### Operator - enum

```JS
enum Operator {
    EQUALS = 'EQUALS',
    NOT_EQUALS = 'NOT_EQUALS',
    GREATER_THAN = 'GREATER_THAN',
    GREATER_THAN_OR_EQUALS = 'GREATER_THAN_OR_EQUALS',
    LESS_THAN = 'LESS_THAN',
    LESS_THAN_OR_EQUALS = 'LESS_THAN_OR_EQUALS',
    LIKE = 'LIKE',
    NOT_LIKE = 'NOT_LIKE',
    IN = 'IN',
    NOT_IN = 'NOT_IN',
    BETWEEN = 'BETWEEN',
}
```

### FieldType - enum

```JS
enum FieldType {
    BOOLEAN = 'BOOLEAN',
    INTEGER = 'INTEGER',
    CHAR = 'CHAR',
    STRING = 'STRING',
    DOUBLE = 'DOUBLE',
    LONG = 'LONG',
    DATE = 'DATE',
    BIG_DECIMAL = 'BIGDECIMAL',
}
```

### SortDirection - enum

```JS
enum SortDirection {
    ASC = 'ASC',
    DESC = 'DESC'
}
```

### Filters - validation

```JSON
{
    "Filters": [
    {
      "Key": {
        "required": true,
      }
      "Operator": {
        "required": true,
        "enum": [
            "EQUALS",
            "NOT_EQUALS",
            "GREATER_THAN",
            "GREATER_THAN_OR_EQUALS",
            "LESS_THAN",
            "LESS_THAN_OR_EQUALS",
            "LIKE",
            "NOT_LIKE",
            "IN",
            "NOT_IN",
            "BETWEEN"
        ]
      }
      "FieldType": {
        "required": true,
        "enum": [
            "BOOLEAN",
            "INTEGER",
            "CHAR",
            "STRING",
            "DOUBLE",
            "LONG",
            "DATE",
            "BIGDECIMAL"
        ]
      }
      "Value": {
        "required": false, 
        "neededIf": {
            "Operator": [
                "EQUALS",
                "NOT_EQUALS",
                "GREATER_THAN", - 'if fieldType is not "boolean" or "char"'
                "GREATER_THAN_OR_EQUALS", - 'if fieldType is not     "boolean" or "char"'
                "LESS_THAN", - 'if fieldType is not "boolean" or "char"'
                "LESS_THAN_OR_EQUALS", - 'if fieldType is not "boolean" or "char"'
                "LIKE",
                "NOT_LIKE",
                "BETWEEN" - 'if fieldType is not "boolean" or "char" and "ValueTo" is provided'
            ]
        }
      }
      "ValueTo": {
        "required": false,
        "neededIf": {
            "Operator": [
                "BETWEEN" - 'if fieldType is not "boolean" or "char" and "Value" is provided'
            ]
        }
      }
      "Values": {
        "required": false,
        "neededIf": {
            "Operator": [
                "IN",
                "NOT_IN"
            ]
        }
      }
    }
  ]
}
```

### Sorts - validation

```JSON
{
    "Sorts": [
    {
      "Key": {
            "required": true,
      }
      "Direction": {
            "required": true,
            "enum": [
                "ASC",
                "DESC"
                ]
            }
        }
    ]
}
```

### Page - validation

```JSON
{
    "Page": {
        "required": false,
        "min": 0,
        "Default": 0
    }
}
```

### Size - validation

```JSON
{
    "Size": {
        "required": false,
        "min": 0, 
        "Default": 20
    }
}
```

### StartDate - validation

```JSON
{
    "StartDate": {
        "required": false,
        "format": "yyyy-MM-dd",
        "Default": "now"
    }
}
```

### EndDate - validation

```JSON
{
    "EndDate": {
        "required": false,
        "format": "yyyy-MM-dd", 
        "Default": "max date valid to database"
    }
}
```

### Endpoints

```HTTP
POST /api/v1/search/vehicles
```

```HTTP
POST /api/v1/search/locations
```

```HTTP
GET /api/v1/search/vehicles/models?page={page}&size={size}&brand={brand}

Default: page=0, size=20, brand=null - all models
```

```HTTP
GET /api/v1/search/vehicles/brands?page={page}&size={size}

Default: page=0, size=20
```

```HTTP
GET /api/v1/search/vehicles/colors?page={page}&size={size}

Default: page=0, size=20
```

```HTTP
GET /api/v1/search/vehicles/body-types?page={page}&size={size}

Default: page=0, size=20
```

### Examples

```
curl -X GET "http://localhost:8080/api/v1/search/vehicles/models?page=0&size=20&brand=BMW" 
    -H "accept: application/json"
```

```JSON
{
    "content": [],
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
    "totalElements": 0,
    "totalPages": 0,
    "size": 20,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "numberOfElements": 0,
    "first": true,
    "empty": true
}
```

```
curl -X GET "http://localhost:8080/api/v1/search/vehicles/models?page=0&size=20&brand=TOYOTA" 
    -H "accept: application/json"
```

```JSON
{
    "content": [
        "Yaris"
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
    "totalElements": 1,
    "totalPages": 1,
    "size": 20,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "numberOfElements": 1,
    "first": true,
    "empty": false
}
```

```
curl -X GET "http://localhost:8080/api/v1/search/vehicles/models" 
    -H "accept: application/json"
```

```JSON
{
    "content": [
        "Levante",
        "Yaris"
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
    "totalElements": 2,
    "totalPages": 1,
    "size": 20,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "numberOfElements": 2,
    "first": true,
    "empty": false
}
```

```
curl -X GET "http://localhost:8080/api/v1/search/vehicles/brands" 
    -H "accept: application/json"
```

```JSON
{
    "content": [
        "Maserati",
        "Toyota"
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
    "totalElements": 2,
    "totalPages": 1,
    "size": 20,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "numberOfElements": 2,
    "first": true,
    "empty": false
}
```

```
curl -X GET "http://localhost:8080/api/v1/search/vehicles/colors" 
    -H "accept: application/json"
```

```JSON
{
    "content": [
        "silver"
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
    "totalElements": 1,
    "totalPages": 1,
    "size": 20,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "numberOfElements": 1,
    "first": true,
    "empty": false
}
```

```
curl -X GET "http://localhost:8080/api/v1/search/vehicles/body-types" 
    -H "accept: application/json"
```

```JSON
{
    "content": [
        "monocoque",
        "wielokok"
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
    "totalElements": 2,
    "totalPages": 1,
    "size": 20,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "numberOfElements": 2,
    "first": true,
    "empty": false
}
```

```
curl -X POST "http://localhost:8080/api/v1/search/vehicles" 
    -H "Content-Type: application/json" 
    -d 
    '{
        "Filters": [
            {
              "Key": "location.city", - add prefix Location. to search through Location entity
              "Operator": "NOT_EQUALS",
              "FieldType": "STRING",
              "Value": "Warszawa"
            }
        ],
        "Sorts": [
        ],
        "Page": 0,
        "Size": 20,
        "StartDate": "2023-07-20"
    }'
```

```JSON
{
    "_embedded": {
        "Vehicles": [
            {
                "id": 1,
                "registration": "KR45043",
                "brand": "Toyota",
                "model": "Yaris",
                "dailyFee": 20.34,
                "bestOffer": true,
                "bodyType": "monocoque",
                "productionYear": 1900,
                "fuelType": "benzyna",
                "power": 87,
                "gearbox": "manual",
                "frontWheelDrive": true,
                "doorsNumber": 3,
                "seatsNumber": 5,
                "color": "silver",
                "metalic": true,
                "description": "toyota yaris to najlepsze auto świata!",
                "photoURL": "https://kong-proxy-aws.toyota-europe.com/c1-images/resize/ccis/680x680/zip/pl/product-token/ba4ecbf7-1049-49c6-b93d-65b75c27bf98/vehicle/867d8a62-bdb6-4e74-add7-7493d9536b5c/padding/50,50,50,50/image-quality/70/day-exterior-4_040.png"
            },
            {
                "id": 3,
                "registration": "KR45053",
                "brand": "Toyota",
                "model": "Yaris",
                "dailyFee": 20.34,
                "bestOffer": true,
                "bodyType": "monocoque",
                "productionYear": 1900,
                "fuelType": "benzyna",
                "power": 87,
                "gearbox": "manual",
                "frontWheelDrive": true,
                "doorsNumber": 3,
                "seatsNumber": 5,
                "color": "silver",
                "metalic": true,
                "description": "toyota yaris to najlepsze auto świata!"
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/v1/vehicles?page=0&size=20"
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

```
curl -X POST "http://localhost:8080/api/v1/search/vehicles" 
    -H "Content-Type: application/json" 
    -d 
    '{
        "Filters": [
            {
                "Key": "location.city",
                "Operator": "EQUALS",
                "FieldType": "STRING",
                "Value": "Warszawa"
            },
            {
                "Key": "brand",
                "Operator": "NOT_EQUALS",
                "FieldType": "STRING",
                "Value": "TOYOTA"
            }
        ],
        "Sorts": [],
        "Page": 0,
        "Size": 20,
        "StartDate": "2023-07-20"
    }'
```

```JSON
{
    "_embedded": {
        "Vehicles": [
            {
                "id": 4,
                "registration": "KR45051",
                "brand": "Maserati",
                "model": "Levante",
                "dailyFee": 120.34,
                "bestOffer": true,
                "bodyType": "monocoque",
                "productionYear": 2021,
                "fuelType": "benzyna",
                "power": 387,
                "gearbox": "automat",
                "frontWheelDrive": false,
                "doorsNumber": 3,
                "seatsNumber": 5,
                "color": "silver",
                "metalic": true,
                "description": "brak",
                "photoURL": "https://mycompanypolska.pl/image/w960h560/dMDNtm1c9vuGXvZ1O1J8.jpg"
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/v1/vehicles?page=0&size=20"
        }
    },
    "page": {
        "size": 20,
        "totalElements": 1,
        "totalPages": 1,
        "number": 0
    }
}
```

```
curl -X POST "http://localhost:8080/api/v1/search/vehicles" 
    -H "Content-Type: application/json" 
    -d 
    '{
        "Filters": [
            {
                "Key": "dailyFee",
                "Operator": "BETWEEN",
                "FieldType": "DOUBLE",
                "Value": 10.23,
                "ValueTo": 200.32
            }
        ],
        "Sorts": [
            {
                "Key": "dailyFee",
                "Direction": "DESC"
            }
        ],
        "Page": 0,
        "Size": 20,
        "StartDate": "2023-07-20"
    }'
```

```JSON
{
    "_embedded": {
        "Vehicles": [
            {
                "id": 4,
                "registration": "KR45051",
                "brand": "Maserati",
                "model": "Levante",
                "dailyFee": 120.34,
                "bestOffer": true,
                "bodyType": "monocoque",
                "productionYear": 2021,
                "fuelType": "benzyna",
                "power": 387,
                "gearbox": "automat",
                "frontWheelDrive": false,
                "doorsNumber": 3,
                "seatsNumber": 5,
                "color": "silver",
                "metalic": true,
                "description": "brak",
                "photoURL": "https://mycompanypolska.pl/image/w960h560/dMDNtm1c9vuGXvZ1O1J8.jpg"
            },
            {
                "id": 1,
                "registration": "KR45043",
                "brand": "Toyota",
                "model": "Yaris",
                "dailyFee": 20.34,
                "bestOffer": true,
                "bodyType": "monocoque",
                "productionYear": 1900,
                "fuelType": "benzyna",
                "power": 87,
                "gearbox": "manual",
                "frontWheelDrive": true,
                "doorsNumber": 3,
                "seatsNumber": 5,
                "color": "silver",
                "metalic": true,
                "description": "toyota yaris to najlepsze auto świata!",
                "photoURL": "https://kong-proxy-aws.toyota-europe.com/c1-images/resize/ccis/680x680/zip/pl/product-token/ba4ecbf7-1049-49c6-b93d-65b75c27bf98/vehicle/867d8a62-bdb6-4e74-add7-7493d9536b5c/padding/50,50,50,50/image-quality/70/day-exterior-4_040.png"
            },
            {
                "id": 3,
                "registration": "KR45053",
                "brand": "Toyota",
                "model": "Yaris",
                "dailyFee": 20.34,
                "bestOffer": true,
                "bodyType": "monocoque",
                "productionYear": 1900,
                "fuelType": "benzyna",
                "power": 87,
                "gearbox": "manual",
                "frontWheelDrive": true,
                "doorsNumber": 3,
                "seatsNumber": 5,
                "color": "silver",
                "metalic": true,
                "description": "toyota yaris to najlepsze auto świata!"
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/v1/vehicles?page=0&size=20"
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
