package com.epam.esm.dao.search;

import com.epam.esm.TestConfig;
import com.epam.esm.exception.AbstractLocalizedCustomException;
import com.epam.esm.exception.InvalidRequestSortParamValueException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
class SortFactoryImplTest {

    @Autowired
    SortFactory sortFactory;

    private final String dateAscRequest1 = "DATE_ASC";
    private final String dateAscRequest2 = "date_asc";
    private final String dateAscRequest3 = "Date_Asc";
    private final String dateAscResponseExpected = " ORDER BY createDate ASC";

    private final String dateDescRequest1 = "DATE_DESC";
    private final String dateDescRequest2 = "date_desc";
    private final String dateDescRequest3 = "Date_Desc";
    private final String dateDescResponseExpected = " ORDER BY createDate DESC";

    private final String nameAscRequest1 = "NAME_ASC";
    private final String nameAscRequest2 = "name_asc";
    private final String nameAscRequest3 = "Name_Asc";
    private final String nameAscResponseExpected = " ORDER BY gift_certificate.name ASC";

    private final String nameDescRequest1 = "NAME_DESC";
    private final String nameDescRequest2 = "name_desc";
    private final String nameDescRequest3 = "Name_Desc";
    private final String nameDescResponse = " ORDER BY gift_certificate.name DESC";

    public static String someNewRequest = "Some_Request";
    public static String someNewResponse = "Some response";

    private final String invalidSortRequest = "Some_Wrong_Request";

    @Test
    void provideDateAscSortQueryFragmentForDateAscRequest1() {
        assertEquals(dateAscResponseExpected, sortFactory.provideSortQueryFragment(dateAscRequest1));
    }

    @Test
    void provideDateAscSortQueryFragmentForDateAscRequest2() {
        assertEquals(dateAscResponseExpected, sortFactory.provideSortQueryFragment(dateAscRequest2));
    }

    @Test
    void provideDateAscSortQueryFragmentForDateAscRequest3() {
        assertEquals(dateAscResponseExpected, sortFactory.provideSortQueryFragment(dateAscRequest3));
    }

    @Test
    void provideDateDescSortQueryFragmentForDateDescRequest1() {
        assertEquals(dateDescResponseExpected, sortFactory.provideSortQueryFragment(dateDescRequest1));
    }

    @Test
    void provideDateDescSortQueryFragmentForDateDescRequest2() {
        assertEquals(dateDescResponseExpected, sortFactory.provideSortQueryFragment(dateDescRequest2));
    }

    @Test
    void provideDateDescSortQueryFragmentForDateDescRequest3() {
        assertEquals(dateDescResponseExpected, sortFactory.provideSortQueryFragment(dateDescRequest3));
    }

    @Test
    void provideNameAscSortQueryFragmentForNameAscRequest1() {
        assertEquals(nameAscResponseExpected, sortFactory.provideSortQueryFragment(nameAscRequest1));
    }

    @Test
    void provideNameAscSortQueryFragmentForNameAscRequest2() {
        assertEquals(nameAscResponseExpected, sortFactory.provideSortQueryFragment(nameAscRequest2));
    }

    @Test
    void provideNameAscSortQueryFragmentForNameAscRequest3() {
        assertEquals(nameAscResponseExpected, sortFactory.provideSortQueryFragment(nameAscRequest3));
    }

    @Test
    void provideNameDescSortQueryFragmentForNameDescRequest1() {
        assertEquals(nameDescResponse, sortFactory.provideSortQueryFragment(nameDescRequest1));
    }

    @Test
    void provideNameDescSortQueryFragmentForNameDescRequest2() {
        assertEquals(nameDescResponse, sortFactory.provideSortQueryFragment(nameDescRequest2));
    }

    @Test
    void provideNameDescSortQueryFragmentForNameDescRequest3Test() {
        assertEquals(nameDescResponse, sortFactory.provideSortQueryFragment(nameDescRequest3));
    }

    @Test
    void provideSortQueryFragmentAfterInvalidRequestThrowsInvalidRequestSortParamValueException() {
        String errorMessageKeyExpected = "message.invalid_request_param_value_exception";
        String param0Expected = "Some_Wrong_Request";
        String param1Expected = "NAME_ASC, DATE_ASC, NAME_DESC, DATE_DESC";
        AbstractLocalizedCustomException exception = assertThrows(
                InvalidRequestSortParamValueException.class,
                () -> {
                    sortFactory.provideSortQueryFragment(invalidSortRequest);
                });
        assertEquals(errorMessageKeyExpected, exception.getMessageKey());
        assertEquals(param0Expected, exception.getParams()[0]);
        assertEquals(param1Expected, exception.getParams()[1]);
    }

    @Test
    void addNewSortQueryFragment() {
        sortFactory.addNewSortQueryFragment(someNewRequest, someNewResponse);
        assertEquals(someNewResponse, sortFactory.provideSortQueryFragment(someNewRequest));
    }

}