## Zapytania

### Sprawdzanie czy możliwe jest wynajęcie samochodu w danym terminie

- Funkcja

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

- Zapytanie JPQL

```SQL
SELECT DISTINCT v.id FROM Vehicle v
                WHERE v.id = :vehicleId AND  v.vehicleStatus.type='AVI'
                AND v.id NOT IN (SELECT b.vehicle.id FROM Booking b WHERE b.receiptDate <= :endDate AND b.returnDate >= :startDate
                AND b.bookingStateCode.bookingCode IN ('RES', 'REN'))
```

- Zapytanie SQL wygenerowane przez Hibernate

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

- Zapytanie SQL wygenerowane gdy powiedzie się rezerwacja

```SQL
    insert 
    into
        bookings
        (booking_state_codeid, locationid, receipt_date, return_date, total_cost, userid, vehicleid) 
    values
        (?, ?, ?, ?, ?, ?, ?)
```

"?" - w zapytaniu to parametry które zastępowane są przez wartości otrzymane z endpointa (POST /api/v1/bookings/reserve).
