package org.agh.edu.pl.carrentalrestapi.utils.enums;

public enum BookingStateCodeConstants {
    AVI,
    RES,
    RET,
    REN,
    CAN,
    UNA;

    public String toString() {
        return this.name();
    }
}
