package org.agh.edu.pl.carrentalrestapi.utils.enums;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import org.agh.edu.pl.carrentalrestapi.utils.search.SortRequest;

public enum SortDirection {
    ASC {
        @Override
        public <T> Order buildOrder(Root<T> root, CriteriaBuilder criteriaBuilder, SortRequest sortRequest) {
            return criteriaBuilder.asc(root.get(sortRequest.getKey()));
        }
        @Override
        public <T, V> Order buildOrder(Join<T, V> join, CriteriaBuilder criteriaBuilder, SortRequest sortRequest) {
            String key = sortRequest.getKey().split("\\.")[1];
            return criteriaBuilder.asc(join.get(key));
        }
    },
    DESC {
        @Override
        public <T> Order buildOrder(Root<T> root, CriteriaBuilder criteriaBuilder, SortRequest sortRequest) {
            return criteriaBuilder.desc(root.get(sortRequest.getKey()));
        }

        @Override
        public <T, V> Order buildOrder(Join<T, V> join, CriteriaBuilder criteriaBuilder, SortRequest sortRequest) {
            String key = sortRequest.getKey().split("\\.")[1];
            return criteriaBuilder.desc(join.get(key));
        }
    };


    public abstract <T> Order buildOrder(Root<T> root, CriteriaBuilder criteriaBuilder, SortRequest sortRequest);
    public abstract <T, V> Order buildOrder(Join<T, V> join, CriteriaBuilder criteriaBuilder, SortRequest sortRequest);
}
