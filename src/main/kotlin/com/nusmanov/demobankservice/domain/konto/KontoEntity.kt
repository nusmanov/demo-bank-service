package com.nusmanov.demobankservice.domain.konto

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonManagedReference
import com.nusmanov.demobankservice.domain.person.PersonEntity
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "bankkonto")
data class KontoEntity(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    // for PRODUCTION please use a custom db sequence generator
    val kontonummer: Long,
    @Column @Enumerated(EnumType.STRING)
    val type: AccountTyp = AccountTyp.GIROKONTO,
    val name: String,
    val guthaben: BigDecimal = BigDecimal.ZERO,
    val pin: String,
    val dispolimit: BigDecimal = BigDecimal.ZERO,
    @ManyToMany(mappedBy = "konten", fetch = FetchType.LAZY)
    @JsonBackReference
    val inhaber: MutableList<PersonEntity> = mutableListOf()
)
