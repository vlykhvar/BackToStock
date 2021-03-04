package com.backtostock.service.impl;

import com.backtostock.lib.ProductCategory;
import com.backtostock.models.User;
import com.backtostock.service.QueueOrganizer;
import java.util.LinkedList;

public class QueueOrganizerImpl implements QueueOrganizer {
    private static final short PRIORITY_AGE = 70;
    private int presentPriority;

    public QueueOrganizerImpl() {
        presentPriority = 0;
    }

    public LinkedList<User> priorityAdd(LinkedList<User> usersByPriority,
                                        User user,
                                        ProductCategory productCategory) {
        switch (productCategory) {
            case MEDICAL:
                if (!user.isPremium() && user.getAge() < PRIORITY_AGE) {
                    usersByPriority.addLast(user);
                    return usersByPriority;
                }
                break;
            case BOOKS:
            case DIGITAL:
            default:
                if (!user.isPremium()) {
                    usersByPriority.addLast(user);
                    return usersByPriority;
                }
                break;
        }
        usersByPriority.add(presentPriority, user);
        presentPriority++;
        return usersByPriority;
    }
}
