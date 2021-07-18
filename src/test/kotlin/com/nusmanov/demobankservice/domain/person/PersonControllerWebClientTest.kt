package com.nusmanov.demobankservice.domain.person

import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient


/**
 *
 * 26.3.13. Auto-configured Spring MVC Tests
 * https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing.spring-boot-applications.spring-mvc-tests
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
internal class PersonControllerWebClientTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @MockBean
    private lateinit var personService: PersonService

    @Test
    fun test_getPerson() {
        // prepare
        given(personService.findByKundennummer(anyLong())).willReturn(PersonDto("Tom", "Sawyer", "m"))

        // act & verify
        webTestClient.get().uri("/person/88888")
            .exchange().expectStatus().isEqualTo(200)
    }

}