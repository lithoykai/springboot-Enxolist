package com.enxolist.enxolist.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.enxolist.enxolist.model.Request.ProductRequest;
import com.enxolist.enxolist.model.Response.ProductResponse;
import com.enxolist.enxolist.persistence.entity.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

@Service
public interface IProductService {
    
    ProductResponse create(ProductRequest request);

    List<Product> listProducts(String idUser);
    
    ProductResponse getUniqueProduct(String id);

    void deleteProduct(String id);

    ProductResponse imagePatch(JsonPatch patch, Product targetProduct) throws JsonPatchException, JsonProcessingException;
}
