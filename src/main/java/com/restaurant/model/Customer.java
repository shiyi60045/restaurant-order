package com.restaurant.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer extends User {

    private String nickname;
    private String avatarUrl;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ShoppingCart shoppingCart;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    public Customer() {
        this.setUserType(com.restaurant.enums.UserType.CUSTOMER);
    }

    public Customer(String username, String password) {
        super(username, password, com.restaurant.enums.UserType.CUSTOMER);
    }

    // Getter 和 Setter
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    public ShoppingCart getShoppingCart() { return shoppingCart; }
    public void setShoppingCart(ShoppingCart shoppingCart) { this.shoppingCart = shoppingCart; }

    public List<Order> getOrders() { return orders; }
    public void setOrders(List<Order> orders) { this.orders = orders; }

    // 业务方法
    public void browseMenu() {
        System.out.println("顾客 " + getUsername() + " 正在浏览菜单");
    }

    public void addToCart(Dish dish, int quantity) {
        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart(this);
        }
        // 具体的添加逻辑将在ShoppingCart类中实现
    }

    public ShoppingCart viewCart() {
        return this.shoppingCart;
    }
}