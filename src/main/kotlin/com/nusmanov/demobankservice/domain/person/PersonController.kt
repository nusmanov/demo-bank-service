package com.nusmanov.demobankservice.domain.person

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/person")
class PersonController(
    @Autowired
    val personService: PersonService
) {

    @GetMapping("/{kundennummer}")
    fun getPerson(@PathVariable kundennummer: Long) = personService.findByKundennummer(kundennummer)


    @PostMapping(consumes = ["application/json"], produces = ["application/hal+json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun createPerson(@RequestBody personDto: PersonDto): PersonDto {
        val savedPersonDto = personService.save(personDto)
        savedPersonDto.add(linkTo(PersonController::class.java).slash(savedPersonDto.kundennummer).withSelfRel())
        return savedPersonDto
    }
}