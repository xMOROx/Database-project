## Zapytania

### Bookings repository

#### Sprawdzanie czy możliwe jest wynajęcie samochodu w danym terminie

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

#### Zapytania SQL wygenerowane gdy powiedzie się rezerwacja

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
