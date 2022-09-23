package net.da.net.soapservice.repository;

import net.da.net.soapservice.entity.EntityRole;
import net.da.net.soapservice.entity.EntityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
public class InitRepository {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        EntityRole adminEntityRole = roleRepository.save(new EntityRole(null, "Admin", null));
        EntityRole analystEntityRole = roleRepository.save(new EntityRole(null, "Analyst", null));
        EntityRole operatorEntityRole = roleRepository.save(new EntityRole(null, "Operator", null));

        userRepository.save(new EntityUser("Patrick", "Pat", "12A3", Arrays.asList(adminEntityRole, analystEntityRole)));
        userRepository.save(new EntityUser("Dan", "Alpha", "23B4", Arrays.asList(analystEntityRole, operatorEntityRole)));
        userRepository.save(new EntityUser("Luke", "Keystone", "C345", Arrays.asList(operatorEntityRole)));
    }
}
