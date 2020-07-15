package com.ez2pay.business.inventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Table(name = "inventory")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Inventory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_name", length = 100, nullable = false)
    private String itemName;

    @Column(name = "item_description", length = 200, nullable = false)
    private String itemDescription;

    @Column(precision = 5, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer quantityAvailable;
}
