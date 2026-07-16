package com.example.biblio_barrio.user.infrastructure;

import com.example.biblio_barrio.user.domain.User;
import com.example.biblio_barrio.user.domain.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;


//UserRepository
//ORM -> JPA ----> conexcion bd
// SELECT * from tabla1 //no sqlInyection
// getFromTabla1();
public interface SpringDataUserRepository extends JpaRepository<User, Long>, UserRepository {
}
