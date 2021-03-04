package com.backtostock.service;

import com.backtostock.lib.ProductCategory;
import com.backtostock.models.User;
import java.util.LinkedList;

public interface QueueOrganizer {
    LinkedList<User> priorityAdd(LinkedList<User> usersByPriority,
                                 User user,
                                 ProductCategory productCategory);
}
