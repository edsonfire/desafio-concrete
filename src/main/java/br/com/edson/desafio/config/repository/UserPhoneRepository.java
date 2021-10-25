package br.com.edson.desafio.config.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.edson.desafio.entities.UserPhone;

public interface UserPhoneRepository extends JpaRepository<UserPhone, UUID> {

}
