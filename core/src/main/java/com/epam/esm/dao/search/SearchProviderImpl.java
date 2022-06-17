package com.epam.esm.dao.search;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class SearchProviderImpl implements SearchProvider {

  public static final String BASIC_QUERY =
      "SELECT gift_certificate.id, gift_certificate.name, description, price, duration, createDate, lastUpdateDate FROM gift_certificate";
  public static final String WHERE_TAG_NAME =
      " JOIN gift_certificate_tag ON gift_certificate.id = gift_certificate_tag.gift_certeficate_id JOIN tag ON gift_certificate_tag.tag_id = tag.id WHERE tag.name = ?";
  public static final String AND_CERTIFICATE_NAME = " AND gift_certificate.name = ?";
  public static final String WHERE_CERTIFICATE_NAME = " WHERE gift_certificate.name = ?";
  public static final String AND_DESCRIPTION = " AND description = ?";
  public static final String WHERE_DESCRIPTION = " WHERE description = ?";
  private SortFactory sortFactory;

  @Autowired
  public SearchProviderImpl(SortFactory sortFactory) {
    this.sortFactory = sortFactory;
  }

  @Override
  public String provideQuery(String tagName, String name, String description, String sort) {
    log.debug(
        "Providing query string for prepared statement. Tag name - {}, Certificate name - {}, Certificate description - {}, Sort - {}",
        tagName,
        name,
        description,
        sort);

    StringBuilder builder = new StringBuilder(BASIC_QUERY);

    boolean isWhere = false;

    if (tagName != null) {
      builder.append(WHERE_TAG_NAME);
      isWhere = true;
    }

    if ((name != null) && isWhere) {
      builder.append(AND_CERTIFICATE_NAME);
    } else if (name != null) {
      builder.append(WHERE_CERTIFICATE_NAME);
      isWhere = true;
    }

    if ((description != null) && isWhere) {
      builder.append(AND_DESCRIPTION);
    } else if (description != null) {
      builder.append(WHERE_DESCRIPTION);
    }

    if (sort != null) {
      builder.append(sortFactory.provideSortQueryFragment(sort));
    }

    return builder.toString();
  }

  @Override
  public String[] provideArgs(String tagName, String name, String description) {
    log.debug(
        "Providing arguments for prepared statement. Tag name - {}, Certificate name - {}, Certificate description - {}",
        tagName,
        name,
        description);
    List<String> argsList = new ArrayList<>();
    if (tagName != null) {
      argsList.add(tagName);
    }
    if (name != null) {
      argsList.add(name);
    }
    if (description != null) {
      argsList.add(description);
    }
    return argsList.toArray(new String[0]);
  }
}
