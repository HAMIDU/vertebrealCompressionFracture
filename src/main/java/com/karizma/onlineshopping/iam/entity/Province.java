package com.karizma.onlineshopping.iam.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "Province")
public class Province implements Serializable {
    private static final long serialVersionUID = 3832626162173359411L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false, name = "name")
    private String title;

}
