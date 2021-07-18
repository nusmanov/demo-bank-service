package com.nusmanov.demobankservice.domain.konto

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface KontoRepository : CrudRepository<KontoEntity, Long>{

    fun findByKontonummer(kontonummer:Long)
    fun deleteByKontonummer(kontonummer: Long)
}