package org.example.movieapp.Dto;

import lombok.Data;

import java.util.List;

@Data
public class PageDto<T> {
    private int pageNum;
    private int pageSize;
    private int totalPages;
    private List<T> content;
}
