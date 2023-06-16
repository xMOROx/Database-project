package org.agh.edu.pl.carrentalrestapi.utils.search;

import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
public class SearchSpecification<T> implements Specification<T> {
    @Serial
    private final static long serialVersionUID = 158590859403908345L;
    protected final transient SearchRequest searchRequest;


    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder
                .equal(criteriaBuilder.literal(Boolean.TRUE), Boolean.TRUE);


        for (FilterRequest filter : this.searchRequest.getFilters()) {
            log.info("Filter: {} {} {}", filter.getKey(), filter.getOperator().toString(), filter.getValue());

            predicate = filter
                    .getOperator()
                    .build(root, criteriaBuilder, filter, predicate);

        }

        List<Order> orders = new ArrayList<>();

        for (SortRequest sort : this.searchRequest.getSorts()) {
            orders.add(sort.getDirection().buildOrder(root, criteriaBuilder, sort));
        }

        query.orderBy(orders);
        return predicate;
    }

    public static Pageable getPageable(Integer page, Integer size) {
        return PageRequest.of(
                Objects.requireNonNullElse(page, 0),
                Objects.requireNonNullElse(size, 20));
    }
}