# Documentation

## Table of Contents

- [Documentation](#documentation)
  - [Table of Contents](#table-of-contents)
  - [Authors](#authors)
  - [Technologies](#technologies)
  - [Link to remote repository](#link-to-remote-repository)
  - [Tables](#tables)
    - [Addresses](#addresses)
    - [App\_Users\_Roles](#app_users_roles)
    - [Booking\_State\_Codes](#booking_state_codes)
    - [Bookings](#bookings)
    - [Changes\_Bookings](#changes_bookings)
    - [Cities](#cities)
    - [Comments](#comments)
    - [Countries](#countries)
    - [Equipment\_Set](#equipment_set)
    - [Equipments](#equipments)
    - [Locations](#locations)
    - [Stars](#stars)
    - [User\_Roles](#user_roles)
    - [Users](#users)
    - [Vehicle\_Status](#vehicle_status)
    - [Vehicles](#vehicles)
    - [Constraints - Foreign Keys](#constraints---foreign-keys)
  - [Users](#users-1)
    - [Endpoints](#endpoints)
  - [Auth](#auth)
    - [Login](#login)
    - [Register](#register)
    - [Endpoints](#endpoints-1)
  - [Vehicles](#vehicles-1)
    - [Json format - entity](#json-format---entity)
    - [Validation - for post and put methods](#validation---for-post-and-put-methods)
    - [Endpoints](#endpoints-2)
    - [Examples](#examples)
  - [Vehicles statuses](#vehicles-statuses)
    - [Endpoints](#endpoints-3)
    - [Examples](#examples-1)
  - [Locations](#locations-1)
    - [Json format - entity](#json-format---entity-1)
    - [Validation - for post and put methods](#validation---for-post-and-put-methods-1)
    - [Endpoints](#endpoints-4)
    - [Examples](#examples-2)
  - [Bookings](#bookings-1)
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
  - [Zapytania](#zapytania)
    - [Bookings repository](#bookings-repository)
      - [Sprawdzanie czy możliwe jest wynajęcie samochodu w danym terminie](#sprawdzanie-czy-możliwe-jest-wynajęcie-samochodu-w-danym-terminie)
      - [Zapytania SQL wygenerowane gdy powiedzie się rezerwacja](#zapytania-sql-wygenerowane-gdy-powiedzie-się-rezerwacja)
      - [Zapytania SQL wygenerowane podczas wyszukiwania aktywnych rezerwacji (wynajmu) dla użytkownika](#zapytania-sql-wygenerowane-podczas-wyszukiwania-aktywnych-rezerwacji-wynajmu-dla-użytkownika)
      - [Zapytania SQL wygenerowane podczas wyszukiwania rezerwacji (wynajmu)  po ich statusie dla użytkownika](#zapytania-sql-wygenerowane-podczas-wyszukiwania-rezerwacji-wynajmu--po-ich-statusie-dla-użytkownika)
    - [UserRole repository](#userrole-repository)
      - [Uzyskanie roli użytkownika które mogą być mu przydzielone](#uzyskanie-roli-użytkownika-które-mogą-być-mu-przydzielone)
    - [Vehicle repository](#vehicle-repository)
      - [Wyszukiwanie dostępnych pojazdów dla danej lokalizacji w danym przedziale czasowym](#wyszukiwanie-dostępnych-pojazdów-dla-danej-lokalizacji-w-danym-przedziale-czasowym)
      - [Wyszukiwanie wszystkich dostępnych pojazdów](#wyszukiwanie-wszystkich-dostępnych-pojazdów)
      - [Wyszukiwanie wszystkich najlepszych ofert](#wyszukiwanie-wszystkich-najlepszych-ofert)
    - [Search request](#search-request)
      - [Łączenie filtrów wyszukiwania](#łączenie-filtrów-wyszukiwania)
      - [Łączenie opcji sortowania](#łączenie-opcji-sortowania)
      - [Wyszukiwanie pojazdów z użyciem filtrów](#wyszukiwanie-pojazdów-z-użyciem-filtrów)
      - [Sortowanie pojazdów](#sortowanie-pojazdów)

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

## Tables
### Addresses

```SQL
create table addresses
(
    address     varchar(100) not null,
    postal_code varchar(15)  not null,
    id          bigint       not null,
    primary key (id)
)
```

### App_Users_Roles

```SQL
create table app_users_roles
(
    userid      bigint not null,
    user_roleid bigint not null,
    primary key (userid, user_roleid)
)
```

### Booking_State_Codes

```SQL
create table booking_state_codes
(
    id           bigint identity not null,
    booking_code CHAR(3)     NOT NULL,
    description  VARCHAR(50) NOT NULL,
    primary key (id)
)
```

### Bookings

```SQL
create table bookings
(
    id                   bigint identity not null,
    receipt_date         DATETIME default CURRENT_TIMESTAMP NULL,
    return_date          DATETIME                           NOT NULL,
    total_cost           DECIMAL(15, 2)                     NOT NULL,
    booking_state_codeid bigint,
    locationid           bigint,
    userid               bigint,
    vehicleid            bigint,
    primary key (id)
)
```

### Changes_Bookings

```SQL
create table changes_bookings
(
    id          bigint identity not null,
    change_date DATETIME default CURRENT_TIMESTAMP NOT NULL,
    who_change  VARCHAR(100)                       NOT NULL,
    bookingid   bigint,
    primary key (id)
)
```

### Cities

```SQL
create table cities
(
    city varchar(100) not null,
    id   bigint       not null,
    primary key (id)
)
```

### Comments

```SQL
create table comments
(
    id                bigint identity not null,
    content           TEXT     NOT NULL,
    creation_date     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modification_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    rating            INT      NOT NULL DEFAULT 0 check (rating<=10 AND rating>=1),
    userid            bigint,
    vehicleid         bigint,
    primary key (id)
)
```

### Countries

```SQL
create table countries
(
    country varchar(100) not null,
    id      bigint       not null,
    primary key (id)
)
```

### Equipment_Set

```SQL
create table equipment_set
(
    vehicleid   bigint not null,
    equipmentid bigint not null,
    primary key (vehicleid, equipmentid)
)
```

### Equipments

```SQL
create table equipments
(
    id             bigint identity not null,
    description    VARCHAR(50) NOT NULL,
    equipment_code VARCHAR(3)  NOT NULL UNIQUE,
    primary key (id)
)
```

### Locations

```SQL
create table locations
(
    id            bigint identity not null,
    closing_hours varchar(20)  not null,
    email         varchar(255) not null unique,
    opening_hours varchar(20)  not null,
    phone_number  varchar(20)  not null unique,
    photourl      VARCHAR(255) NULL,
    primary key (id)
)
```

### Stars

```SQL
create table stars
(
    id        bigint identity not null,
    stars     INTEGER NOT NULL check (stars >= 1 AND stars <= 5),
    vehicleid bigint,
    primary key (id)
)
```

### User_Roles

```SQL
create table user_roles
(
    id   bigint identity not null,
    type varchar(30) default 'Customer' UNIQUE,
    primary key (id)
)
```

### Users

```SQL
create table users
(
    id           bigint identity not null,
    email        varchar(255) NOT NULL,
    first_name   varchar(70)  NOT NULL,
    password     varchar(255) NOT NULL,
    pesel        varchar(11)  NOT NULL,
    phone_number varchar(20)  NOT NULL,
    sur_name     varchar(70)  NOT NULL,
    primary key (id)
)
```

### Vehicle_Status

```SQL
create table vehicle_status
(
    id          bigint identity not null,
    description VARCHAR(50) NOT NULL,
    type        VARCHAR(3)  NOT NULL UNIQUE,
    primary key (id)
)
```

### Vehicles

```SQL
create table vehicles
(
    id                bigint identity not null,
    best_offer        tinyint        NOT NULL default 0,
    body_type         VARCHAR(100)   NOT NULL,
    brand             VARCHAR(255)   NULL,
    color             VARCHAR(50)    NOT NULL,
    daily_fee         DECIMAL(15, 2) NOT NULL,
    description       varchar(1000)  NOT NULL,
    doors_number      INT            NOT NULL check (doors_number <= 5 AND doors_number >= 3),
    front_wheel_drive tinyint        NOT NULL default 1,
    fuel_type         VARCHAR(30)    NOT NULL,
    gearbox           VARCHAR(50)    NOT NULL,
    metalic           tinyint        NOT NULL default 0,
    model             VARCHAR(255)   NULL,
    photourl          VARCHAR(255)   NULL,
    power             INT            NOT NULL,
    production_year   INT            NOT NULL check (production_year >= 1900),
    registration      VARCHAR(30)    NOT NULL UNIQUE,
    seats_number      INT            NOT NULL default 5 check (seats_number>=2 AND seats_number<=9),
    locationid        bigint,
    vehicle_statusid  bigint,
    primary key (id)
)
```

### Constraints - Foreign Keys

```SQL
alter table addresses
    add constraint FKhn2hysrhumqexwokc5t8bhc0c
        foreign key (id)
            references locations


alter table app_users_roles
    add constraint FKo4cj2ne2ndhi26xa53t1qfvwf
        foreign key (user_roleid)
            references user_roles


alter table app_users_roles
    add constraint FKh67ibgqlf0u9gl44qpa575ray
        foreign key (userid)
            references users



alter table bookings
    add constraint FKlrdeymrl0psmvkk40efqo5oy8
        foreign key (booking_state_codeid)
            references booking_state_codes


alter table bookings
    add constraint FK9yutnp1yjekd1hduab6qv19xs
        foreign key (locationid)
            references locations


alter table bookings
    add constraint FKhpweps6it8n224l44tahx19y2
        foreign key (userid)
            references users

alter table bookings
    add constraint FKa17ddxcsevuj5mj47vwv9xg9g
        foreign key (vehicleid)
            references vehicles


alter table changes_bookings
    add constraint FKml16yy5rnopxvq73jb1wrd9re
        foreign key (bookingid)
            references bookings


alter table cities
    add constraint FKfpgypc14v811rrg2urwge4x3
        foreign key (id)
            references locations


alter table comments
    add constraint FKjxggc60wwwlf4xl065fjrx68y
        foreign key (userid)
            references users


alter table comments
    add constraint FK5l0tmce4cdkfwc8xtygpo2cjq
        foreign key (vehicleid)
            references vehicles


alter table countries
    add constraint FKlu3v2k02uur32ccpig1jvnlct
        foreign key (id)
            references locations


alter table equipment_set
    add constraint FKfg724kg6rda59aamcthni3tvc
        foreign key (equipmentid)
            references equipments


alter table equipment_set
    add constraint FKox65lx66nhp6txjye3gvcvqla
        foreign key (vehicleid)
            references vehicles


alter table stars
    add constraint FKck8rmmigyycjwgx3x62wq1bva
        foreign key (vehicleid)
            references vehicles


alter table vehicles
    add constraint FKtg4afmbd5ljonp8vmdnx90gns
        foreign key (locationid)
            references locations


alter table vehicles
    add constraint FKegnbl4t5wbk4lwlgkb904u7t1
        foreign key (vehicle_statusid)
            references vehicle_status
```

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

## Zapytania

### Bookings repository

#### Sprawdzanie czy możliwe jest wynajęcie samochodu w danym terminie

- **Endpoint** `POST /api/v1/bookings/reserve`

- **Funkcja**

  ```JAVA
  private boolean isVehicleAvailableToRent(Long vehicleId,
                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

      String query = "SELECT DISTINCT v.id FROM Vehicle v " +
              "WHERE v.id = :vehicleId AND  v.vehicleStatus.type='AVI'" +
              "AND v.id NOT IN (SELECT b.vehicle.id FROM Booking b WHERE b.receiptDate <= :endDate AND b.returnDate >= :startDate " +
              "AND b.bookingStateCode.bookingCode IN ('RES', 'REN'))";


      return entityManager.createQuery(query, Booking.class)
              .setParameter("vehicleId", vehicleId)
              .setParameter("startDate", startDate)
              .setParameter("endDate", endDate)
              .getResultList()
              .size() > 0;
  }
  ```

- **Zapytanie JPQL**

  ```SQL
  SELECT DISTINCT v.id FROM Vehicle v
                  WHERE v.id = :vehicleId AND  v.vehicleStatus.type='AVI'
                  AND v.id NOT IN (SELECT b.vehicle.id FROM Booking b WHERE b.receiptDate <= :endDate AND b.returnDate >= :startDate
                  AND b.bookingStateCode.bookingCode IN ('RES', 'REN'))
  ```

- **Zapytanie SQL wygenerowane przez Hibernate**

  ```SQL
  select
      distinct v1_0.id 
  from
      vehicles v1_0 
  join
      vehicle_status v2_0 
          on v2_0.id=v1_0.vehicle_statusid 
  where
      v1_0.id=? 
      and v2_0.type='AVI' 
      and v1_0.id not in(select
          b1_0.vehicleid 
      from
          bookings b1_0 
      join
          booking_state_codes b2_0 
              on b2_0.id=b1_0.booking_state_codeid 
      where
          b1_0.receipt_date<=? 
          and b1_0.return_date>=? 
          and b2_0.booking_code in('RES','REN'))
  ```

#### Zapytania SQL wygenerowane gdy powiedzie się rezerwacja

- **Endpoint** `POST /api/v1/bookings/reserve`

- **Wyszukanie pojazdu - zapytanie stworzone przez Hibernate**
  Nie jest wymagane pisanie własnego zapytania JPQL ponieważ JPA dostarcza podstawowe operację wyszukiwanie przez dynamiczne generowanie zapytań.
  W tym wypadku wykorzystujemy EntityManager.find() który zwraca obiekt o podanym identyfikatorze lub null jeśli nie istnieje.
  &NewLine;
  &NewLine;

  ```JAVA
  Vehicle vehicle = entityManager.find(Vehicle.class, vehicleId);
  ```

  ```SQL
  select
      v1_0.id,
      v1_0.best_offer,
      v1_0.body_type,
      v1_0.brand,
      v1_0.color,
      v1_0.daily_fee,
      v1_0.description,
      v1_0.doors_number,
      v1_0.front_wheel_drive,
      v1_0.fuel_type,
      v1_0.gearbox,
      l1_0.id,
      l1_0.closing_hours,
      l1_0.email,
      l1_0.opening_hours,
      l1_0.phone_number,
      l1_0.photourl,
      l1_1.address,
      l1_1.postal_code,
      l1_2.city,
      l1_3.country,
      v1_0.metalic,
      v1_0.model,
      v1_0.photourl,
      v1_0.power,
      v1_0.production_year,
      v1_0.registration,
      v1_0.seats_number,
      v2_0.id,
      v2_0.description,
      v2_0.type 
  from
      vehicles v1_0 
  left join
      (locations l1_0 
  left join
      addresses l1_1 
          on l1_0.id=l1_1.id 
  left join
      cities l1_2 
          on l1_0.id=l1_2.id 
  left join
      countries l1_3 
          on l1_0.id=l1_3.id) 
              on l1_0.id=v1_0.locationid 
      left join
          vehicle_status v2_0 
              on v2_0.id=v1_0.vehicle_statusid 
      whered
          v1_0.id=?
  ```

  Jak można zauwayżyć Hibernate generuje zapytanie które pobiera wszystkie dane związane z pojazdem, w tym lokalizację.

  &NewLine;
  &NewLine;

  Chociaż w kodzie używamy:

  ```JAVA
  Location location = entityManager.find(Location.class, reservation.getLocationID());
  ```

  To hibernate/JPA nie generują nowego zapytania SQL tylko pobiera danę które uzyskał w poprzednim zapytaniu.

  &NewLine;
  &NewLine;

- **Wyszukanie użytkownika - zapytanie stworzone przez Hibernate**

  &NewLine;
  &NewLine;

  Sytuacja analogiczna jak w przypadku wyszukiwania pojazdu.

  ```JAVA
  User user = entityManager.find(User.class, reservation.getUserID());
  ```

  ```SQL
  select
      u1_0.id,
      u1_0.email,
      u1_0.first_name,
      u1_0.password,
      u1_0.pesel,
      u1_0.phone_number,
      u1_0.sur_name 
  from
      users u1_0 
  where
      u1_0.id=?
  ```

- **Wyszukanie statusu** `zarezerwowany`
  - **Zapytanie JPQL**

    &NewLine;

    Zapytanie stworzone przez nas z powodu braku możliwości wyszukiwania entity **`BookingStateCode`** w sposób dynamiczny poprzez podanie nazwy statusu **`RES`**.

    &NewLine;
    &NewLine;

    ```SQL
    SELECT b FROM BookingStateCode b WHERE b.bookingCode=:bookingCode
    ```

  - **Zapytanie SQL wygenerowane przez Hibernate**

    ```SQL
    select
        b1_0.id,
        b1_0.booking_code,
        b1_0.description 
    from
        booking_state_codes b1_0 
    where
        b1_0.booking_code=?
    ```

  - **Zapytanie dodające booking jak wszystko poszło pomyśle**

    ```SQL
        insert 
        into
            bookings
            (booking_state_codeid, locationid, receipt_date, return_date, total_cost, userid, vehicleid) 
        values
            (?, ?, ?, ?, ?, ?, ?)
    ```

    `?` - w zapytaniu to parametry które zastępowane są przez wartości otrzymane z endpointa `POST /api/v1/bookings/reserve`.

#### Zapytania SQL wygenerowane podczas wyszukiwania aktywnych rezerwacji (wynajmu) dla użytkownika

- **Endpoint** `GET /api/v1/users/{id}/bookings/active`

- **Kod i zapytania**
  
  - **Kod**
  
    ```JAVA
    @Query(value = "SELECT b FROM Booking b WHERE b.bookingStateCode.bookingCode='REN' " +
            "AND b.returnDate >= CURRENT_DATE AND b.user.id=:userId",
            countQuery = "SELECT count(b) FROM Booking b WHERE b.bookingStateCode.bookingCode='REN' " +
                    "AND b.returnDate >= CURRENT_DATE AND b.user.id=:userId")
    Page<Booking> findActiveBookingsByUserId(Long userId, Pageable pageRequest);
    ```

  - **Zapytania JPQL**
  
    Faktyczne zapytanie które wyszukuje aktywnych rezerwacji.

    &NewLine;
    &NewLine;

    ```SQL
    SELECT b FROM Booking b WHERE b.bookingStateCode.bookingCode='REN' " +
            "AND b.returnDate >= CURRENT_DATE AND b.user.id=:userId
    ```

    Zapytanie pomocnicznego liczącego ilość aktywnych rezerwacji.
    Wymagane dla **`Pageable`**.

    &NewLine;
    &NewLine;

    ```SQL
    SELECT count(b) FROM Booking b WHERE b.bookingStateCode.bookingCode='REN' " +
                    "AND b.returnDate >= CURRENT_DATE AND b.user.id=:userId
    ```

  - **Zapytanie wygenerowane przez Hibernate**

    ```SQL
    select
        b1_0.id,
        b1_0.booking_state_codeid,
        b1_0.locationid,
        b1_0.receipt_date,
        b1_0.return_date,
        b1_0.total_cost,
        b1_0.userid,
        b1_0.vehicleid 
    from
        bookings b1_0 
    join
        booking_state_codes b2_0 
            on b2_0.id=b1_0.booking_state_codeid 
    where
        b2_0.booking_code='REN' 
        and b1_0.return_date>=convert(date,getdate()) 
        and b1_0.userid=? 
    order by
        (select
            1) offset ? rows fetch first ? rows only
    ```

    Pojawiające się zapytanie **`offset ? rows fetch first ? rows only`** jest wymagane przez **`Pageable`** i **`?`** są zastępowane przez wartości otrzymane z endpointa **`GET /api/v1/users/{id}/bookings/active?page={page}&size={size}`**.

#### Zapytania SQL wygenerowane podczas wyszukiwania rezerwacji (wynajmu)  po ich statusie dla użytkownika

- **Kod i zapytania**

  - **Endpoints**

    - `GET /api/v1/users/1/bookings/reserved`
    - `GET /api/v1/users/1/bookings/rented`
  
  - **Kod**

    ```JAVA
    @Query(value = "SELECT b FROM Booking b WHERE b.bookingStateCode.bookingCode=:bookingStateCode AND b.user.id=:userId",
            countQuery = "SELECT count(b) FROM Booking b WHERE b.bookingStateCode.bookingCode=:bookingStateCode AND b.user.id=:userId")
    @Transactional
    Page<Booking> findByUserIdAndBookingStateCode(Long userId, String bookingStateCode, Pageable pageable);
    ```

  - **Zapytania JPQL**
  
    Faktyczne zapytanie które wyszukuje rezerwacje.

    &NewLine;
    &NewLine;

    ```SQL
    SELECT b FROM Booking b WHERE b.bookingStateCode.bookingCode=:bookingStateCode AND b.user.id=:userId
    ```

    Zapytanie pomocnicznego liczącego ilość rezerwacji.
    Wymagane dla **`Pageable`**.

    &NewLine;
    &NewLine;

    ```SQL
    SELECT count(b) FROM Booking b WHERE b.bookingStateCode.bookingCode=:bookingStateCode AND b.user.id=:userId
    ```

  - **Zapytanie wygenerowane przez Hibernate**

    ```SQL
     select
        b1_0.id,
        b1_0.booking_state_codeid,
        b1_0.locationid,
        b1_0.receipt_date,
        b1_0.return_date,
        b1_0.total_cost,
        b1_0.userid,
        b1_0.vehicleid 
    from
        bookings b1_0 
    join
        booking_state_codes b2_0 
            on b2_0.id=b1_0.booking_state_codeid 
    where
        b2_0.booking_code=? 
        and b1_0.userid=? 
    order by
        (select
            1) offset ? rows fetch first ? rows only
     ```

### UserRole repository

#### Uzyskanie roli użytkownika które mogą być mu przydzielone

- **Endpoint** `GET /api/v1/users/{id}/available-roles`

- **Kod i zapytania**

  - **Kod**

    ```JAVA
    @Transactional
    public Page<UserRole> findUnExistingDistinctUserRolesForUser(Long id, Pageable pageable) {
        TypedQuery<UserRole> query = entityManager.createQuery("SELECT ur FROM UserRole ur WHERE ur.id NOT IN " +
                "(SELECT ur.id FROM UserRole ur JOIN ur.users u WHERE u.id = :id)", UserRole.class);
        TypedQuery<UserRole> typedQuery = query.setParameter("id", id);

        TypedQuery<Long> countQuery = entityManager.createQuery("SELECT COUNT(ur) FROM UserRole ur WHERE ur.id NOT IN " +
                "(SELECT ur.id FROM UserRole ur JOIN ur.users u WHERE u.id = :id)", Long.class);

        countQuery.setParameter("id", id);

        return new PageImpl<>(typedQuery.getResultList(), pageable, countQuery.getSingleResult());
    }
    ```

  - **Zapytania JPQL**

    ```SQL
    SELECT ur FROM UserRole ur WHERE ur.id NOT IN
              (SELECT ur.id FROM UserRole ur JOIN ur.users u WHERE u.id = :id)
    ```

    Zapytanie liczące

    ```SQL
    SELECT COUNT(ur) FROM UserRole ur WHERE ur.id NOT IN
                (SELECT ur.id FROM UserRole ur JOIN ur.users u WHERE u.id = :id)
    ```

  - **Zapytanie wygenerowane przez Hibernate**

    ```SQL
    select
        u1_0.id,
        u1_0.type 
    from
        user_roles u1_0 
    where
        u1_0.id not in(select
            u2_0.id 
        from
            user_roles u2_0 
        join
            app_users_roles u3_0 
                on u2_0.id=u3_0.user_roleid 
        where
            u3_0.userid=?)
    ```

    ```SQL
    select
        count(u1_0.id) 
    from
        user_roles u1_0 
    where
        u1_0.id not in(select
            u2_0.id 
        from
            user_roles u2_0 
        join
            app_users_roles u3_0 
                on u2_0.id=u3_0.user_roleid 
        where
            u3_0.userid=?)
    ```

### Vehicle repository

#### Wyszukiwanie dostępnych pojazdów dla danej lokalizacji w danym przedziale czasowym

- **Endpoint** `GET /api/v1/locations/1/available-vehicles`

- **Kod i zapytania**

  - **Kod**

    ```JAVA
    @Transactional
    @Query(value = "SELECT DISTINCT v FROM Vehicle v " + "JOIN Location l ON(v.location.id=l.id)" +
            "WHERE l.id=?1 AND v.vehicleStatus.type='AVI' AND v.id NOT IN (SELECT b.vehicle.id FROM Booking b WHERE b.receiptDate <= ?3 AND b.returnDate >= ?2 " +
            "AND b.bookingStateCode.bookingCode IN ('RES', 'REN'))",

            countQuery = "SELECT COUNT(DISTINCT v) FROM Vehicle v " + "JOIN Location l ON(v.location.id=l.id)" +
                    "WHERE l.id=?1 AND v.vehicleStatus.type='AVI' AND v.id NOT IN (SELECT b.vehicle.id FROM Booking b WHERE b.receiptDate <= ?3 AND b.returnDate >= ?2 " +
                    "AND b.bookingStateCode.bookingCode IN ('RES', 'REN'))")
    Page<Vehicle> findAvailableVehiclesForLocation(Long locationId,
                                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate, Pageable pageable);
    ```

  - **Zapytania JPQL**

    ```SQL
    SELECT DISTINCT v FROM Vehicle v JOIN Location l ON(v.location.id=l.id)
              WHERE l.id=?1 AND v.vehicleStatus.type='AVI' 
              AND v.id NOT IN (SELECT b.vehicle.id FROM Booking b WHERE b.receiptDate <= ?3 AND b.returnDate >= ?2
              AND b.bookingStateCode.bookingCode IN ('RES', 'REN'))
    ```

    Zapytanie liczące dla paginacji

    ```SQL
    SELECT COUNT(DISTINCT v) FROM Vehicle v JOIN Location l ON(v.location.id=l.id)
                    WHERE l.id=?1 AND v.vehicleStatus.type='AVI' 
                    AND v.id NOT IN (SELECT b.vehicle.id FROM Booking b WHERE b.receiptDate <= ?3 AND b.returnDate >= ?2 
                    AND b.bookingStateCode.bookingCode IN ('RES', 'REN'))
    ```

    Gdzie: `?1` - id lokalizacji, `?2` - data odbioru, `?3` - data zwrotu

  - **Zapytanie wygenerowane przez Hibernate**

    ```SQL
    select
        * 
    from
        (select
            distinct top (?+?) v1_0.id c0,
            v1_0.best_offer c1,
            v1_0.body_type c2,
            v1_0.brand c3,
            v1_0.color c4,
            v1_0.daily_fee c5,
            v1_0.description c6,
            v1_0.doors_number c7,
            v1_0.front_wheel_drive c8,
            v1_0.fuel_type c9,
            v1_0.gearbox c10,
            v1_0.locationid c11,
            v1_0.metalic c12,
            v1_0.model c13,
            v1_0.photourl c14,
            v1_0.power c15,
            v1_0.production_year c16,
            v1_0.registration c17,
            v1_0.seats_number c18,
            v1_0.vehicle_statusid c19,
            dense_rank() over(
        order by
            v1_0.id asc,v1_0.best_offer asc,v1_0.body_type asc,v1_0.brand asc,v1_0.color asc,v1_0.daily_fee asc,v1_0.description asc,v1_0.doors_number asc,v1_0.front_wheel_drive asc,v1_0.fuel_type asc,v1_0.gearbox asc,v1_0.locationid asc,v1_0.metalic asc,v1_0.model asc,v1_0.photourl asc,v1_0.power asc,v1_0.production_year asc,v1_0.registration asc,v1_0.seats_number asc,v1_0.vehicle_statusid asc) rn 
        from
            vehicles v1_0 
        join
            locations l1_0 
                on (
                    v1_0.locationid=l1_0.id
                ) 
        join
            vehicle_status v2_0 
                on v2_0.id=v1_0.vehicle_statusid 
        where
            l1_0.id=? 
            and v2_0.type='AVI' 
            and v1_0.id not in(select
                b1_0.vehicleid c0 
            from
                bookings b1_0 
            join
                booking_state_codes b2_0 
                    on b2_0.id=b1_0.booking_state_codeid 
            where
                b1_0.receipt_date<=? 
                and b1_0.return_date>=? 
                and b2_0.booking_code in('RES','REN'))
        ) r_0_ 
    where
        r_0_.rn>? 
    order by
        r_0_.rn
    ```

    Pobiera dane także o aktualizacji

    ```SQL
    select
        l1_0.id,
        l1_0.closing_hours,
        l1_0.email,
        l1_0.opening_hours,
        l1_0.phone_number,
        l1_0.photourl,
        l1_1.address,
        l1_1.postal_code,
        l1_2.city,
        l1_3.country 
    from
        locations l1_0 
    left join
        addresses l1_1 
            on l1_0.id=l1_1.id 
    left join
        cities l1_2 
            on l1_0.id=l1_2.id 
    left join
        countries l1_3 
            on l1_0.id=l1_3.id 
    where
        l1_0.id=?
    ```

    A także o statusie samochodu

    ```SQL
        select
        v1_0.id,
        v1_0.description,
        v1_0.type 
    from
        vehicle_status v1_0 
    where
        v1_0.id=?
    ```

    `dense_rank()` została użyta do numerowania wyników zapytania w celu umożliwienia paginacji.
    `?+?` - pierwszy parametr to rozmiar strony, drugi to numer strony
    `rn` - numer strony

#### Wyszukiwanie wszystkich dostępnych pojazdów

- **Endpoint** `GET /api/v1/vehicles`

- **Kod i zapytania**

  - **Kod**

    ```JAVA
    @Transactional
    @Query(value = "SELECT DISTINCT v FROM Vehicle v INNER JOIN VehicleStatus vs ON v.vehicleStatus.id  =  vs.id WHERE vs.type = 'AVI' ORDER BY v.brand ASC, v.model ASC",
          countQuery = "SELECT COUNT(DISTINCT v) FROM Vehicle v INNER JOIN VehicleStatus vs ON v.vehicleStatus.id  =  vs.id WHERE vs.type = 'AVI'")
    Page<Vehicle> findAllAvailableVehicles(Pageable pageable); 
    ```

  - **Zapytanie JPQL**

    ```SQL
    SELECT DISTINCT v FROM Vehicle v INNER JOIN VehicleStatus vs ON v.vehicleStatus.id  =  vs.id WHERE vs.type = 'AVI' ORDER BY v.brand ASC, v.model ASC
    ```

    Zapytanie liczące dla paginacji

    ```SQL
    SELECT COUNT(DISTINCT v) FROM Vehicle v INNER JOIN VehicleStatus vs ON v.vehicleStatus.id  =  vs.id WHERE vs.type = 'AVI' 
    ```

  - **Zapytanie wygenerowane przez Hibernate**

    ```SQL
    select
        distinct v1_0.id,
        v1_0.best_offer,
        v1_0.body_type,
        v1_0.brand,
        v1_0.color,
        v1_0.daily_fee,
        v1_0.description,
        v1_0.doors_number,
        v1_0.front_wheel_drive,
        v1_0.fuel_type,
        v1_0.gearbox,
        v1_0.locationid,
        v1_0.metalic,
        v1_0.model,
        v1_0.photourl,
        v1_0.power,
        v1_0.production_year,
        v1_0.registration,
        v1_0.seats_number,
        v1_0.vehicle_statusid 
    from
        vehicles v1_0 
    join
        vehicle_status v2_0 
            on v1_0.vehicle_statusid=v2_0.id 
    where
        v2_0.type='AVI' 
    order by
        v1_0.brand asc,
        v1_0.model asc offset ? rows fetch first ? rows only
    ```

    &NewLine;

    Jak można zauważyć w tym zapytaniu wygenerowanym przez hibernate używa on `offset` i `fetch first` do paginacji.

#### Wyszukiwanie wszystkich najlepszych ofert

- **Endpoint** `GET /api/v1/best-offers`

  - **Kod i zapytania**

    - **Kod**

      ```JAVA
      @Transactional
      @Query(value = "SELECT DISTINCT v FROM Vehicle v LEFT JOIN FETCH v.equipment INNER JOIN VehicleStatus vs ON v.vehicleStatus.id  =  vs.id WHERE v.bestOffer = true AND vs.type = 'AVI' ORDER BY v.brand ASC, v.model ASC",
              countQuery = "SELECT COUNT(DISTINCT v) FROM Vehicle v LEFT JOIN v.equipment INNER JOIN VehicleStatus vs ON v.vehicleStatus.id  =  vs.id WHERE v.bestOffer = true AND vs.type = 'AVI'")
      Page<Vehicle> findBestOffer(Pageable pageable);
      ```

    - **Zapytania JPQL**

      ```SQL 
      SELECT DISTINCT v FROM Vehicle v LEFT JOIN FETCH v.equipment INNER JOIN VehicleStatus vs ON v.vehicleStatus.id  =  vs.id WHERE v.bestOffer = true AND vs.type = 'AVI' ORDER BY v.brand ASC, v.model ASC
      ```

      `LEFT JOIN FETCH` zostało użyte do pobrania wyposażenia pojazdu bez lazy loading.

      &NewLine;
      &NewLine;

      Zapytanie liczące dla paginacji

      ```SQL
      SELECT COUNT(DISTINCT v) FROM Vehicle v LEFT JOIN v.equipment INNER JOIN VehicleStatus vs ON v.vehicleStatus.id  =  vs.id WHERE v.bestOffer = true AND vs.type = 'AVI'
      ```

    - **Zapytanie wygenerowane przez Hibernate**

      ```SQL
      select
        distinct v1_0.id,
        v1_0.best_offer,
        v1_0.body_type,
        v1_0.brand,
        v1_0.color,
        v1_0.daily_fee,
        v1_0.description,
        v1_0.doors_number,
        e1_0.vehicleid,
        e1_1.id,
        e1_1.description,
        e1_1.equipment_code,
        v1_0.front_wheel_drive,
        v1_0.fuel_type,
        v1_0.gearbox,
        v1_0.locationid,
        v1_0.metalic,
        v1_0.model,
        v1_0.photourl,
        v1_0.power,
        v1_0.production_year,
        v1_0.registration,
        v1_0.seats_number,
        v1_0.vehicle_statusid 
      from
          vehicles v1_0 
      left join
          (equipment_set e1_0 
      join
          equipments e1_1 
              on e1_1.id=e1_0.equipmentid) 
                  on v1_0.id=e1_0.vehicleid 
          join
              vehicle_status v2_0 
                  on v1_0.vehicle_statusid=v2_0.id 
          where
              v1_0.best_offer=1 
              and v2_0.type='AVI' 
          order by
              v1_0.brand asc,
              v1_0.model asc
      ```

### Search request

#### Łączenie filtrów wyszukiwania

- **Kod**

  ```java
  Predicate predicate = criteriaBuilder
                  .equal(criteriaBuilder.literal(Boolean.TRUE), Boolean.TRUE);
  
          for (FilterRequest filter : this.searchRequest.getFilters()) {
              log.info("Filter: Key = {}; Operator = {}; Value = {} | ValueTo = {} | Values = {}",
                      filter.getKey(),
                      filter.getOperator().toString(),
                      filter.getValue(),
                      filter.getValueTo(),
                      filter.getValues());
  
              if (checkIfJoinExists(filter.getKey())) {
                  String[] keys = filter.getKey().split("\\.");
                  Join<T, V> join = root.join(keys[0]);
  
                  predicate = filter
                          .getOperator()
                          .build(join, criteriaBuilder, filter, predicate);
              } else {
                  predicate = filter
                          .getOperator()
                          .build(root, criteriaBuilder, filter, predicate);
              }
          }
  ```

#### Łączenie opcji sortowania

- **Kod**

  ```java
  List<Order> orders = new ArrayList<>();
  
          for (SortRequest sort : this.searchRequest.getSorts()) {
              log.info("Sort: {} {}", sort.getKey(), sort.getDirection().toString());
  
              if (checkIfJoinExists(sort.getKey())) {
                  String[] keys = sort.getKey().split("\\.");
                  Join<T, V> join = root.join(keys[0]);
  
                  orders.add(sort
                          .getDirection()
                          .buildOrder(join, criteriaBuilder, sort));
              } else {
                  orders.add(sort
                          .getDirection()
                          .buildOrder(root, criteriaBuilder, sort));
              }
          }
  ```

#### Wyszukiwanie pojazdów z użyciem filtrów

- **Endpoint** `POST /api/v1/search/vehicles`
  - **Request body**

    ```json
    {
      "Filters": [
          {
              "Key": "dailyFee",
              "Operator": "BETWEEN",
              "FieldType": "DOUBLE",
              "Value": 50,
              "ValueTo": 100
          },
          {
              "Key": "doorsNumber",
              "Operator": "BETWEEN",
              "FieldType": "DOUBLE",
              "Value": 2,
              "ValueTo": 5
          },
          {
              "Key": "location.city",
              "Operator": "EQUALS",
              "FieldType": "STRING",
              "Value": "Kraków"
          }
      ],
      "Sorts": [],
      "Page": 0,
      "Size": 20,
      "StartDate": "2023-06-22",
      "EndDate": "2023-06-29"
    }
    ```

  - **Zapytanie wygenerowane przez Hibernate**

    ```sql
    select
        v1_0.id,
          v1_0.best_offer,
          v1_0.body_type,
          v1_0.brand,
          v1_0.color,
          v1_0.daily_fee,
          v1_0.description,
          v1_0.doors_number,
          v1_0.front_wheel_drive,
          v1_0.fuel_type,
          v1_0.gearbox,
          v1_0.locationid,
          v1_0.metalic,
          v1_0.model,
          v1_0.photourl,
          v1_0.power,
          v1_0.production_year,
          v1_0.registration,
          v1_0.seats_number,
          v1_0.vehicle_statusid 
      from
          vehicles v1_0 
      join
          (locations l1_0 
      left join
          cities l1_1 
              on l1_0.id=l1_1.id) 
                  on l1_0.id=v1_0.locationid 
          where
              l1_1.city=? 
              and v1_0.doors_number<=? 
              and v1_0.doors_number>=? 
              and v1_0.daily_fee<=? 
              and v1_0.daily_fee>=? 
              and 1=? 
          order by
              (select
                  1) offset ? rows fetch first ? rows only
    ```

#### Sortowanie pojazdów

- **Endpoint** `POST /api/v1/search/vehicles`

  - **Request body**

    ```json
    {
      "Filters": [],
      "Sorts": [
        {
          "Key": "brand",
          "Direction": "ASC"
        },
        {
          "Key": "model",
          "Direction": "DESC"
        }
      ],
      "Page": 0,
      "Size": 20,
      "StartDate": "2023-06-22",
      "EndDate": "2023-06-29"
    }
    ```

  - **Zapytanie wygenerowane przez Hibernate**

    ```sql
    select
        v1_0.id,
        v1_0.best_offer,
        v1_0.body_type,
        v1_0.brand,
        v1_0.color,
        v1_0.daily_fee,
        v1_0.description,
        v1_0.doors_number,
        v1_0.front_wheel_drive,
        v1_0.fuel_type,
        v1_0.gearbox,
        v1_0.locationid,
        v1_0.metalic,
        v1_0.model,
        v1_0.photourl,
        v1_0.power,
        v1_0.production_year,
        v1_0.registration,
        v1_0.seats_number,
        v1_0.vehicle_statusid 
    from
        vehicles v1_0 
    where
        1=? 
    order by
        v1_0.brand asc,
        v1_0.model desc offset ? rows fetch first ? rows only
    ```
