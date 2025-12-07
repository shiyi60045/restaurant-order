package com.restaurant.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "dishes")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;  // 菜品名称

    private String description;  // 菜品描述

    @Column(nullable = false)
    private BigDecimal price;  // 价格

    private String imageUrl;  // 菜品图片URL
    private Integer stock;    // 库存数量
    private Boolean available = true;  // 是否可用
    private Integer sortOrder;  // 排序号

    // 关联分类
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    // 关联商家
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    // 构造器
    public Dish() {}

    public Dish(String name, String description, BigDecimal price, Merchant merchant) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.merchant = merchant;
    }

    // Getter 和 Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }

    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public Merchant getMerchant() { return merchant; }
    public void setMerchant(Merchant merchant) { this.merchant = merchant; }

    // 业务方法
    public boolean isInStock() {
        return available && (stock == null || stock > 0);
    }
}