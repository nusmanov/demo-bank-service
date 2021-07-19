package com.nusmanov.demobankservice.domain.person

import com.fasterxml.jackson.annotation.JsonInclude
import com.nusmanov.demobankservice.domain.konto.KontoEntity
import org.springframework.hateoas.RepresentationModel
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty


data class PersonDto(
    @field:NotBlank
    val vorname: String,
    @field:NotBlank
    val nachname: String,
    val kundennummer: Long? = null,
    @field:NotEmpty
    val geschlecht: String,
    // TODO use a new DTO here
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val konten: MutableList<KontoEntity> = mutableListOf()
) : RepresentationModel<PersonDto>() {

    fun toEntity(): PersonEntity {
        return PersonEntity(
            vorname = this.vorname,
            nachname = this.nachname,
            geschlecht = this.geschlecht,
            kundennummer = this.kundennummer,
            konten = this.konten
        )
    }

    companion object {
        fun toDto(entity: PersonEntity): PersonDto {
            return PersonDto(entity.vorname, entity.nachname, entity.kundennummer, entity.geschlecht, entity.konten)
        }
    }
}