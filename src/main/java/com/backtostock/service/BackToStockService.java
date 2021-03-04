package com.backtostock.service;

import com.backtostock.models.Product;
import com.backtostock.models.User;
import java.util.List;

public interface BackToStockService {
    void subscribe(User user, Product product);

    List<User> subscribedUsers(Product product);
}
