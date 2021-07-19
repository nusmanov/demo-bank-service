package com.nusmanov.demobankservice.domain.konto

import com.nusmanov.demobankservice.domain.person.PersonService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import java.math.BigDecimal


@ExtendWith(MockitoExtension::class)
class KontoServiceTest{
    @Mock
    private lateinit var personService: PersonService

    @Mock
    private lateinit var kontoRepository: KontoRepository

    @InjectMocks // cut = class under test
    private lateinit var cut: KontoService

    @Test
    fun test_save_OK() {
        // prepare
        val kontoDto = KontoDto("Test", BigDecimal.TEN, "1234", BigDecimal.ZERO, setOf(11))

        // act
        cut.save(kontoDto)

        // verify
        verify(personService).findByKundennummerIn(setOf(11))
    }

    @Test
    fun test_save_inhaber_empty() {
        // prepare
        val kontoDto = KontoDto("Test", BigDecimal.TEN, "1234", BigDecimal.ZERO, setOf())
        Mockito.`when`(kontoRepository.save(any())).thenReturn(kontoDto.toEntity())

        // act
        cut.save(kontoDto)

        // verify
        verify(kontoRepository).save(kontoDto.toEntity())
    }
}