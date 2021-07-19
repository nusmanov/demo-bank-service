package com.nusmanov.demobankservice.domain.konto

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
    fun createKonto(@Valid @RequestBody newKontoDto: KontoDto): KontoEntity =  kontoService.save(newKontoDto)

}