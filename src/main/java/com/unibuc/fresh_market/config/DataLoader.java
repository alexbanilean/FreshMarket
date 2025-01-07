package com.unibuc.fresh_market.config;

import com.unibuc.fresh_market.domain.*;
import com.unibuc.fresh_market.domain.security.Role;
import com.unibuc.fresh_market.domain.security.User;
import com.unibuc.fresh_market.repository.*;
import com.unibuc.fresh_market.repository.security.RoleRepository;
import com.unibuc.fresh_market.repository.security.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FarmRepository farmRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductFarmRepository productFarmRepository;
    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;
    private final ProductOrderRepository productOrderRepository;
    private final ReviewRepository reviewRepository;

    public DataLoader(RoleRepository roleRepository, UserRepository userRepository,
                      PasswordEncoder passwordEncoder, FarmRepository farmRepository,
                      ProductRepository productRepository, ProductFarmRepository productFarmRepository,
                      CategoryRepository categoryRepository, OrderRepository orderRepository,
                      DeliveryRepository deliveryRepository, ProductOrderRepository productOrderRepository,
                      ReviewRepository reviewRepository
                      ) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.farmRepository = farmRepository;
        this.productRepository = productRepository;
        this.productFarmRepository = productFarmRepository;
        this.categoryRepository = categoryRepository;
        this.orderRepository = orderRepository;
        this.deliveryRepository = deliveryRepository;
        this.productOrderRepository = productOrderRepository;
        this.reviewRepository = reviewRepository;
    }


    private void loadInitialUserData() {
        // Create Roles
        Role adminRole = roleRepository.save(Role.builder().name("ADMIN").build());
        Role userRole = roleRepository.save(Role.builder().name("USER").build());
        Role guestRole = roleRepository.save(Role.builder().name("GUEST").build());

        // Create Users
        User admin = User.builder().username("admin").email("admin@unibuc.com")
                .password(passwordEncoder.encode("1234")).role(adminRole).build();
        User user1 = User.builder().username("user1").email("user1@unibuc.com")
                .password(passwordEncoder.encode("1234")).role(userRole).build();
        User user2 = User.builder().username("user2").email("user2@unibuc.com")
                .password(passwordEncoder.encode("1234")).role(userRole).build();
        User user3 = User.builder().username("user3").email("user3@unibuc.com")
                .password(passwordEncoder.encode("1234")).role(userRole).build();
        User guest = User.builder().username("guest").email("guest@unibuc.com")
                .password(passwordEncoder.encode("1234")).role(guestRole).build();

        userRepository.deleteAll();
        userRepository.saveAll(List.of(admin, user1, user2, user3, guest));

        // Create Farms
        Farm user1Farm = Farm.builder().name("User1 Farm").address("123 Farmer's Lane").user(user1).build();
        Farm user2Farm = Farm.builder().name("User2 Farm").address("456 Grower's Drive").user(user2).build();
        Farm user3Farm = Farm.builder().name("User3 Farm").address("789 Harvest Blvd").user(user3).build();

        farmRepository.saveAll(List.of(user1Farm, user2Farm, user3Farm));

        // Create Categories
        Category fruits = categoryRepository.save(Category.builder().name("Fruits").description("Fresh fruits").build());
        Category vegetables = categoryRepository.save(Category.builder().name("Vegetables").description("Green vegetables").build());
        Category dairy = categoryRepository.save(Category.builder().name("Dairy").description("Dairy products").build());

        // Create Products
        Product apples = productRepository.save(Product.builder().name("Apples").description("Red Apples").price(3.0).category(fruits).build());
        Product carrots = productRepository.save(Product.builder().name("Carrots").description("Organic Carrots").price(2.0).category(vegetables).build());
        Product milk = productRepository.save(Product.builder().name("Milk").description("Fresh Cow Milk").price(1.5).category(dairy).build());

        // Create ProductFarm relations
        ProductFarm farm1Apples = ProductFarm.builder().farm(user1Farm).product(apples).quantity(100).notes("Harvested recently").build();
        ProductFarm farm2Carrots = ProductFarm.builder().farm(user2Farm).product(carrots).quantity(50).notes("Organic batch").build();
        ProductFarm farm3Milk = ProductFarm.builder().farm(user3Farm).product(milk).quantity(200).notes("Delivered daily").build();

        productFarmRepository.saveAll(List.of(farm1Apples, farm2Carrots, farm3Milk));

        // Create Orders
        Order order1 = Order.builder()
                .status("Pending")
                .totalAmount(30.0)
                .createdAt(new Date())
                .user(user1)
                .farm(user1Farm)
                .build();
        Order order2 = Order.builder()
                .status("Shipped")
                .totalAmount(50.0)
                .createdAt(new Date())
                .user(user2)
                .farm(user2Farm)
                .build();
        Order order3 = Order.builder()
                .status("Delivered")
                .totalAmount(70.0)
                .createdAt(new Date())
                .user(user3)
                .farm(user3Farm)
                .build();
        orderRepository.saveAll(List.of(order1, order2, order3));

        // Create Deliveries
        Delivery delivery1 = Delivery.builder().deliveryStatus("In Progress").deliveryDate(new Date()).order(order1).build();
        Delivery delivery2 = Delivery.builder().deliveryStatus("Delivered").deliveryDate(new Date()).order(order2).build();
        Delivery delivery3 = Delivery.builder().deliveryStatus("Delivered").deliveryDate(new Date()).order(order3).build();

        deliveryRepository.saveAll(List.of(delivery1, delivery2, delivery3));

        // Create ProductOrders
        ProductOrder productOrder1 = ProductOrder.builder().order(order1).product(apples).quantity(10).notes("For family").build();
        ProductOrder productOrder2 = ProductOrder.builder().order(order2).product(carrots).quantity(20).notes("Bulk order").build();
        ProductOrder productOrder3 = ProductOrder.builder().order(order3).product(milk).quantity(30).notes("Daily delivery").build();

        productOrderRepository.saveAll(List.of(productOrder1, productOrder2, productOrder3));

        // Create Reviews
        Review review1 = Review.builder().rating(5).content("Great apples!").user(user1).farm(user1Farm).build();
        Review review2 = Review.builder().rating(4).content("Fresh carrots!").user(user2).farm(user2Farm).build();
        Review review3 = Review.builder().rating(3).content("Milk was okay.").user(user3).farm(user3Farm).build();

        reviewRepository.saveAll(List.of(review1, review2, review3));
    }

    @Override
    public void run(String... args) throws Exception {
        loadInitialUserData();
    }
}
