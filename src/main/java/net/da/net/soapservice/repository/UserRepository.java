package net.da.net.soapservice.repository;

import net.da.net.soapservice.entity.EntityUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<EntityUser, String> {
    EntityUser findUserByLogin(String login);

    long deleteByLogin(String login);
}
