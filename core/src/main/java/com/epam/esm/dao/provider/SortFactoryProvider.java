package com.epam.esm.dao.provider;

import org.springframework.stereotype.Component;

/**
 * Class for storing and providing short pieces of SQL-query determining the
 * variables for the ordering and its direction (ascending or descending)
 */
@Component
interface SortFactoryProvider {

	/**
	 * Provides the fragment of sort SQL query for a bigger query.
	 *
	 * @param sort
	 *            sort command
	 * @return fragment of sort SQL code
	 */
	String provideSortQueryFragment(String sort);

	/**
	 * Adds new sorting request query fragment for further use.
	 *
	 * @param sortRequest
	 *            request for the fragment of SQL sort code
	 * @param sortQueryFragment
	 *            the fragment of SQL sort code
	 */
	void addNewSortQueryFragment(String sortRequest, String sortQueryFragment);
}
