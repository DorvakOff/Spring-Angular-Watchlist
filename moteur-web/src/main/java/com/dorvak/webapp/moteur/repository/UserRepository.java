package com.dorvak.webapp.moteur.repository;

import com.dorvak.webapp.moteur.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {

}
