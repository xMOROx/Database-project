# Vehicles

## Json format - entity

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

## Validation - for post and put methods

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

## Endpoints

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

## Examples

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
