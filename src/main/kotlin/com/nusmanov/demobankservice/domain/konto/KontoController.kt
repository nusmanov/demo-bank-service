package com.nusmanov.demobankservice.domain.konto

import com.nusmanov.demobankservice.domain.person.PersonController
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/konto")
class KontoController(val kontoService: KontoService) {

    @GetMapping("/{kontonummer}")
    fun getKonto(@PathVariable kontonummer: Long): KontoEntity =  kontoService.findByKontonummer(kontonummer)


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createKonto(@Valid @RequestBody newKontoDto: KontoDto): KontoEntity {
        val savedKonto = kontoService.save(newKontoDto)
        return savedKonto.add(WebMvcLinkBuilder.linkTo(KontoController::class).slash(savedKonto.kontonummer).withSelfRel())
    }
}