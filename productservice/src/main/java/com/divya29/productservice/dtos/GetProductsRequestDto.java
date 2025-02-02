package com.divya29.productservice.dtos;

import java.util.List;

import com.divya29.productservice.models.SortParam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetProductsRequestDto {
    private String query;
    private int numOfResults;
    private int offset;
    private List<SortParam> sortParamsList;
}
