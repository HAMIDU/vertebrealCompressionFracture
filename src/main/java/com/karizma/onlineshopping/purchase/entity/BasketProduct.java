package com.karizma.onlineshopping.purchase.entity;

import com.karizma.onlineshopping.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "BasketProduct")
@Entity
public class BasketProduct implements Serializable {

    private static final long serialVersionUID = -500096635852035094L;

    @EmbeddedId
    @AttributeOverride(name = "basketId", column = @Column(name = "basketId", nullable = false, columnDefinition = "BIGINT"))
    @AttributeOverride(name = "productId", column = @Column(name = "productId", nullable = false, columnDefinition = "BIGINT"))
    private BasketProductId basketProductId;

    @ManyToOne
    @JoinColumn(name = "basketId", insertable = false, updatable = false)
    private Basket basket;


    @JoinColumn(name = "productId", insertable = false, updatable = false)
    @ManyToOne
    private Product product;

    @NotEmpty
    private Long totalCount;

    @NotEmpty
    private Long totalPrice;

}
