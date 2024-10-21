package com.karizma.onlineshopping.iam.entity;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;


@Embeddable
public class Place {

    @ManyToOne(targetEntity = Province.class)
    private Province province;

    @ManyToOne(targetEntity = City.class)
    private City city;

}
