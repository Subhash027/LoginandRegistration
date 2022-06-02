package com.parkingmanagementsystem.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponeDetails {

    private LocalDateTime timestamp;
    private String message;
    private String description;
    HttpStatus status;


}
