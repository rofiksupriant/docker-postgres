package com.rofik.springpostgres.domain.dao;

import com.rofik.springpostgres.domain.common.BaseDao;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @ManyToOne
    private Category category;

    @Column(name = "model_year", nullable = false)
    private String modelYear;

    @Column(name = "price", nullable = false)
    private Integer price;
}
