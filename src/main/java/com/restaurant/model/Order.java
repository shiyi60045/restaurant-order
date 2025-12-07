package com.restaurant.model;

import com.restaurant.enums.OrderStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String orderNumber;  // 订单号

    @Column(nullable = false)
    private BigDecimal totalAmount;  // 订单总金额

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.PENDING;  // 订单状态

    private String customerNotes;  // 顾客备注
    private String merchantNotes;  // 商家备注

    @Column(nullable = false)
    private LocalDateTime orderTime;  // 下单时间

    private LocalDateTime confirmTime;  // 确认时间
    private LocalDateTime completeTime; // 完成时间

    // 关联顾客
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // 关联商家
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    // 订单项列表
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    // 构造器
    public Order() {
        this.orderTime = LocalDateTime.now();
        this.orderNumber = generateOrderNumber();
    }

    public Order(Customer customer, Merchant merchant) {
        this();
        this.customer = customer;
        this.merchant = merchant;
        this.totalAmount = BigDecimal.ZERO;
    }

    // Getter 和 Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public String getCustomerNotes() { return customerNotes; }
    public void setCustomerNotes(String customerNotes) { this.customerNotes = customerNotes; }

    public String getMerchantNotes() { return merchantNotes; }
    public void setMerchantNotes(String merchantNotes) { this.merchantNotes = merchantNotes; }

    public LocalDateTime getOrderTime() { return orderTime; }
    public void setOrderTime(LocalDateTime orderTime) { this.orderTime = orderTime; }

    public LocalDateTime getConfirmTime() { return confirmTime; }
    public void setConfirmTime(LocalDateTime confirmTime) { this.confirmTime = confirmTime; }

    public LocalDateTime getCompleteTime() { return completeTime; }
    public void setCompleteTime(LocalDateTime completeTime) { this.completeTime = completeTime; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public Merchant getMerchant() { return merchant; }
    public void setMerchant(Merchant merchant) { this.merchant = merchant; }

    public List<OrderItem> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; }

    // 业务方法：生成订单号
    private String generateOrderNumber() {
        return "ORD" + System.currentTimeMillis();
    }

    // 添加订单项
    public void addOrderItem(OrderItem orderItem) {
        orderItem.setOrder(this);
        orderItems.add(orderItem);
        calculateTotalAmount();
    }

    // 计算订单总金额
    public void calculateTotalAmount() {
        this.totalAmount = orderItems.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // 确认订单
    public void confirm() {
        this.status = OrderStatus.CONFIRMED;
        this.confirmTime = LocalDateTime.now();
    }

    // 完成订单
    public void complete() {
        this.status = OrderStatus.COMPLETED;
        this.completeTime = LocalDateTime.now();
    }

    // 取消订单
    public void cancel() {
        this.status = OrderStatus.CANCELLED;
    }

    // 检查订单是否可以修改
    public boolean canBeModified() {
        return status == OrderStatus.PENDING;
    }
}