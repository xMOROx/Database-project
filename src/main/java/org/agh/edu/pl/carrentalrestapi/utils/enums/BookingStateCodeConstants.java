package org.agh.edu.pl.carrentalrestapi.utils.enums;

public enum BookingStateCodeConstants {
    RES,
    RET,
    REN,
    CAN;

    public String toString() {
        return this.name();
    }
}
