create table person
(
    kundennummer serial primary key not null,
    nachname     varchar(255) null,
    vorname      varchar(255) null,
    geschlecht   varchar(255) null
);

CREATE SEQUENCE public.hibernate_sequence INCREMENT 1 START 1 MINVALUE 1;

create table bankkonto
(
    kontonummer serial not null primary key,
    type        varchar(255) null,
    name        varchar(255) null,
    guthaben    decimal(19, 2) null,
    dispolimit  decimal(19, 2) null,
    pin         varchar(255) null
);

CREATE TABLE person_bankkonto
(
    person_kundennummer int REFERENCES person (kundennummer) ON UPDATE CASCADE,
    bankkonto_kontonummer  int REFERENCES bankkonto (kontonummer) ON UPDATE CASCADE,
    CONSTRAINT person_bankkonto_pkey PRIMARY KEY (person_kundennummer, bankkonto_kontonummer)
)







