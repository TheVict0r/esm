# module2

### Task

REST API Project for EPAM Laboratory


##### Business requirements
PART 1
1. Develop web service for Gift Certificates system with the following entities (many-to-many):
    - *CreateDate*, *LastUpdateDate* - format *ISO 8601* . Example: 2018-08-29T06:12:15.156.  
    - *Duration* - in days (expiration period)
2. The system should expose REST APIs to perform the following operations:
    - CRUD operations for GiftCertificate. If new tags are passed during creation/modification – they should be created in the DB. For update operation - update only fields, that pass in request, others should not be updated. Batch insert is out of scope.
    - CRD operations for Tag.
    - Get certificates with tags (all params are optional and can be used in conjunction):
        - by tag name (ONE tag)
        - search by part of name/description (can be implemented, using DB function call)
        - sort by date or by name ASC/DESC (extra task: implement ability to apply both sort type at the same time).
PART 2
The system should be extended to expose the following REST APIs: 
1. Change single field of gift certificate (e.g. implement the possibility to change only duration of a certificate or only price). 
2. Add new entity User.
   * implement only get operations for user entity.
3. Make an order on gift certificate for a user (user should have an ability to buy a certificate).
4. Get information about user’s orders. 
5. Get information about user’s order: cost and timestamp of a purchase.
   * The order cost should not be changed if the price of the gift certificate is changed.
6. Get the most widely used tag of a user with the highest cost of all orders.
   * Create separate endpoint for this query.
   * Demonstrate SQL execution plan for this query (explain).
7. Search for gift certificates by several tags (“and” condition).
8. Pagination should be implemented for all GET endpoints. Please, create a flexible and non-erroneous solution. Handle all exceptional cases. 
9. Support HATEOAS on REST endpoints.

##### Application requirements

1. JDK version: 8. Use Streams, java.time.*, an etc. where it is appropriate. (the JDK version can be increased in agreement with the mentor/group coordinator/run coordinator)
2. Application packages root: com.epam.esm.
3. Java Code Convention is mandatory (exception: margin size –120 characters).
4. Apache Maven/Gradle, latest version. Multi-module project.
5. Spring Framework, the latest version.
6. Database: PostgreSQL/MySQL, latest version.
7. Testing: JUnit, the latest version, Mockito.
8. Service layer should be covered with unit tests not less than 80%.
9. Hibernate should be used as a JPA implementation for data access.
10. Spring Transaction should be used in all necessary areas of the application.

##### Application restrictions

1. Hibernate specific features.
2. Spring Data
