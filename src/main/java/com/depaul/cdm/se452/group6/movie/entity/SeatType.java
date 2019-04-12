package com.depaul.cdm.se452.group6.movie.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "seat_types")
public class SeatType implements Serializable {

  @Id
  @GeneratedValue
  private Long id;

  private String type;
}