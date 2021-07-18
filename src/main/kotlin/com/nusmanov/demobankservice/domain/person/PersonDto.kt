package com.nusmanov.demobankservice.domain.person

import com.fasterxml.jackson.annotation.JsonInclude
import com.nusmanov.demobankservice.domain.konto.KontoEntity
import org.springframework.hateoas.RepresentationModel


data class PersonDto(
    val vorname: String,
    val nachname: String,
    val kundennummer: Long? = null,
    val geschlecht: String,
    // TODO use a new DTO here
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val konten: List<KontoEntity> = mutableListOf()
) : RepresentationModel<PersonDto>() {
    constructor(vorname: String, nachname: String, geschlecht: String) : this(
        vorname = vorname,
        nachname = nachname,
        kundennummer = null,
        geschlecht = geschlecht
    )

    fun toEntity(): PersonEntity {
        return PersonEntity(
            vorname = this.vorname,
            nachname = this.nachname,
            geschlecht = this.geschlecht,
            kundennummer = this.kundennummer,
        )
    }

    companion object {
        fun toDto(entity: PersonEntity): PersonDto {
            return PersonDto(entity.vorname, entity.nachname, entity.kundennummer, entity.geschlecht, entity.konten)
        }
    }
}