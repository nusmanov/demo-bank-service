package com.nusmanov.demobankservice.domain.person

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class PersonService(@Autowired val personRepository: PersonRepository) {

    fun save(personDto: PersonDto): PersonDto {
        val personEntity = personDto.toEntity()
        personDto.konten.forEach(personEntity::addKonto)
        return PersonDto.toDto(personRepository.save(personEntity))
    }

    fun findByKundennummer(kundennummer: Long): PersonDto {
        return PersonDto.toDto(
            personRepository.findByKundennummer(kundennummer) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        )
    }
}