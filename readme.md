# Coach
This project ist used to coach new spring boot developer to:
 - implement a simple bank restful service
 

## Testing
Please pay attention, that this Readme is a summary and cites from docs.spring.io.
Spring introduces a large library for testing.
For Spring Newbies i highly recommend following the official docs, to keep testing strategies clear.

### Testing with a running server
If you need to start a full running server, we recommend that you use random ports.
If you use @SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT), an available port is picked at random each time your test runs.

The @LocalServerPort annotation can be used to inject the actual port used into your test.
For convenience, tests that need to make REST calls to the started server can 
additionally @Autowire a WebTestClient, which resolves relative links to the running server 
and comes with a dedicated API for verifying responses, as shown in the following example:
### Testing with a mock environment
`By default, @SpringBootTest does not start the server. 
If you have web endpoints that you want to test against this mock environment, 
you can additionally configure MockMvc`

### Testing web layer without application context 
Focus only on the web layer and not start a complete ApplicationContext, consider using @WebMvcTest instead
  - https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing.spring-boot-applications.spring-mvc-tests
Testing within a mocked environment is usually faster than running with a full Servlet container.
However, since mocking occurs at the Spring MVC layer, 
code that relies on lower-level Servlet container behavior cannot be directly tested with MockMvc.
For example, Spring Boot’s error handling is based on the “error page” support provided by the Servlet container. This means that, whilst you can test your MVC layer throws and handles exceptions as expected, you cannot directly test that a specific custom error page is rendered. If you need to test these lower-level concerns, you can start a fully running server as described in the next section.
link:

### Test with application context vs Test Web Layer without application context
- `mvn -Dtest=PersonControllerWebClientTest#test_getPerson test`
- `mvn -Dtest=PersonControllerTest#test_getPerson_classic test`

### Integration Tests with Testcontainers
- Starts the application and a PostgresSQL Docker Container

#### Manual testing the API with a started application
- Pay attention you need a running PostgresSQL
```
    curl -d '{"vorname":"tim","nachname":"sawyer", "geschlecht":"m"}' -H 'Content-Type: application/json' http://localhost:8080/person
    curl -d '{"vorname":"tim","nachname":"sawyer", "geschlecht":"m" ,"konten":[{"type": "GIROKONTO", "name": "pi", "pin": "1234", "dispolimit": "30.00"}]}' -H 'Content-Type: application/json' http://localhost:8080/person
    curl http://localhost:8080/person/3
```