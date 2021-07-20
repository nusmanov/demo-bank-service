package com.nusmanov.demobankservice.domain.konto

import com.fasterxml.jackson.annotation.JsonBackReference
import com.nusmanov.demobankservice.domain.person.PersonEntity
import org.springframework.hateoas.Link
import org.springframework.util.Assert
import java.math.BigDecimal
import javax.persistence.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Entity
@Table(name = "bankkonto")
data class KontoEntity(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(updatable = false)
    // for PRODUCTION please use a custom db sequence generator
    val kontonummer: Long? = null,
    @Column @Enumerated(EnumType.STRING)
    val type: AccountTyp = AccountTyp.GIROKONTO,
    @NotEmpty
    val name: String,
    @NotNull
    val guthaben: BigDecimal = BigDecimal.ZERO,
    @NotEmpty
    val pin: String,
    @NotNull
    val dispolimit: BigDecimal = BigDecimal.ZERO,
    @ManyToMany(mappedBy = "konten", fetch = FetchType.LAZY)
    @JsonBackReference
    val inhaber: MutableList<PersonEntity> = mutableListOf(),

    //TODO belongs to KontoDto
    @Transient
    var links: MutableList<Link> = mutableListOf()
)
{
    fun add(link: Link): KontoEntity {
        links.add(link)
        return this
    }
}
