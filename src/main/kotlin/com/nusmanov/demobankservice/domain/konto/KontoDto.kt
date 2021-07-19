package com.nusmanov.demobankservice.domain.konto

import org.springframework.hateoas.RepresentationModel
import java.math.BigDecimal
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

data class KontoDto(
    @NotEmpty
    val name: String,
    val guthaben: BigDecimal = BigDecimal.ZERO,
    @NotEmpty
    @Size(min = 4, max = 6)
    val pin: String,
    val dispolimit: BigDecimal = BigDecimal.ZERO,
    val inhaberKundennummer: Set<Long> = mutableSetOf()

) : RepresentationModel<KontoDto>() {

    fun toEntity(): KontoEntity {
        return KontoEntity(name = name, guthaben = guthaben, dispolimit = dispolimit, pin = pin)
    }
}