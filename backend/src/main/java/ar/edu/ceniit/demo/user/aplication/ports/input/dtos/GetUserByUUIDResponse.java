package ar.edu.ceniit.demo.user.aplication.ports.input.dtos;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record GetUserByUUIDResponse (
    //Propiedades del sistema
    UUID uuid,//-> Existe en DB del dominio
    Integer id,//-> Existe en DB del dominio
    String username,//-> Existe en DB del dominio
    String email, //-> Existe en DB del dominio
    //private String password; //-> NO Existe en DB del dominio

    //propiedades del dominio
    String firstName, //-> Existe en DB del dominio
    String secondName, //-> Existe en DB del dominio
    String lastName, //-> Existe en DB del dominio
    String secondLastName, //-> Existe en DB del dominio
    Integer dni, //-> Existe en DB del dominio
    //Propiedades de DB (?
    Instant createdAt, //-> Existe en DB del dominio
    Instant updatedAt, //-> Existe en DB del dominio

    Set<String> roles
){}
