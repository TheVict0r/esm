package com.epam.esm.dao.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Entity class for creating {@code Tag} objects. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag implements Serializable {

  private long id;
  private String name;

  public Tag(String name) {
    this.name = name;
  }
}
