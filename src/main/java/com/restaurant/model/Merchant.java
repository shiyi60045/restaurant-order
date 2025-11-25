package com.restaurant.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "merchants")
public class Merchant extends User {

    private String shopName;
    private String address;
    private String shopPhone;

    @OneToMany(mappedBy = "merchant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Dish> dishes = new ArrayList<>();

    @OneToMany(mappedBy = "merchant", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    public Merchant() {
        this.setUserType(com.restaurant.enums.UserType.MERCHANT);
    }

    public Merchant(String username, String password, String shopName) {
        super(username, password, com.restaurant.enums.UserType.MERCHANT);
        this.shopName = shopName;
    }

    // Getter 和 Setter
    public String getShopName() { return shopName; }
    public void setShopName(String shopName) { this.shopName = shopName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getShopPhone() { return shopPhone; }
    public void setShopPhone(String shopPhone) { this.shopPhone = shopPhone; }

    public List<Dish> getDishes() { return dishes; }
    public void setDishes(List<Dish> dishes) { this.dishes = dishes; }

    public List<Order> getOrders() { return orders; }
    public void setOrders(List<Order> orders) { this.orders = orders; }

    // 业务方法
    public Dish addDish(String name, String description, BigDecimal price) {
        Dish dish = new Dish(name, description, price, this);
        dishes.add(dish);
        return dish;
    }
}