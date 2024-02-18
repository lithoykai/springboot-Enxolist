package com.enxolist.enxolist.domain.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.enxolist.enxolist.data.model.Request.ProductRequest;
import com.enxolist.enxolist.data.model.Response.ProductResponse;
import com.enxolist.enxolist.data.model.Response.TotalValue;
import com.enxolist.enxolist.domain.persistence.entity.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

@Service
public interface IProductService {
    
    ProductResponse create(ProductRequest request);

    List<Product> listProducts(String idUser);
    
    ProductResponse getUniqueProduct(String id);

    void deleteProduct(String id);

    List<Product> listProductsForCategory(String idUser, int category);

    List<TotalValue> listAllPriceByCategory(String idUser);

    ProductResponse imagePatch(JsonPatch patch, Product targetProduct) throws JsonPatchException, JsonProcessingException;
}
