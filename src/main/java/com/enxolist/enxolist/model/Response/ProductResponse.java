package com.enxolist.enxolist.model.Response;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import com.enxolist.enxolist.model.Request.ProductRequest;

import lombok.Data;

@Data
public class ProductResponse {
    
    @Id
    private String id;
    
    private String name;
    private int price;
    private String urlLink;
    private Boolean wasBought;
    private String image;
    @CreatedDate
    private Date createdAt;
    private String idUser;


    public ProductResponse(){}

    public ProductResponse(
        ProductRequest request
    ){ 
        this.name = request.getName();
        this.price = request.getPrice();
        this.image = request.getImage();
        this.urlLink = request.getUrlLink();
        this.wasBought = request.getWasBought();
        this.idUser = request.getIdUser();
        this.createdAt = request.getCreatedAt();
    }
}
