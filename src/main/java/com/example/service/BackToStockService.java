package com.example.service;

import com.example.models.User;
import com.example.models.Product;

import java.util.List;

public interface BackToStockService {
    void subscribe(User user, Product product);

    List<User> subscribedUsers(Product product);
}
