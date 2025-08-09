package com.app.booking_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public String HomeControllerHandler(){
        return "Booking micro services for salons" ;
    }


}
