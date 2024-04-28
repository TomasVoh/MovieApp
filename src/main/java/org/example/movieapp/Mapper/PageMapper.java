package org.example.movieapp.Mapper;

import org.example.movieapp.Dto.PageDto;
import org.springframework.data.domain.Page;

import java.util.List;

public class PageMapper {
    public static <T> PageDto<T> pageMapper(Page<T> page) {
        PageDto<T> pageDto = new PageDto<>();
        pageDto.setTotalPages(page.getTotalPages());
        pageDto.setPageNum(page.getNumber());
        pageDto.setPageSize(page.getSize());
        pageDto.setContent(page.getContent());
        return pageDto;
    }
}
