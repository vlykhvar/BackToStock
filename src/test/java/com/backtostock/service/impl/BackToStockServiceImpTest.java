package com.backtostock.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.backtostock.exception.EmptyEntity;
import com.backtostock.lib.ProductCategory;
import com.backtostock.models.Product;
import com.backtostock.models.User;
import com.backtostock.service.BackToStockService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class BackToStockServiceImpTest {
    private static BackToStockService backToStockService;
    private static Product productMedical;
    private static Product productDigital;
    private static Product productBooks;

    @BeforeAll
    public static void setup() {
        backToStockService = new BackToStockServiceImp();
        productMedical = new Product("1", ProductCategory.MEDICAL);
        productDigital = new Product("2", ProductCategory.DIGITAL);
        productBooks = new Product("3", ProductCategory.BOOKS);
    }

    @BeforeEach
    public void update() {
        backToStockService = new BackToStockServiceImp();
    }

    @DisplayName("Single user")
    @Test
    public void testSingleTest() {
        User user = new User("Bob", false, 20);
        backToStockService.subscribe(user, productBooks);
        assertEquals(List.of(user), backToStockService.subscribedUsers(productBooks));
    }

    @DisplayName("Several users with have different priorities priority")
    @Test
    public void testSeveralUsers () {
        User bob = new User("Bob", false, 20);
        User alice = new User("Alice", false, 50);
        User bogdan = new User("Bogdan", false, 35);
        User gman = new User("Gman", true, 18);
        User tyrion  = new User("Tyrion", true, 39);

        backToStockService.subscribe(gman, productDigital);
        backToStockService.subscribe(bogdan, productDigital);
        backToStockService.subscribe(alice, productDigital);
        backToStockService.subscribe(bob, productDigital);
        backToStockService.subscribe(tyrion, productDigital);

        assertEquals(List.of(gman, tyrion, bogdan, alice, bob), backToStockService.subscribedUsers(productDigital));
    }

    @DisplayName("Queue of medical supplies")
    @Test
    public void testMedicalSupplies () {
        User bob = new User("Bob", false, 20);
        User alice = new User("Alice", false, 50);
        User bogdan = new User("Bogdan", false, 35);
        User gman = new User("Gman", true, 18);
        User tyrion  = new User("Tyrion", true, 39);
        User gandalf  = new User("Gandalf", false, 75);
        User ivan  = new User("Ivan", false, 80);

        backToStockService.subscribe(ivan, productMedical);
        backToStockService.subscribe(bogdan, productMedical);
        backToStockService.subscribe(gman, productMedical);
        backToStockService.subscribe(alice, productMedical);
        backToStockService.subscribe(bob, productMedical);
        backToStockService.subscribe(tyrion, productMedical);
        backToStockService.subscribe(gandalf, productMedical);

        assertEquals(List.of(ivan, gman, tyrion, gandalf, bogdan, alice, bob), backToStockService.subscribedUsers(productMedical));
    }

    @DisplayName("Random Test")
    @Test
    public void testRandom() {
        List<User> priorityQueue = new ArrayList<>();
        List<User> normalQueue = new ArrayList<>();
        Random random = new Random();
        User user = null;
        for (int i = 0; i < 1000; i++) {
            user = new User("bob", random.nextBoolean(), random.nextInt(100));
            backToStockService.subscribe(user, productMedical);
            if (user.isPremium() || user.getAge() >= 70) {
                priorityQueue.add(user);
            } else {
                normalQueue.add(user);
            }
        }
        priorityQueue.addAll(normalQueue);
        assertEquals(priorityQueue, backToStockService.subscribedUsers(productMedical));
    }

    @DisplayName("Exception Test: set null user in queue")
    @Test
    public void testExceptionSetNullUser() {
        Exception exception = assertThrows(EmptyEntity.class, ()
                -> backToStockService.subscribe(null, productMedical));
        String expectedMessage = "User or product does not exist";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @DisplayName("Exception Test: set null product queue")
    @Test
    public void testExceptionSetNullProduct() {
        User user = new User("bob" , false, 20);
        Exception exception = assertThrows(EmptyEntity.class, ()
                -> backToStockService.subscribe(user, null));
        String expectedMessage = "User or product does not exist";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @DisplayName("Exception Test: set null product and user in queue")
    @Test
    public void testExceptionSetNullProductAndUser() {
        Exception exception = assertThrows(EmptyEntity.class, ()
                -> backToStockService.subscribe(null, null));
        String expectedMessage = "User or product does not exist";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @DisplayName("Get null product")
    @Test
    void testExceptionGetNull() {
        Exception exception = assertThrows(EmptyEntity.class, ()
                -> backToStockService.subscribedUsers(null));
        String expectedMessage = "Product does not exist";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
