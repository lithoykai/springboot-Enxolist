package com.enxolist.enxolist.model.Request;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;

import lombok.Data;

@Data
public class ProductRequest {
        
    private String name;
    private int price;
    private String urlLink;
    private Boolean wasBought;
    private String image;
    @CreatedDate
    private Date createdAt;
    private String idUser;

}
