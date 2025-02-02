package com.divya29.productservice.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SortParam {
    private String paramName;
    // sortType can also by enum SortType
    private SortType sortType;
}
