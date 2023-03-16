package com.dorvak.webapp.moteur.repository;

import com.dorvak.webapp.moteur.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

    User findByUsernameOrEmail(String username, String email);

    boolean existsByUsernameOrEmail(String username, String email);

}
