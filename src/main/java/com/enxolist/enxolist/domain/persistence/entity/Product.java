package com.enxolist.enxolist.domain.persistence.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.enxolist.enxolist.data.model.Request.ProductRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    @Id
    private String id;
    
    private String name;
    private double price;
    private int category;
    private String urlLink;
    private Boolean wasBought;
    private String image;
    @CreatedDate
    private Date createdAt;
    private String idUser;

    public Product(
        ProductRequest request
    ){ 
        this.name = request.getName();
        this.price = request.getPrice();
        this.image = request.getImage();
        this.category = request.getCategory();
        this.urlLink = request.getUrlLink();
        this.wasBought = request.getWasBought();
        this.idUser = request.getIdUser();
        this.createdAt = request.getCreatedAt();
    }


}
