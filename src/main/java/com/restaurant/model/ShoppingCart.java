package com.restaurant.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shopping_carts")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 一个顾客对应一个购物车
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // 购物车中的商品项
    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItem> cartItems = new ArrayList<>();

    // 构造器
    public ShoppingCart() {}

    public ShoppingCart(Customer customer) {
        this.customer = customer;
    }

    // Getter 和 Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public List<CartItem> getCartItems() { return cartItems; }
    public void setCartItems(List<CartItem> cartItems) { this.cartItems = cartItems; }

    // 业务方法：计算总价
    public BigDecimal getTotalPrice() {
        return cartItems.stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // 添加商品到购物车
    public void addItem(Dish dish, int quantity) {
        // 检查是否已存在该商品
        for (CartItem item : cartItems) {
            if (item.getDish().getId().equals(dish.getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        // 新商品
        CartItem newItem = new CartItem(dish, quantity, this);
        cartItems.add(newItem);
    }

    // 清空购物车
    public void clear() {
        cartItems.clear();
    }
}