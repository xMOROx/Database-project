package org.agh.edu.pl.carrentalrestapi.utils.enums;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import org.agh.edu.pl.carrentalrestapi.utils.SortRequest;

public enum SortDirection {
    ASC {
        @Override
        public <T> Order buildOrder(Root<T> root, CriteriaBuilder criteriaBuilder, SortRequest sortRequest) {
            return criteriaBuilder.asc(root.get(sortRequest.getKey()));
        }
    },
    DESC {
        @Override
        public <T> Order buildOrder(Root<T> root, CriteriaBuilder criteriaBuilder, SortRequest sortRequest) {
            return criteriaBuilder.desc(root.get(sortRequest.getKey()));
        }
    };


    public abstract <T> Order buildOrder(Root<T> root, CriteriaBuilder criteriaBuilder, SortRequest sortRequest);
}
