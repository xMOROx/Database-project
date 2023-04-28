package org.agh.edu.pl.carrentalrestapi.utils;

public enum BookingStatusCode {
    AVI,
    RET,
    REN,
    CAN,
    UNA;

    public String toString() {
        return this.name();
    }
}
