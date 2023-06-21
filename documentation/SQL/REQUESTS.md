
## CARS with FILTERS (date, max price, min doors)
### Bookings
```
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
        b1_0.receipt_date<=? 
        and b1_0.return_date>=? 
        and (
            b2_0.booking_code=? 
            or b2_0.booking_code=?
        ) 
    order by
        (select
            0) offset ? rows fetch first ? rows only
```
### Vehicles
```
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
        v1_0.doors_number>=? 
        and v1_0.daily_fee<=? 
        and 1=? 
    order by
        (select
            0) offset ? rows fetch first ? rows only
```

## LOCATIONS with FILTERS (city)

```
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
        l1_2.city in(?) 
        and 1=? 
    order by
        (select
            0) offset ? rows fetch first ? rows only
```

## BEST OFFERS
### VEHICLES
```
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
        where
            v1_0.best_offer=1 
        order by
            v1_0.brand asc,
            v1_0.model asc
```

## ADDING USER
```
select
        u1_0.id,
        u1_0.email,
        u1_0.first_name,
        u1_0.password,
        u1_0.pesel,
        u1_0.phone_number,
        u1_0.sur_name,
        u2_0.userid,
        u2_1.id,
        u2_1.type 
    from
        users u1_0 
    left join
        (app_users_roles u2_0 
    join
        user_roles u2_1 
            on u2_1.id=u2_0.user_roleid) 
                on u1_0.id=u2_0.userid 
        where
            u1_0.email='danielbielaszka@gmail.com'
```
```
    insert 
    into
        users
        (email, first_name, password, pesel, phone_number, sur_name) 
    values
        (?, ?, ?, ?, ?, ?)                  
```

## RESERVATION
```
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
        where
            v1_0.id=?
```
```
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
```
select
        b1_0.id,
        b1_0.booking_code,
        b1_0.description 
    from
        booking_state_codes b1_0 
    where
        b1_0.booking_code=?
```

## OTHER
### CITIES
```
select
        distinct l1_1.city 
    from
        locations l1_0 
    left join
        cities l1_1 
            on l1_0.id=l1_1.id 
    order by
        l1_1.city asc offset ? rows fetch first ? rows only
```



### LOCATIONS

```
select l1_0.id,
       l1_0.closing_hours,
       l1_0.email,
       l1_0.opening_hours,
       l1_0.phone_number,
       l1_0.photourl,
       l1_1.address,
       l1_1.postal_code,
       l1_2.city,
       l1_3.country
from locations l1_0
         left join
     addresses l1_1
     on l1_0.id = l1_1.id
         left join
     cities l1_2
     on l1_0.id = l1_2.id
         left join
     countries l1_3
     on l1_0.id = l1_3.id
where l1_0.id = ?
```

### VEHICLE_STATUS

```
select
        v1_0.id,
        v1_0.description,
        v1_0.type 
    from
        vehicle_status v1_0 
    where
        v1_0.id=?
```