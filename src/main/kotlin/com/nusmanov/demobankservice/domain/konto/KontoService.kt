package com.nusmanov.demobankservice.domain.konto

import com.nusmanov.demobankservice.domain.person.PersonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
class KontoService(
    @Autowired
    val kontoRepository: KontoRepository,
    @Autowired
    val personService: PersonService
) {
    @Transactional
    fun save(kontoDto: KontoDto): KontoEntity {

        return if (kontoDto.inhaberKundennummer.isEmpty()) {
            // save the account without an owner
            kontoRepository.save(kontoDto.toEntity())
        } else {
            val personen = personService.findByKundennummerIn(kontoDto.inhaberKundennummer)
            val kontoEntity = kontoDto.toEntity()
            personen.forEach { it.addKonto(kontoEntity) }
            kontoEntity
        }
    }

    fun findByKontonummer(kontonummer: Long): KontoEntity {
        return kontoRepository.findByKontonummer(kontonummer) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }


}