package com.epam.esm.dao.search;

import com.epam.esm.exception.InvalidRequestSortParamValueException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Log4j2
class SortFactoryImpl implements SortFactory {

  private static final Map<String, String> sortQueryFragments = new HashMap<>();

  static {
    sortQueryFragments.put("DATE_ASC", " ORDER BY createDate ASC");
    sortQueryFragments.put("DATE_DESC", " ORDER BY createDate DESC");
    sortQueryFragments.put("NAME_ASC", " ORDER BY gift_certificate.name ASC");
    sortQueryFragments.put("NAME_DESC", " ORDER BY gift_certificate.name DESC");
  }

  @Override
  public String provideSortQueryFragment(String sort) {
    log.debug("Providing sort SQL query fragment for request - {}", sort);
    String sortQueryFragment = sortQueryFragments.get(sort.toUpperCase());
    if (sortQueryFragment == null) {
      log.error("Incorrect sort request {}", sort);
      throw new InvalidRequestSortParamValueException(sort, getActualSortRequests());
    }
    return sortQueryFragment;
  }

  @Override
  public void addNewSortQueryFragment(String sortRequest, String sortQueryFragment) {
    log.debug(
        "Adding for the request - {} the new sort SQL query fragment - {}",
        sortRequest,
        sortQueryFragment);
    sortQueryFragments.put(sortRequest.toUpperCase(), sortQueryFragment);
  }

  private String getActualSortRequests() {
    Set<String> keySet = sortQueryFragments.keySet();
    StringBuilder builder = new StringBuilder();
    keySet.forEach(key -> builder.append(key).append(", "));
    builder.delete(builder.lastIndexOf(", "), builder.lastIndexOf(", ") + 2);
    return builder.toString();
  }
}
