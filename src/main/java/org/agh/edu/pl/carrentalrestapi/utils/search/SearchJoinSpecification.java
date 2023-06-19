package org.agh.edu.pl.carrentalrestapi.utils.search;

import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SearchJoinSpecification<T, V> extends SearchSpecification<T> {
    @Serial
    private final static long serialVersionUID = 1434534543908345L;
    private static final String JOIN_DELIMITER = ".";

    public SearchJoinSpecification(SearchRequest searchRequest) {
        super(searchRequest);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (!anyJoinExists()) {
            return super.toPredicate(root, query, criteriaBuilder);
        }

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

        query.orderBy(orders);
        return predicate;
    }

    private boolean checkIfJoinExists(String key) {
        return key.contains(JOIN_DELIMITER);
    }

    private boolean anyJoinExists() {
        return this.searchRequest
                .getFilters()
                .stream()
                .anyMatch(filter -> checkIfJoinExists(filter.getKey()));
    }
}
