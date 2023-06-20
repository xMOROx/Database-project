# Bookings

## Reserve vehicle - JSON format

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

## Endpoints

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

## Example

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