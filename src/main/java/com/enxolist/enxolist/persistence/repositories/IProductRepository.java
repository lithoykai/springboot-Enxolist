package com.enxolist.enxolist.persistence.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.enxolist.enxolist.persistence.entity.Product;


@Repository
public interface IProductRepository extends MongoRepository<Product, String>{
    
    List<Product> findByIdUser(String idUser);

    List<Product> findByCategory(int category);

}
    