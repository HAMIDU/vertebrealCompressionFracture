package com.karizma.onlineshopping.purchase.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class BasketProductId implements Serializable {

    private static final long serialVersionUID = -5411096635852235094L;

    @NotEmpty
    private Long basketId;

    @NotEmpty
    private Long productId;


}
