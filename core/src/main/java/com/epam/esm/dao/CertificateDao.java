package com.epam.esm.dao;

import com.epam.esm.dao.entity.Certificate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data access operations with the {@code Certificate}.
 */
@Repository
public interface CertificateDao extends BasicDao<Certificate> {

    /**
     * Searches {@code Certificates} with tags (all params are optional and can be used in conjunction).
     *
     * @param tagName     {@code Tag's} name
     * @param name        {@code Certificate's} name
     * @param description {@code Certificate's} description
     * @param sort        sort by some {@code Certificate's} parameter
     *                    At the moment this param accepts DATE_ASC, DATE_DESC, NAME_ASC, NAME_DESC sorting
     * @return The list with found {@code Certificates}, or empty list if nothing was found
     */
    List<Certificate> search(String tagName, String name, String description, String sort);

    /**
     * Retrieves all {@code Certificates} which contain the {@code Tag}.
     *
     * @param tagId <b>ID</b> of the {@code Tag}
     * @return all {@code Certificates} which contain the {@code Tag}
     */
    List<Certificate> retrieveCertificatesByTagId(long tagId);

}