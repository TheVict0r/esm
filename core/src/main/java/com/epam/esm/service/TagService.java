package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service operations with the {@code Tag}
 */
@Service
public interface TagService extends BasicService<TagDto> {

    /**
     * Provides all existing {@code TagDto} objects.
     *
     * @return the list containing all {@code TagsDto} objects
     * converted from all {@code Tags} existing in the datasource
     */
    List<TagDto> searchAll();

}