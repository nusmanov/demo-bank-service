package com.nusmanov.demobankservice

import com.nusmanov.demobankservice.domain.konto.KontoDto
import com.nusmanov.demobankservice.domain.konto.KontoService
import com.nusmanov.demobankservice.domain.person.PersonEntity
import com.nusmanov.demobankservice.domain.person.PersonRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.math.BigDecimal

/**
 * 26.3.6. Testing with a running server
 * - start a full running server using random ports
 * - start PostgresSQL Server as Docker Container
 */
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class IntegrationWithDatabaseTest {

    @Autowired
    lateinit var personRepository: PersonRepository

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var kontoService: KontoService


    companion object {

        @Container
        val container = PostgreSQLContainer<Nothing>("postgres:13").apply {
            withDatabaseName("testdb")
            withUsername("postgres")
            withPassword("postgres")
        }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", container::getJdbcUrl)
            registry.add("spring.datasource.password", container::getPassword)
            registry.add("spring.datasource.username", container::getUsername)
        }
    }

    @Test
    @Transactional
    fun savePerson_withJPARepository() {
        personRepository.save(PersonEntity(vorname = "Tom", nachname = "Sawyer", geschlecht = "m"))

        println(personRepository.findAll())
    }

    @Test
    fun getPerson_withRestEndpoint() {
        // prepare
        val nr =
            personRepository.save(PersonEntity(vorname = "Tom", nachname = "Sawyer", geschlecht = "m")).kundennummer

        // act & verify
        webTestClient.get()
            .uri("/person/$nr")
            .accept(APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .is2xxSuccessful
            .expectHeader()
            .contentType(APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.vorname").isEqualTo("Tom")
            .jsonPath("$.kundennummer").isNotEmpty
    }

    @Test
    fun test_saveKonto() {
        // prepare
        val nr =
            personRepository.save(PersonEntity(vorname = "Tom", nachname = "Sawyer", geschlecht = "m")).kundennummer
        val kontoDto = KontoDto("Test", BigDecimal.TEN, "1234", BigDecimal.ZERO, setOf(nr) as Set<Long>)

        // act
        val kontoEntity = kontoService.save(kontoDto)

        // verify
        val savedKontoDto = kontoService.findByKontonummer(kontoEntity.kontonummer!!)
        assertThat(savedKontoDto.name).isEqualTo("Test")
        assertThat(savedKontoDto.guthaben).isEqualByComparingTo(BigDecimal.TEN)
        assertThat(savedKontoDto.dispolimit).isEqualByComparingTo(BigDecimal.ZERO)
    }

     @Test
    fun test_saveKonto_wihtout_owner() {
        // prepare
        val kontoDto = KontoDto("Test123", BigDecimal.TEN, "1234", BigDecimal.ZERO, setOf())

        // act
        val kontoEntity = kontoService.save(kontoDto)

        // verify
        val savedKontoDto = kontoService.findByKontonummer(kontoEntity.kontonummer!!)
        assertThat(savedKontoDto.name).isEqualTo("Test123")
    }
}