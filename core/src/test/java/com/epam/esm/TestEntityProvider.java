package com.epam.esm;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.entity.Tag;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class-container with entities and corresponding DTOs for testing.
 *
 * Entities from tag1 to tag 10 and certificate1 to certificate9 are equals to testing database
 */
@Component
@Getter
public class TestEntityProvider {

    private Tag tag1 = new Tag(1L, "Tag 1 test");
    private Tag tag2 = new Tag(2L, "Tag 2 test");
    private Tag tag3 = new Tag(3L, "Tag 3 test");
    private Tag tag4 = new Tag(4L, "Tag 4 test");
    private Tag tag5 = new Tag(5L, "Tag 5 test");
    private Tag tag6 = new Tag(6L, "Tag 6 test");
    private Tag tag7 = new Tag(7L, "Tag 7 test");
    private Tag tag8 = new Tag(8L, "Tag 8 test");
    private Tag tag9 = new Tag(9L, "Tag 9 test");
    private Tag tag10 = new Tag(10L, "Tag 10 test");

    private Tag tagForCreate = new Tag("Tag for create test");
    private Tag tagAfterCreate = new Tag(11L, "Tag for create test");
    private Tag tagForUpdate = new Tag(1L, "Tag for update test");

    private TagDto tag1dto = new TagDto(1L, "Tag 1 test");
    private TagDto tag2dto = new TagDto(2L, "Tag 2 test");
    private TagDto tag3dto = new TagDto(3L, "Tag 3 test");
    private TagDto tag4dto = new TagDto(4L, "Tag 4 test");
    private TagDto tag5dto = new TagDto(5L, "Tag 5 test");
    private TagDto tag6dto = new TagDto(6L, "Tag 6 test");
    private TagDto tag7dto = new TagDto(7L, "Tag 7 test");
    private TagDto tag8dto = new TagDto(8L, "Tag 8 test");
    private TagDto tag9dto = new TagDto(9L, "Tag 9 test");
    private TagDto tag10dto = new TagDto(10L, "Tag 10 test");

    private TagDto tagForCreationDto = new TagDto("Tag for create test");
    private TagDto tagAfterCreationDto = new TagDto(11L, "Tag for create test");
    private TagDto tagForUpdateDto = new TagDto(1L, "Tag for update test");

    private List<Tag> allTagsList = new ArrayList<>(List.of(
            new Tag[]{tag1, tag10, tag2, tag3, tag4, tag5, tag6, tag7, tag8, tag9}));

    private List<TagDto> allTagsDtoList = new ArrayList<>(List.of(
            new TagDto[]{tag1dto, tag10dto, tag2dto, tag3dto, tag4dto, tag5dto, tag6dto, tag7dto, tag8dto, tag9dto}));

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    private LocalDateTime dateTimeNow = LocalDateTime.now();

     /* Certificates as in database */

    private Certificate certificate1 = new Certificate().builder()
            .id(1L)
            .name("name 1 test")
            .description("description 1")
            .price(30)
            .duration(45)
            .createDate(LocalDateTime.parse("2022-04-21 23:14:03.635", formatter))
            .lastUpdateDate(LocalDateTime.parse("2022-04-21 23:14:03.636", formatter))
            .build();

    private Certificate certificate1withTags = new Certificate().builder()
            .id(1L)
            .name("name 1 test")
            .description("description 1")
            .price(30)
            .duration(45)
            .createDate(LocalDateTime.parse("2022-04-21 23:14:03.635", formatter))
            .lastUpdateDate(LocalDateTime.parse("2022-04-21 23:14:03.636", formatter))
            .tags(new HashSet<>(Set.of(tag1, tag2, tag3)))
            .build();

    private Certificate certificate2 = new Certificate().builder()
            .id(2L)
            .name("name 2 test")
            .description("description 2")
            .price(20)
            .duration(20)
            .createDate(LocalDateTime.parse("2022-04-22 23:14:03.635", formatter))
            .lastUpdateDate(LocalDateTime.parse("2022-04-22 23:14:03.636", formatter))
            .build();

    private Certificate certificate2withTags = new Certificate().builder()
            .id(2L)
            .name("name 2 test")
            .description("description 2")
            .price(20)
            .duration(20)
            .createDate(LocalDateTime.parse("2022-04-22 23:14:03.635", formatter))
            .lastUpdateDate(LocalDateTime.parse("2022-04-22 23:14:03.636", formatter))
            .tags(new HashSet<>(Set.of(tag2, tag3)))
            .build();

    private Certificate certificate3 = new Certificate().builder()
            .id(3L)
            .name("name 3 test")
            .description("description 3")
            .price(10)
            .duration(10)
            .createDate(LocalDateTime.parse("2022-04-23 23:14:03.635", formatter))
            .lastUpdateDate(LocalDateTime.parse("2022-04-23 23:14:03.636", formatter))
            .build();

    private Certificate certificate3withTags = new Certificate().builder()
            .id(3L)
            .name("name 3 test")
            .description("description 3")
            .price(10)
            .duration(10)
            .createDate(LocalDateTime.parse("2022-04-23 23:14:03.635", formatter))
            .lastUpdateDate(LocalDateTime.parse("2022-04-23 23:14:03.636", formatter))
            .tags(new HashSet<>(Set.of(tag1, tag4, tag5)))
            .build();

    private Certificate certificate4 = new Certificate().builder()
            .id(4L)
            .name("name 4 test")
            .description("description 4")
            .price(40)
            .duration(55)
            .createDate(LocalDateTime.parse("2022-04-24 11:14:03.635", formatter))
            .lastUpdateDate(LocalDateTime.parse("2022-04-24 11:14:03.636", formatter))
            .build();

    private Certificate certificate4withTags = new Certificate().builder()
            .id(4L)
            .name("name 4 test")
            .description("description 4")
            .price(40)
            .duration(55)
            .createDate(LocalDateTime.parse("2022-04-24 11:14:03.635", formatter))
            .lastUpdateDate(LocalDateTime.parse("2022-04-24 11:14:03.636", formatter))
            .tags(new HashSet<>(Set.of(tag6, tag7, tag8)))
            .build();

    private Certificate certificate5 = new Certificate().builder()
            .id(5L)
            .name("name 5 test")
            .description("description 5")
            .price(80)
            .duration(80)
            .createDate(LocalDateTime.parse("2022-04-25 13:11:03.635", formatter))
            .lastUpdateDate(LocalDateTime.parse("2022-04-25 13:12:03.636", formatter))
            .build();

    private Certificate certificate5withTags = new Certificate().builder()
            .id(5L)
            .name("name 5 test")
            .description("description 5")
            .price(80)
            .duration(80)
            .createDate(LocalDateTime.parse("2022-04-25 13:11:03.635", formatter))
            .lastUpdateDate(LocalDateTime.parse("2022-04-25 13:12:03.636", formatter))
            .tags(new HashSet<>(Set.of(tag9, tag10)))
            .build();

    private Certificate certificate6 = new Certificate().builder()
            .id(6L)
            .name("name 6 test")
            .description("description 6")
            .price(5)
            .duration(10)
            .createDate(LocalDateTime.parse("2022-04-26 14:13:03.635", formatter))
            .lastUpdateDate(LocalDateTime.parse("2022-04-26 14:14:03.636", formatter))
            .build();

    private Certificate certificate6withTags = new Certificate().builder()
            .id(6L)
            .name("name 6 test")
            .description("description 6")
            .price(5)
            .duration(10)
            .createDate(LocalDateTime.parse("2022-04-26 14:13:03.635", formatter))
            .lastUpdateDate(LocalDateTime.parse("2022-04-26 14:14:03.636", formatter))
            .tags(new HashSet<>(Set.of(tag1, tag2, tag3)))
            .build();

    private Certificate certificate7 = new Certificate().builder()
            .id(7L)
            .name("name 7 test")
            .description("description 7")
            .price(60)
            .duration(75)
            .createDate(LocalDateTime.parse("2022-04-27 15:15:03.635", formatter))
            .lastUpdateDate(LocalDateTime.parse("2022-04-27 15:16:03.636", formatter))
            .build();

    private Certificate certificate7withTags = new Certificate().builder()
            .id(7L)
            .name("name 7 test")
            .description("description 7")
            .price(60)
            .duration(75)
            .createDate(LocalDateTime.parse("2022-04-27 15:15:03.635", formatter))
            .lastUpdateDate(LocalDateTime.parse("2022-04-27 15:16:03.636", formatter))
            .tags(new HashSet<>(Set.of(tag4, tag5, tag6)))
            .build();

    private Certificate certificate8 = new Certificate().builder()
            .id(8L)
            .name("name 8 test")
            .description("description 8")
            .price(20)
            .duration(15)
            .createDate(LocalDateTime.parse("2022-04-28 17:17:03.635", formatter))
            .lastUpdateDate(LocalDateTime.parse("2022-04-28 17:18:03.636", formatter))
            .build();

    private Certificate certificate8withTags = new Certificate().builder()
            .id(8L)
            .name("name 8 test")
            .description("description 8")
            .price(20)
            .duration(15)
            .createDate(LocalDateTime.parse("2022-04-28 17:17:03.635", formatter))
            .lastUpdateDate(LocalDateTime.parse("2022-04-28 17:18:03.636", formatter))
            .tags(new HashSet<>(Set.of(tag7, tag8)))
            .build();

    private Certificate certificate9 = new Certificate().builder()
            .id(9L)
            .name("name 9 test")
            .description("description 9")
            .price(10)
            .duration(8)
            .createDate(LocalDateTime.parse("2022-04-29 19:19:03.635", formatter))
            .lastUpdateDate(LocalDateTime.parse("2022-04-29 19:20:03.636", formatter))
            .build();

    private Certificate certificate9withTags = new Certificate().builder()
            .id(9L)
            .name("name 9 test")
            .description("description 9")
            .price(10)
            .duration(8)
            .createDate(LocalDateTime.parse("2022-04-29 19:19:03.635", formatter))
            .lastUpdateDate(LocalDateTime.parse("2022-04-29 19:20:03.636", formatter))
            .tags(new HashSet<>(Set.of(tag9, tag10, tag1)))
            .build();

    /* Certificates for transformations */

    Set<Tag> setTagForCreationNoId = new HashSet<>();
    Tag tag1ForCreationNoId = new Tag("Tag 1 test");
    Tag tag2ForCreationNoId = new Tag("Tag 2 test");
    Tag tag3ForCreationNoId = new Tag("Tag 3 test");

    {
        setTagForCreationNoId.add(tag1ForCreationNoId);
        setTagForCreationNoId.add(tag2ForCreationNoId);
        setTagForCreationNoId.add(tag3ForCreationNoId);
    }

    Tag tag1ForCreationWithId = new Tag(1L, "Tag 1 test");
    Tag tag2ForCreationWithId = new Tag(2L, "Tag 2 test");
    Tag tag3ForCreationWithId = new Tag(3L, "Tag 3 test");

    private Certificate certificateForCreationInService = new Certificate().builder()
            .name("name 10 test created")
            .description("description 10 created")
            .price(10)
            .duration(8)
            .tags(setTagForCreationNoId)
            .build();

    private Certificate certificateForCreationInDao = new Certificate().builder()
            .name("name 10 test created")
            .description("description 10 created")
            .price(10)
            .duration(8)
            .createDate(dateTimeNow)
            .lastUpdateDate(dateTimeNow)
            .tags(setTagForCreationNoId)
            .build();

    private Certificate certificateCreated = new Certificate().builder()
            .id(10L)
            .name("name 10 test created")
            .description("description 10 created")
            .price(10)
            .duration(8)
            .createDate(dateTimeNow)
            .lastUpdateDate(dateTimeNow)
            .tags(setTagForCreationNoId)
            .build();


    Tag tagReplacement = new Tag("Tag 7 test");
    Set<Tag> tagReplacementSet = new HashSet<>();

    {
        tagReplacementSet.add(tagReplacement);
    }

    private Certificate certificateForReplacementInService = new Certificate().builder()
            .id(1L)
            .name("name replaced")
            .description("description replaced")
            .price(11)
            .duration(18)
            .tags(tagReplacementSet)
            .build();

    private Certificate certificateAfterReplacementInService = new Certificate().builder()
            .id(1L)
            .name("name replaced")
            .description("description replaced")
            .price(11)
            .duration(18)
            .createDate(LocalDateTime.parse("2022-04-21 23:14:03.635", formatter))
            .lastUpdateDate(dateTimeNow)
            .tags(tagReplacementSet)
            .build();

    private Certificate certificateForReplacementInDao = new Certificate().builder()
            .id(1L)
            .name("name replaced")
            .description("description replaced")
            .price(11)
            .duration(18)
            .lastUpdateDate(LocalDateTime.parse("2022-05-27 16:02:08.987", formatter))
            .build();

    private Certificate certificateWithWrongIdForReplacementInDao = new Certificate().builder()
            .id(1_000_000L)
            .name("name replaced")
            .description("description replaced")
            .price(11)
            .duration(18)
            .lastUpdateDate(LocalDateTime.parse("2022-05-27 16:02:08.987", formatter))
            .build();

     /* Certificate DTOs as in database */

    private CertificateDto certificate1dto = new CertificateDto().builder()
            .id(1L)
            .name("name 1 test")
            .description("description 1")
            .price(30)
            .duration(45)
            .createDate(LocalDateTime.parse("2022-04-21 23:14:03.635", formatter))
            .lastUpdateDate(LocalDateTime.parse("2022-04-21 23:14:03.636", formatter))
            .build();

    private CertificateDto certificate1withTagsDto = new CertificateDto().builder()
            .id(1L)
            .name("name 1 test")
            .description("description 1")
            .price(30)
            .duration(45)
            .createDate(LocalDateTime.parse("2022-04-21 23:14:03.635", formatter))
            .lastUpdateDate(LocalDateTime.parse("2022-04-21 23:14:03.636", formatter))
            .tags(new HashSet<>(Set.of(tag1dto, tag2dto, tag3dto)))
            .build();

    private CertificateDto certificate2dto = new CertificateDto().builder()
            .id(2L)
            .name("name 2 test")
            .description("description 2")
            .price(20)
            .duration(20)
            .createDate(LocalDateTime.parse("2022-04-22 23:14:03.635", formatter))
            .lastUpdateDate(LocalDateTime.parse("2022-04-22 23:14:03.636", formatter))
            .build();

    private CertificateDto certificate2withTagsDto = new CertificateDto().builder()
            .id(2L)
            .name("name 2 test")
            .description("description 2")
            .price(20)
            .duration(20)
            .createDate(LocalDateTime.parse("2022-04-22 23:14:03.635", formatter))
            .lastUpdateDate(LocalDateTime.parse("2022-04-22 23:14:03.636", formatter))
            .tags(new HashSet<>(Set.of(tag2dto, tag3dto)))
            .build();

    private CertificateDto certificate3dto = new CertificateDto().builder()
            .id(3L)
            .name("name 3 test")
            .description("description 3")
            .price(10)
            .duration(10)
            .createDate(LocalDateTime.parse("2022-04-23 23:14:03.635", formatter))
            .lastUpdateDate(LocalDateTime.parse("2022-04-23 23:14:03.636", formatter))
            .build();

    private CertificateDto certificate3withTagsDto = new CertificateDto().builder()
            .id(3L)
            .name("name 3 test")
            .description("description 3")
            .price(10)
            .duration(10)
            .createDate(LocalDateTime.parse("2022-04-23 23:14:03.635", formatter))
            .lastUpdateDate(LocalDateTime.parse("2022-04-23 23:14:03.636", formatter))
            .tags(new HashSet<>(Set.of(tag1dto, tag4dto, tag5dto)))
            .build();

    private CertificateDto certificate4dto = new CertificateDto().builder()
            .id(4L)
            .name("name 4 test")
            .description("description 4")
            .price(40)
            .duration(55)
            .createDate(LocalDateTime.parse("2022-04-24 11:14:03.635", formatter))
            .lastUpdateDate(LocalDateTime.parse("2022-04-24 11:14:03.636", formatter))
            .build();

    private CertificateDto certificate4withTagsDto = new CertificateDto().builder()
            .id(4L)
            .name("name 4 test")
            .description("description 4")
            .price(40)
            .duration(55)
            .createDate(LocalDateTime.parse("2022-04-24 11:14:03.635", formatter))
            .lastUpdateDate(LocalDateTime.parse("2022-04-24 11:14:03.636", formatter))
            .tags(new HashSet<>(Set.of(tag6dto, tag7dto, tag8dto)))
            .build();

    private CertificateDto certificate5dto = new CertificateDto().builder()
            .id(5L)
            .name("name 5 test")
            .description("description 5")
            .price(80)
            .duration(80)
            .createDate(LocalDateTime.parse("2022-04-25 13:11:03.635", formatter))
            .lastUpdateDate(LocalDateTime.parse("2022-04-25 13:12:03.636", formatter))
            .build();

    private CertificateDto certificate5withTagsDto = new CertificateDto().builder()
            .id(5L)
            .name("name 5 test")
            .description("description 5")
            .price(80)
            .duration(80)
            .createDate(LocalDateTime.parse("2022-04-25 13:11:03.635", formatter))
            .lastUpdateDate(LocalDateTime.parse("2022-04-25 13:12:03.636", formatter))
            .tags(new HashSet<>(Set.of(tag9dto, tag10dto)))
            .build();

    private CertificateDto certificate6dto = new CertificateDto().builder()
            .id(6L)
            .name("name 6 test")
            .description("description 6")
            .price(5)
            .duration(10)
            .createDate(LocalDateTime.parse("2022-04-26 14:13:03.635", formatter))
            .lastUpdateDate(LocalDateTime.parse("2022-04-26 14:14:03.636", formatter))
            .build();

    private CertificateDto certificate6withTagsDto = new CertificateDto().builder()
            .id(6L)
            .name("name 6 test")
            .description("description 6")
            .price(5)
            .duration(10)
            .createDate(LocalDateTime.parse("2022-04-26 14:13:03.635", formatter))
            .lastUpdateDate(LocalDateTime.parse("2022-04-26 14:14:03.636", formatter))
            .tags(new HashSet<>(Set.of(tag1dto, tag2dto, tag3dto)))
            .build();

    private CertificateDto certificate7dto = new CertificateDto().builder()
            .id(7L)
            .name("name 7 test")
            .description("description 7")
            .price(60)
            .duration(75)
            .createDate(LocalDateTime.parse("2022-04-27 15:15:03.635", formatter))
            .lastUpdateDate(LocalDateTime.parse("2022-04-27 15:16:03.636", formatter))
            .build();

    private CertificateDto certificate7withTagsDto = new CertificateDto().builder()
            .id(7L)
            .name("name 7 test")
            .description("description 7")
            .price(60)
            .duration(75)
            .createDate(LocalDateTime.parse("2022-04-27 15:15:03.635", formatter))
            .lastUpdateDate(LocalDateTime.parse("2022-04-27 15:16:03.636", formatter))
            .tags(new HashSet<>(Set.of(tag4dto, tag5dto, tag6dto)))
            .build();

    private CertificateDto certificate8dto = new CertificateDto().builder()
            .id(8L)
            .name("name 8 test")
            .description("description 8")
            .price(20)
            .duration(15)
            .createDate(LocalDateTime.parse("2022-04-28 17:17:03.635", formatter))
            .lastUpdateDate(LocalDateTime.parse("2022-04-28 17:18:03.636", formatter))
            .build();

    private CertificateDto certificate8withTagsDto = new CertificateDto().builder()
            .id(8L)
            .name("name 8 test")
            .description("description 8")
            .price(20)
            .duration(15)
            .createDate(LocalDateTime.parse("2022-04-28 17:17:03.635", formatter))
            .lastUpdateDate(LocalDateTime.parse("2022-04-28 17:18:03.636", formatter))
            .tags(new HashSet<>(Set.of(tag7dto, tag8dto)))
            .build();

    private CertificateDto certificate9dto = new CertificateDto().builder()
            .id(9L)
            .name("name 9 test")
            .description("description 9")
            .price(10)
            .duration(8)
            .createDate(LocalDateTime.parse("2022-04-29 19:19:03.635", formatter))
            .lastUpdateDate(LocalDateTime.parse("2022-04-29 19:20:03.636", formatter))
            .build();

    private CertificateDto certificate9withTagsDto = new CertificateDto().builder()
            .id(9L)
            .name("name 9 test")
            .description("description 9")
            .price(10)
            .duration(8)
            .createDate(LocalDateTime.parse("2022-04-29 19:19:03.635", formatter))
            .lastUpdateDate(LocalDateTime.parse("2022-04-29 19:20:03.636", formatter))
            .tags(new HashSet<>(Set.of(tag9dto, tag10dto, tag1dto)))
            .build();

     /* Certificate DTOs for transformation */

    Set<TagDto> setTagDtoForCreationNoId = new HashSet<>();

    {
        setTagDtoForCreationNoId.add(new TagDto("Tag 1 test"));
        setTagDtoForCreationNoId.add(new TagDto("Tag 2 test"));
        setTagDtoForCreationNoId.add(new TagDto("Tag 3 test"));
    }

    private CertificateDto certificateForCreationDto = new CertificateDto().builder()
            .name("name 10 test created")
            .description("description 10 created")
            .price(10)
            .duration(8)
            .tags(setTagDtoForCreationNoId)
            .build();

    private CertificateDto certificateWithIdForCreationDto = new CertificateDto().builder()
            .id(99L)
            .name("name 101 test created")
            .description("description 101 created")
            .price(10)
            .duration(8)
            .build();
    Set<TagDto> setTagDtoForCreationWithId = new HashSet<>();

    {
        setTagDtoForCreationNoId.add(new TagDto(1L, "Tag 1 test"));
        setTagDtoForCreationNoId.add(new TagDto(2L, "Tag 2 test"));
        setTagDtoForCreationNoId.add(new TagDto(3L, "Tag 3 test"));
    }

    private CertificateDto certificateCreatedDto = new CertificateDto().builder()
            .id(10L)
            .name("name 10 test created")
            .description("description 10 created")
            .price(10)
            .duration(8)
            .createDate(dateTimeNow)
            .lastUpdateDate(dateTimeNow)
            .tags(setTagDtoForCreationWithId)
            .build();

    TagDto tagReplacementDto = new TagDto("Tag 7 test");
    Set<TagDto> tagReplacementSetDto = new HashSet<>();

    {
        tagReplacementSetDto.add(tagReplacementDto);
    }

    private CertificateDto certificateForReplacementDto = new CertificateDto().builder()
            .id(1L)
            .name("name replaced")
            .description("description replaced")
            .price(11)
            .duration(18)
            .tags(tagReplacementSetDto)
            .build();


    private CertificateDto certificateAfterReplacementDto = new CertificateDto().builder()
            .id(1L)
            .name("name replaced")
            .description("description replaced")
            .price(11)
            .duration(18)
            .createDate(LocalDateTime.parse("2022-04-21 23:14:03.635", formatter))
            .lastUpdateDate(dateTimeNow)
            .tags(tagReplacementSetDto)
            .build();

    private List<Certificate> allCertificatesList = new ArrayList<>(List.of(
            new Certificate[]{certificate1, certificate2, certificate3, certificate4, certificate5,
                    certificate6, certificate7, certificate8, certificate9}));

    private List<CertificateDto> allCertificatesDtoList = new ArrayList<>(List.of(
            new CertificateDto[]{certificate1dto, certificate2dto, certificate3dto, certificate4dto, certificate5dto,
                    certificate6dto, certificate7dto, certificate8dto, certificate9dto}));

}