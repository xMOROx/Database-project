package org.agh.edu.pl.carrentalrestapi.utils.enums;

import lombok.extern.slf4j.Slf4j;

import javax.swing.text.DateFormatter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public enum FieldType {
    BOOLEAN {
        public Object parse(String value) {
            return Boolean.valueOf(value);
        }
    },
    INTEGER {
        public Object parse(String value) {
            return Integer.valueOf(value);
        }
    },
    CHAR {
        public Object parse(String value) {
            return value.charAt(0);
        }
    },
    DOUBLE {
        public Object parse(String value) {
            return Double.valueOf(value);
        }
    },
    STRING {
        public Object parse(String value) {
            return value;
        }
    },
    LONG {
        public Object parse(String value) {
            return Long.valueOf(value);
        }
    },
    DATE {
        public Object parse(String value) {
            Object date = null;
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                date = LocalDateTime.parse(value, formatter);
            } catch (Exception e) {
                log.error("Error while parsing date: " + e.getMessage());
            }
            return date;
        }
    },
    BIGDECIMAL {
        public Object parse(String value) {
            return BigDecimal.valueOf(Double.parseDouble(value));
        }
    };


    public abstract Object parse(String value);
}
