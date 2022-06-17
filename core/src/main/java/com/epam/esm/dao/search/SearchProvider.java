package com.epam.esm.dao.search;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Basic interface for search methods used to provide complex Certificate search by various
 * parameters
 */
@Component
public interface SearchProvider {

  /**
   * Constructs an SQL search query for a prepared statement to find all Certificates corresponding
   * to the specified parameters.
   *
   * <p>All params are optional and can be used in conjunction. If the parameter is not used, null
   * value is passed instead.
   *
   * @param tagName {@code Tag's} name
   * @param name {@code Certificate's} name
   * @param description {@code Certificate's} description
   * @param sort sort by some {@code Certificate's} parameter
   * @return SQL query for prepared statement
   */
  String provideQuery(String tagName, String name, String description, String sort);

  /**
   * Provides String array with args for prepared statement. Args are search parameters.
   *
   * @param tagName {@code Tag's} name
   * @param name {@code Certificate's} name
   * @param description {@code Certificate's} description
   * @return String array with args for prepared statement
   */
  @Nullable
  String[] provideArgs(String tagName, String name, String description);
}
