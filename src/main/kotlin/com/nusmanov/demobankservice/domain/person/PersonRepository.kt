package com.nusmanov.demobankservice.domain.person

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository : CrudRepository<PersonEntity, Long> {

    // return signature without ? would throw EmptyResultDataAccessException, if no user is found
    fun findByKundennummer(kundennummer: Long): PersonEntity?
    fun findByKundennummerIn(kundennummer: Set<Long>): Set<PersonEntity>
    fun deleteByKundennummer(kundennummer: Long)
}