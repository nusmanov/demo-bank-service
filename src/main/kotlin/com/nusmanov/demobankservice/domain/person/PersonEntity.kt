package com.nusmanov.demobankservice.domain.person

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.nusmanov.demobankservice.domain.konto.KontoEntity
import javax.persistence.*

/**
 * Ubiquitous Language - allgegenwärtige Sprache
 * "Übersetzung stört die Kommunikation (Domänenexperten <> Devs)
 *  und macht das Erarbeiten des Wissens blutleer" - DDD
 */
@Entity
@Table(name = "person")
data class PersonEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    val kundennummer: Long? = null,
    val vorname: String,
    val nachname: String,
    val geschlecht: String,

    @ManyToMany(cascade = [CascadeType.PERSIST], fetch = FetchType.LAZY)
    @JoinTable(
        name = "person_bankkonto",
        joinColumns = [JoinColumn(name = "person_kundennummer", referencedColumnName = "kundennummer")],
        inverseJoinColumns = [JoinColumn(name = "bankkonto_kontonummer", referencedColumnName = "kontonummer")]
    )
    @JsonManagedReference
    val konten: MutableList<KontoEntity> = mutableListOf()
) {
    fun addKonto(k: KontoEntity) {
        konten.add(k)
        k.inhaber.add(this)
    }

    constructor(vorname: String, nachname: String, geschlecht: String) : this(
        null,
        vorname,
        nachname,
        geschlecht
    )

}


