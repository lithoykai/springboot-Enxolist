package com.enxolist.enxolist.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.enxolist.enxolist.model.Request.ProductRequest;
import com.enxolist.enxolist.model.Response.ProductResponse;
import com.enxolist.enxolist.persistence.entity.Product;
import com.enxolist.enxolist.persistence.repositories.IProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

@Service
public class ProductService implements IProductService{

    @Autowired
    private IProductRepository repository;
    
    @Autowired
    private MongoTemplate mongoTemplate;


    private ProductResponse createProductResponse(Product product){ 
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        response.setImage(product.getImage());
        response.setCategory(product.getCategory());
        response.setUrlLink(product.getUrlLink());
        response.setWasBought(product.getWasBought());
        response.setIdUser(product.getIdUser());
        response.setCreatedAt(product.getCreatedAt());
        return response;
    }

    @Override
    public ProductResponse create(ProductRequest request) {
        Product product = new Product(request);
        this.repository.save(product);
       return createProductResponse(product);
    }

    @Override
    public List<Product> listProducts(String idUser) {

        List<Product> productsUser = this.repository.findByIdUser(idUser);
        return productsUser;
    }

    @Override
    public void deleteProduct(String id) {
        repository.deleteById(id);
    }


    @Override
    public ProductResponse getUniqueProduct(String id) {
        Product product = this.repository.findById(id).orElse(null);
        return createProductResponse(product);
    }

    @Override
    public ProductResponse imagePatch(JsonPatch patch, Product targetProduct) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = patch.apply(objectMapper.convertValue(targetProduct, JsonNode.class));
        return objectMapper.treeToValue(patched, ProductResponse.class);
    }

        @Override
        public List<Product> listProductsForCategory(String idUser, int category) {
            List<Product> productsUser = this.repository.findByIdUser(idUser);
            
            System.err.println(category);
             List<Product> response = new ArrayList<>();
             for (Product product : productsUser) {
                if (product.getCategory() == category) {
                    response.add(product);
                }} 
                return response;
    
        }


    
}
