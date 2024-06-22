package org.example.movieapp.Dto;

import lombok.Data;

@Data
public class RequestMailDto {
    private String subject;
    private String body;
    private String to;
}
