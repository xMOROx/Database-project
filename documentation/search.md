# Search request body

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

## Operator - enum

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

## FieldType - enum

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

## SortDirection - enum

```JS
enum SortDirection {
    ASC = 'ASC',
    DESC = 'DESC'
}
```

## Filters - validation

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
                "GREATER_THAN", - if fieldType is not "boolean" or "char"
                "GREATER_THAN_OR_EQUALS", - if fieldType is not     "boolean" or "char"
                "LESS_THAN", - if fieldType is not "boolean" or "char"
                "LESS_THAN_OR_EQUALS", - if fieldType is not "boolean" or "char"
                "LIKE",
                "NOT_LIKE",
                "BETWEEN" - if fieldType is not "boolean" or "char" and "ValueTo" is provided
            ]
        }
      }
      "ValueTo": {
        "required": false,
        "neededIf": {
            "Operator": [
                "BETWEEN" - if fieldType is not "boolean" or "char" and "Value" is provided
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

## Sorts - validation

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

## Page - validation

```JSON
{
    "Page": {
        "required": false,
        "min": 0,
        "Default": 0
    }
}
```

## Size - validation

```JSON
{
    "Size": {
        "required": false,
        "min": 0, 
        "Default": 20
    }
}
```

## StartDate - validation

```JSON
{
    "StartDate": {
        "required": false,
        "format": "yyyy-MM-dd",
        "Default": "now"
    }
}
```

## EndDate - validation

```JSON
{
    "EndDate": {
        "required": false,
        "format": "yyyy-MM-dd", 
        "Default": "max date valid to database"
    }
}
```

## Endpoints

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

## Examples

```HTTP
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

```HTTP
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

```HTTP
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

```HTTP
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

```HTTP
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

```HTTP
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

```HTTP
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

```HTTP
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

```HTTP
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
