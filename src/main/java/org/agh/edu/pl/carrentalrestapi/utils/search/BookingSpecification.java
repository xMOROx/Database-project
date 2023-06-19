package org.agh.edu.pl.carrentalrestapi.utils.search;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.agh.edu.pl.carrentalrestapi.entity.Booking;
import org.agh.edu.pl.carrentalrestapi.entity.BookingStateCode;
import org.agh.edu.pl.carrentalrestapi.utils.enums.BookingStateCodeConstants;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BookingSpecification {
    public static Specification<Booking> getSpecification(SearchRequest searchRequest) {
        return ((root, query, criteriaBuilder) -> {
            Predicate predicate;
            LocalDateTime startDateTime;
            LocalDateTime endDateTime;

            if (searchRequest.getStartDate() != null) {
                String replacedDate = searchRequest.getStartDate().trim().replace(" ", "T");
                startDateTime = LocalDateTime.parse(convertDate(replacedDate));
            } else {
                startDateTime = LocalDateTime.now();
            }

            if (searchRequest.getEndDate() != null) {
                String replacedDate = searchRequest.getEndDate().trim().replace(" ", "T");
                endDateTime = LocalDateTime.parse(convertDate(replacedDate));
            } else {
                endDateTime = maximalDate();
            }

            Join<Booking, BookingStateCode> joined = root.join("bookingStateCode", JoinType.INNER);

            predicate = criteriaBuilder.and(
                    criteriaBuilder.lessThanOrEqualTo(root.get("receiptDate"), endDateTime),
                    criteriaBuilder.greaterThanOrEqualTo(root.get("returnDate"), startDateTime),
                    criteriaBuilder.or(
                            criteriaBuilder.equal(joined.get("bookingCode"), BookingStateCodeConstants.REN.toString()),
                            criteriaBuilder.equal(joined.get("bookingCode"), BookingStateCodeConstants.RES.toString())
                    )
                    );

            return criteriaBuilder.and(predicate);
        });
    }

    private static String convertDate(String date) {
        if(date.matches("^[1-9][0-9]{3}-[0-9]{2}-[0-9]{2}$")) return date.trim() + "T00:00:00";

        return date.trim().replace(" ", "T");
    }

    private static LocalDateTime maximalDate() {
        return LocalDateTime.parse("9999-12-31T23:59:59");
    }
}
