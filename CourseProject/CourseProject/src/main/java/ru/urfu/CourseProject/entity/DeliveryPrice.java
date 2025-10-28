package ru.urfu.CourseProject.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "delivery_prices")
public class DeliveryPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "city", unique = true)
    private String city;

    @Column(name = "delivery_cost")
    private int deliveryCost;
}
