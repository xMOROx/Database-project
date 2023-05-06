package org.agh.edu.pl.carrentalrestapi.utils.enums;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.agh.edu.pl.carrentalrestapi.utils.FilterRequest;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public enum Operator {
    EQUALS {
        @Override
        public <T> Predicate build(Root<T> root, CriteriaBuilder criteriaBuilder, FilterRequest filterRequest, Predicate predicate) {
            Object value = filterRequest.getFieldType().parse(filterRequest.getValue().toString());
            Expression<?> expression = root.get(filterRequest.getKey());
            return criteriaBuilder.and(criteriaBuilder.equal(expression, value), predicate);
        }
    },
    NOT_EQUALS {
        @Override
        public <T> Predicate build(Root<T> root, CriteriaBuilder criteriaBuilder, FilterRequest filterRequest, Predicate predicate) {
            Object value = filterRequest.getFieldType().parse(filterRequest.getValue().toString());
            Expression<?> expression = root.get(filterRequest.getKey());
            return criteriaBuilder.and(criteriaBuilder.notEqual(expression, value), predicate);
        }
    },
    GREATER_THAN {
        @Override
        public <T> Predicate build(Root<T> root, CriteriaBuilder criteriaBuilder, FilterRequest filterRequest, Predicate predicate) {
            Object value = filterRequest.getFieldType().parse(filterRequest.getValue().toString());

            if (filterRequest.getFieldType() == FieldType.DATE) {
                LocalDateTime date = (LocalDateTime) value;
                Expression<LocalDateTime> expression = root.get(filterRequest.getKey());
                return criteriaBuilder.and(criteriaBuilder.greaterThan(expression, date), predicate);
            }

            if (filterRequest.getFieldType() != FieldType.CHAR && filterRequest.getFieldType() != FieldType.BOOLEAN) {
                Number number = (Number) value;
                Expression<Number> expression = root.get(filterRequest.getKey());
                return criteriaBuilder.and(criteriaBuilder.gt(expression, number), predicate);
            }

            log.info("Can not use between for {} field type.", filterRequest.getFieldType());

            return predicate;
        }
    },
    GREATER_THAN_OR_EQUALS {
        @Override
        public <T> Predicate build(Root<T> root, CriteriaBuilder criteriaBuilder, FilterRequest filterRequest, Predicate predicate) {
            Object value = filterRequest.getFieldType().parse(filterRequest.getValue().toString());

            if (filterRequest.getFieldType() == FieldType.DATE) {
                LocalDateTime date = (LocalDateTime) value;
                Expression<LocalDateTime> expression = root.get(filterRequest.getKey());
                return criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(expression, date), predicate);
            }

            if (filterRequest.getFieldType() != FieldType.CHAR && filterRequest.getFieldType() != FieldType.BOOLEAN) {
                Number number = (Number) value;
                Expression<Number> expression = root.get(filterRequest.getKey());
                return criteriaBuilder.and(criteriaBuilder.ge(expression, number), predicate);
            }

            log.info("Can not use between for {} field type.", filterRequest.getFieldType());

            return predicate;
        }
    },
    LESS_THAN {
        @Override
        public <T> Predicate build(Root<T> root, CriteriaBuilder criteriaBuilder, FilterRequest filterRequest, Predicate predicate) {
            Object value = filterRequest.getFieldType().parse(filterRequest.getValue().toString());

            if (filterRequest.getFieldType() == FieldType.DATE) {
                LocalDateTime date = (LocalDateTime) value;
                Expression<LocalDateTime> expression = root.get(filterRequest.getKey());
                return criteriaBuilder.and(criteriaBuilder.lessThan(expression, date), predicate);
            }

            if (filterRequest.getFieldType() != FieldType.CHAR && filterRequest.getFieldType() != FieldType.BOOLEAN) {
                Number number = (Number) value;
                Expression<Number> expression = root.get(filterRequest.getKey());
                return criteriaBuilder.and(criteriaBuilder.lt(expression, number), predicate);
            }

            log.info("Can not use between for {} field type.", filterRequest.getFieldType());

            return predicate;
        }
    },
    LESS_THAN_OR_EQUALS{
        @Override
        public <T> Predicate build(Root<T> root, CriteriaBuilder criteriaBuilder, FilterRequest filterRequest, Predicate predicate) {
            Object value = filterRequest.getFieldType().parse(filterRequest.getValue().toString());

            if (filterRequest.getFieldType() == FieldType.DATE) {
                LocalDateTime date = (LocalDateTime) value;
                Expression<LocalDateTime> expression = root.get(filterRequest.getKey());
                return criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(expression, date), predicate);
            }

            if (filterRequest.getFieldType() != FieldType.CHAR && filterRequest.getFieldType() != FieldType.BOOLEAN) {
                Number number = (Number) value;
                Expression<Number> expression = root.get(filterRequest.getKey());
                return criteriaBuilder.and(criteriaBuilder.le(expression, number), predicate);
            }

            log.info("Can not use between for {} field type.", filterRequest.getFieldType());

            return predicate;
        }
    },
    IN {
        @Override
        public <T> Predicate build(Root<T> root, CriteriaBuilder criteriaBuilder, FilterRequest filterRequest, Predicate predicate) {
            List<Object> values = filterRequest.getValues();
            CriteriaBuilder.In<Object> inClause = criteriaBuilder.in(root.get(filterRequest.getKey()));
            for (Object value : values) {
                inClause.value(filterRequest.getFieldType().parse(value.toString()));
            }
            return criteriaBuilder.and(inClause, predicate);
        }
    },
    NOT_IN {
        @Override
        public <T> Predicate build(Root<T> root, CriteriaBuilder criteriaBuilder, FilterRequest filterRequest, Predicate predicate) {
            List<Object> values = filterRequest.getValues();
            CriteriaBuilder.In<Object> inClause = criteriaBuilder.in(root.get(filterRequest.getKey()));
            for (Object value : values) {
                inClause.value(filterRequest.getFieldType().parse(value.toString()));
            }
            return criteriaBuilder.and(criteriaBuilder.not(inClause), predicate);
        }
    },

    BETWEEN {
        @Override
        public <T> Predicate build(Root<T> root, CriteriaBuilder criteriaBuilder, FilterRequest filterRequest, Predicate predicate) {
            Object value = filterRequest.getFieldType().parse(filterRequest.getValue().toString());
            Object valueTo = filterRequest.getFieldType().parse(filterRequest.getValueTo().toString());

            if (filterRequest.getFieldType() == FieldType.DATE) {
                LocalDateTime startDate = (LocalDateTime) value;
                LocalDateTime endDate = (LocalDateTime) valueTo;

                Expression<LocalDateTime> expression = root.get(filterRequest.getKey());

                Predicate lessOrEqual = criteriaBuilder.lessThanOrEqualTo(expression, endDate);
                Predicate greaterOrEqual = criteriaBuilder.greaterThanOrEqualTo(expression, startDate);
                Predicate range = criteriaBuilder.and(lessOrEqual, greaterOrEqual);

                return criteriaBuilder.and(range, predicate);
            }
            if (filterRequest.getFieldType() != FieldType.CHAR && filterRequest.getFieldType() != FieldType.BOOLEAN) {
                Number startNumber = (Number) value;
                Number endNumber = (Number) valueTo;
                Expression<Number> expression = root.get(filterRequest.getKey());

                Predicate lessOrEqual = criteriaBuilder.le(expression, endNumber);
                Predicate greaterOrEqual = criteriaBuilder.ge(expression, startNumber);
                Predicate range = criteriaBuilder.and(lessOrEqual, greaterOrEqual);
                return criteriaBuilder.and(range, predicate);
            }

            log.info("Can not use between for {} field type.", filterRequest.getFieldType());

            return predicate;
        }
    },
    LIKE {
        @Override
        public <T> Predicate build(Root<T> root, CriteriaBuilder criteriaBuilder, FilterRequest filterRequest, Predicate predicate) {
            Expression<String> expression = root.get(filterRequest.getKey());
            String upperCase = filterRequest.getValue().toString().toUpperCase();

            return criteriaBuilder.and(criteriaBuilder.like(expression, "%" + upperCase + "%"), predicate);
        }
    },
    NOT_LIKE {
        @Override
        public <T> Predicate build(Root<T> root, CriteriaBuilder criteriaBuilder, FilterRequest filterRequest, Predicate predicate) {
            Expression<String> expression = root.get(filterRequest.getKey());
            String upperCase = filterRequest.getValue().toString().toUpperCase();

            return criteriaBuilder.and(criteriaBuilder.notLike(expression, "%" + upperCase + "%"), predicate);
        }
    };

    public abstract <T> Predicate build(Root<T> root, CriteriaBuilder criteriaBuilder, FilterRequest filterRequest, Predicate predicate);
}
