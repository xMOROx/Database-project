## ADDRESSES
```
create table addresses
(
    address     varchar(100) not null,
    postal_code varchar(15)  not null,
    id          bigint       not null,
    primary key (id)
)
```

## APP_USERS_ROLES
```
create table app_users_roles
(
    userid      bigint not null,
    user_roleid bigint not null,
    primary key (userid, user_roleid)
)
```

## BOOKING_STATE_CODES
```
create table booking_state_codes
(
    id           bigint identity not null,
    booking_code CHAR(3)     NOT NULL,
    description  VARCHAR(50) NOT NULL,
    primary key (id)
)
```

## BOOKINGS
```
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

## CHANGES_BOOKINGS
```
create table changes_bookings
(
    id          bigint identity not null,
    change_date DATETIME default CURRENT_TIMESTAMP NOT NULL,
    who_change  VARCHAR(100)                       NOT NULL,
    bookingid   bigint,
    primary key (id)
)
```

## CITIES
```
create table cities
(
    city varchar(100) not null,
    id   bigint       not null,
    primary key (id)
)
```

## COMMENTS
```
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

## COUNTRIES
```
create table countries
(
    country varchar(100) not null,
    id      bigint       not null,
    primary key (id)
)
```

## EQUIPMENT_SET
```
create table equipment_set
(
    vehicleid   bigint not null,
    equipmentid bigint not null,
    primary key (vehicleid, equipmentid)
)
```

## EQUIPMENTS
```
create table equipments
(
    id             bigint identity not null,
    description    VARCHAR(50) NOT NULL,
    equipment_code VARCHAR(3)  NOT NULL UNIQUE,
    primary key (id)
)
```

## LOCATIONS
```
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

## STARS
```
create table stars
(
    id        bigint identity not null,
    stars     INTEGER NOT NULL check (stars >= 1 AND stars <= 5),
    vehicleid bigint,
    primary key (id)
)
```

## USER_ROLES
```
create table user_roles
(
    id   bigint identity not null,
    type varchar(30) default 'Customer' UNIQUE,
    primary key (id)
)
```

## USERS
```
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

## VEHICLE_STATUS
```
create table vehicle_status
(
    id          bigint identity not null,
    description VARCHAR(50) NOT NULL,
    type        VARCHAR(3)  NOT NULL UNIQUE,
    primary key (id)
)
```

## VEHICLES
```
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

## CONSTRAINTS
```
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
