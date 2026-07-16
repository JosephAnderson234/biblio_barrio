package com.example.biblio_barrio.user.domain;

import java.util.Optional;

public interface UserRepository {

    User save(User user); // create o insert

    Optional<User> findById(Long id); // select * from where id = *

    Optional<User> findByUsername(String username); // select * from table1 where username = ""

    boolean existsByUsername(String username); // ----

    boolean existsByEmail(String email);
}
