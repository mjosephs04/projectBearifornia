package com.example.springboot;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/rooms")
public class RoomController {

    @GetMapping("/nature-retreat")
    public Listing getNatureRetreat(){
        return new Listing("Nature Retreat", 249.99, "fhjkdhfjkhdsflkjsd\nfhsdjf\nhkjhj");
    }

    @GetMapping("/urban-elegance")
    public Listing getUrbanElegance(){
        return new Listing("Urban Elegance", 249.99, "fhjkdhfjkhdsflkjsd\nfhsdjf\nhkjhj");
    }

    @GetMapping("/vintage")
    public Listing getVintage(){
        return new Listing("Vintage", 349.99, "fhjkdhfjkhdsflkjsd\nfhsdjf\nhkjhj", "https://i.pinimg.com/originals/bb/ce/4b/bbce4b1fb3216aa36d02005082896338.jpg");
    }

}
