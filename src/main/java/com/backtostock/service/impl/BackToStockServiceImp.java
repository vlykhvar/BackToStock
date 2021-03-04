package com.backtostock.service.impl;

import com.backtostock.exception.EmptyEntity;
import com.backtostock.models.Product;
import com.backtostock.models.User;
import com.backtostock.service.BackToStockService;
import com.backtostock.service.QueueOrganizer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class BackToStockServiceImp implements BackToStockService {
    private final HashMap<Product, LinkedList<User>> userQueueByProduct;
    private final QueueOrganizer queueOrganizerImpl;

    public BackToStockServiceImp() {
        this.queueOrganizerImpl = new QueueOrganizerImpl();
        userQueueByProduct = new HashMap<>();
    }

    @Override
    public void subscribe(User user, Product product) {
        if (user == null || product == null) {
            throw new EmptyEntity("User or product does not exist");
        }
        LinkedList<User> usersQueue = initializeList(userQueueByProduct.get(product));
        usersQueue = queueOrganizerImpl.priorityAdd(usersQueue, user, product.getCategory());
        userQueueByProduct.put(product, usersQueue);
    }

    @Override
    public List<User> subscribedUsers(Product product) {
        if (product == null) {
            throw new EmptyEntity("Product does not exist");
        }
        return userQueueByProduct.get(product);
    }

    private LinkedList<User> initializeList(LinkedList<User> list) {
        LinkedList<User> usersList = list;
        if (usersList == null) {
            usersList = new LinkedList<>();
        }
        return usersList;
    }
}
