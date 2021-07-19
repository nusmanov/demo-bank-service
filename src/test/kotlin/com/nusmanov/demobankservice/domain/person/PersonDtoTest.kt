package com.nusmanov.demobankservice.domain.person

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EmptySource
import org.junit.jupiter.params.provider.ValueSource
import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory


class PersonDtoTest {

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = ["  ", "\t", "\n"])
    fun test_validation(inputLastname: String) {
        // prepare
        val personDto = PersonDto("Tim", inputLastname, 123L, "m")
        val factory: ValidatorFactory = Validation.buildDefaultValidatorFactory()
        val validator: Validator = factory.validator
        // act
        val violations: MutableSet<ConstraintViolation<PersonDto>>? = validator.validate(personDto)
        // verify
        assertThat(violations).isNotEmpty
    }



    @Test
    fun test_toEntity() {
        val personDto = PersonDto("Tom", "Sawyer", 123L, "m")
        val personEntity = personDto.toEntity()
        assertThat(personEntity.vorname).isEqualTo("Tom")
        assertThat(personEntity.nachname).isEqualTo("Sawyer")
        assertThat(personEntity.kundennummer).isEqualTo(123L)
        assertThat(personEntity.geschlecht).isEqualTo("m")
    }
}