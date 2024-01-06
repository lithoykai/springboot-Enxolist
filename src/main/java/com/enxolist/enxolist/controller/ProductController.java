package com.enxolist.enxolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enxolist.enxolist.model.Request.ProductRequest;
import com.enxolist.enxolist.model.Response.ProductResponse;
import com.enxolist.enxolist.persistence.entity.Product;
import com.enxolist.enxolist.persistence.repositories.IProductRepository;
import com.enxolist.enxolist.services.ProductService;
import com.enxolist.enxolist.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;




@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;
    @Autowired
    private IProductRepository repository;
    
    @PostMapping("/create")
    public ResponseEntity<ProductResponse> create(@RequestBody @Valid ProductRequest request, HttpServletRequest httpRequest) {
        var idUser = httpRequest.getAttribute("idUser");
        
        request.setIdUser((String) idUser);
        return ResponseEntity.ok(this.service.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity getProductsUser(@PathVariable String id, HttpServletRequest request) {
        var idUser = request.getAttribute("idUser").toString();
        if(idUser.equals(id)){ 
            return ResponseEntity.ok(this.service.listProducts((String) idUser));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User without permission to get these products.");
    
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getUniqueProduct(@PathVariable String id, HttpServletRequest request) {
        var idUser = request.getAttribute("idUser").toString();
        ProductResponse response = service.getUniqueProduct(id);
        if(response.getIdUser().equals(idUser)){ 
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User without permission to get the product.");
    }
    

    
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody ProductRequest productRequest, HttpServletRequest request, @PathVariable String id){
        var idUser = request.getAttribute("idUser");
        var product = this.repository.findById(id).orElse(null);
        System.out.println(id);
        if(product == null){ 
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found.");
        }

        if(!product.getIdUser().equals(idUser)){ 
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User without permission to change this product.");
        }

        Utils.copyNonNullProperties(productRequest,product);

        return ResponseEntity.ok(this.repository.save(product));
    }
    
    @DeleteMapping("/del/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id) {
        this.service.deleteProduct(id);
        return ResponseEntity.ok("Produto deletado com sucesso!");
    }


    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponse> imagePatch(@PathVariable String id, @RequestBody JsonPatch patch) {
        try{ 
            Product product = this.repository.findById((String) id).orElse(null);
            ProductResponse productPatched = this.service.imagePatch(patch, product);
            return ResponseEntity.ok(productPatched);
        } catch (JsonPatchException | JsonProcessingException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        
        }
    }
}
