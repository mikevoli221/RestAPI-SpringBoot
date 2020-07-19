package com.ez2pay.business.inventory;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Table(name = "inventory")
@Entity
@Data
public class Inventory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_name", length = 100, nullable = false)
    private String name;

    @Column(name = "item_description", length = 200, nullable = false)
    private String description;

    @Column(precision = 5, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer quantityAvailable;
}
