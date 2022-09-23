package net.da.net.soapservice.repository;

import net.da.net.soapservice.entity.EntityRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<EntityRole, Long> {
    EntityRole findRoleByName(String name);
}
