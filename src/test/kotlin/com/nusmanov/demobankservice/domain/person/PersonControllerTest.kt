package com.nusmanov.demobankservice.domain.person

import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.BDDMockito.given
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.hateoas.MediaTypes.HAL_JSON
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


/**
 *
 * 26.3.13. Auto-configured Spring MVC Tests
 * https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing.spring-boot-applications.spring-mvc-tests
 */
@WebMvcTest(PersonController::class)
internal class PersonControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @MockBean
    private lateinit var personService: PersonService

    @Test
    fun test_getPerson_modern_DSL() {
        // prepare
        given(personService.findByKundennummer(anyLong())).willReturn(PersonDto("Tom", "Sawyer", "m"))

        // act & verify
        mvc.get("/person/88888")
            .andExpect {
                status { isOk() }
                content { contentType(HAL_JSON) }
                jsonPath("$.vorname") { value("Tom") }
            }
    }

    @Test
    fun test_getPerson_classic() {
        // prepare
        given(personService.findByKundennummer(anyLong())).willReturn(PersonDto("Tom", "Sawyer", "m"))

        // act & verify
        mvc.perform(MockMvcRequestBuilders.get("/person/88888"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.vorname").value("Tom"))
    }

    @Test
    fun test_createPerson() {
        //prepare
        given(personService.save(any())).willReturn(PersonDto("Tom", "Sawyer", 123, "m"))

        // act & verify
        mvc.post("/person")
        {
            contentType = APPLICATION_JSON
            content = """
                        { "vorname": "Tom",
                          "nachname": "Sawyer",
                          "geschlecht": "m"
                        }
                       """
        }
            .andExpect {
                status { isCreated() }
                content { contentType(HAL_JSON) }
                jsonPath("$.vorname") { value("Tom") }
                jsonPath("$._links.self.href") { value("http://localhost/person/123") }
            }
            .andReturn()
    }

}