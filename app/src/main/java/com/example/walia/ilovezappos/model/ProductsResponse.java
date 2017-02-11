package com.example.walia.ilovezappos.model;

import java.util.List;

public class ProductsResponse {

    private final String originalTerm;
    private final List<Product> results;

    public ProductsResponse(String originalTerm, List<Product> results) {
        this.originalTerm = originalTerm;
        this.results = results;
    }

    public String getOriginalTerm() {
        return originalTerm;
    }

    public List<Product> getResults() {
        return results;
    }
}
