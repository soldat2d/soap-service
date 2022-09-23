package net.da.net.soapservice.endpoint;

import net.da.net.soapservice.entity.EntityRole;
import net.da.net.soapservice.entity.EntityUser;
import net.da.net.soapservice.repository.RoleRepository;
import net.da.net.soapservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import soapservice.net.da.net.*;

import java.util.ArrayList;
import java.util.List;

@Endpoint
public class UserEndpoint {
    private static final String NAMESPACE_URI = "http://net.da.net.soapservice";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserEndpoint(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllUsersRequest")
    @ResponsePayload
    public GetAllUsersResponse getAllUsers() {
        GetAllUsersResponse response = new GetAllUsersResponse();
        List<EntityUser> entityUsers = userRepository.findAll();
        if (entityUsers.size() == 0) {
            response.setSuccess("false");
            return response;
        }
        entityUsers.forEach(u -> response.getUser().add(u.getName()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRequest")
    @ResponsePayload
    @Transactional
    public GetUserResponse getUser(@RequestPayload GetUserRequest request) {
        GetUserResponse response = new GetUserResponse();
        EntityUser entityUser = userRepository.findUserByLogin(request.getLogin());
        if (entityUser == null) {
            response.setSuccess("false");
            return response;
        }
        User user = new User();
        user.setName(entityUser.getName());
        user.setLogin(entityUser.getLogin());
        user.setPassword(entityUser.getPassword());
        entityUser.getRoles().forEach(u ->
        {
            Role r = new Role();
            r.setName(u.getName());
            user.getRoles().add(r);
        });
        response.setUser(user);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteUserRequest")
    @ResponsePayload
    @Transactional
    public DeleteUserResponse deleteUser(@RequestPayload DeleteUserRequest request) {
        DeleteUserResponse response = new DeleteUserResponse();
        response.setSuccess(true);
        long result = userRepository.deleteByLogin(request.getLogin());
        if (result < 1) {
            response.setSuccess(false);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addUserRequest")
    @ResponsePayload
    public AddUserResponse addUser(@RequestPayload AddUserRequest request) {
        AddUserResponse response = new AddUserResponse();
        response.setSuccess(false);
        try {
            if (userRepository.findUserByLogin(request.getUser().getLogin()) != null) {
                return response;
            }
            List<EntityRole> entityRoleList = new ArrayList<>();
            request.getUser().getRoles().forEach(r -> {
                EntityRole entityRole = roleRepository.findRoleByName(r.getName());
                if (entityRole == null) {
                    entityRole = roleRepository.save(EntityRole.builder().name(r.getName()).build());
                }
                entityRoleList.add(entityRole);
            });
            EntityUser entityUser = EntityUser.builder()
                    .login(request.getUser().getLogin())
                    .name(request.getUser().getName())
                    .password(request.getUser().getPassword())
                    .roles(entityRoleList)
                    .build();
            userRepository.save(entityUser);
        } catch (Exception e) {
            return response;
        }
        response.setSuccess(true);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateUserRequest")
    @ResponsePayload
    public UpdateUserResponse updateUser(@RequestPayload UpdateUserRequest request) {
        UpdateUserResponse response = new UpdateUserResponse();
        response.setSuccess(false);
        try {
            EntityUser entityUser = userRepository.findUserByLogin(request.getUser().getLogin());
            if (entityUser == null) {
                return response;
            }
            List<EntityRole> entityRoleList = entityUser.getRoles();
            if (request.getUser().getRoles().size() != 0) {
                entityRoleList = new ArrayList<>();
                List<EntityRole> finalEntityRoleList = entityRoleList;
                request.getUser().getRoles().forEach(r -> {
                    EntityRole entityRole = roleRepository.findRoleByName(r.getName());
                    if (entityRole == null) {
                        entityRole = roleRepository.save(EntityRole.builder().name(r.getName()).build());
                    }
                    finalEntityRoleList.add(entityRole);
                });
            }
            String name = request.getUser().getName();
            if (!name.isEmpty()) {
                entityUser.setName(name);
            }
            String password = request.getUser().getPassword();
            if (!password.isEmpty()) {
                entityUser.setPassword(request.getUser().getPassword());
            }
            entityUser.setRoles(entityRoleList);
            userRepository.save(entityUser);
        } catch (Exception e) {
            return response;
        }
        response.setSuccess(true);
        return response;
    }
}