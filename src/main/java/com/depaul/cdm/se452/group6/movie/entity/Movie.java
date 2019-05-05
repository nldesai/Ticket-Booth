package com.depaul.cdm.se452.group6.movie.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table (
    name = "movies",
    uniqueConstraints = { @UniqueConstraint(columnNames = {"name", "year"} ) }
)
public class Movie implements Serializable {
  @Id
  @GeneratedValue
  private Long id;

  private String name;
  private Double runtime;
  private String rating;
  private String genre;
  private Long year;

  @OneToMany(
      mappedBy = "movieID",
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY
  )
  @ToString.Exclude
  private List<Theater> theaters;
}

