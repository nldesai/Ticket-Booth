package com.depaul.cdm.se452.group6.movie.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "user_types")
public class UserType implements Serializable {

    //Id, autogenerated
    @Id
    @GeneratedValue
    private Long id;

    //User Type
    private String type;

}