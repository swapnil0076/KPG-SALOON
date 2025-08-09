package com.app.salon_service.Exception;

public class SalonNotExists extends RuntimeException {
    public SalonNotExists(String message) {
        super(message);
    }
}
