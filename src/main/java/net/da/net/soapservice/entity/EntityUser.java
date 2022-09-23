package net.da.net.soapservice.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class EntityUser {
    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String name;
    @Id
    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String login;
    @NotNull
    @NotBlank
    @Column(nullable = false)
    @Pattern(regexp = ".*\\d+.*[A-Z]+.*|.*[A-Z]+.*\\d+.*")
    private String password;
    @ManyToMany//(//cascade = {CascadeType.ALL})
    @JoinTable(name = "EntityRole_users",
            joinColumns = @JoinColumn(name = "entityUser_login"),
            inverseJoinColumns = @JoinColumn(name = "entityRole_id"))
    private List<EntityRole> roles;
}
