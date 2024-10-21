package com.karizma.onlineshopping.iam.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
@Data
@Table(name = "City")
public class City  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false , name = "name")
    private String title;

    @NotEmpty
    @ManyToOne(optional = false)// required
    private Province province;

}
