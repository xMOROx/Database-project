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
  
### Łączenie filtrów wyszukiwania

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

### Łączenie opcji sortowania

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

### Wyszukiwanie pojazdów z użyciem filtrów

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
                  0) offset ? rows fetch first ? rows only
    ```
### Sortowanie pojazdów
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

[//]: # (TODO: Implement)
