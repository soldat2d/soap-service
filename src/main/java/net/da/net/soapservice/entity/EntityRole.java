package net.da.net.soapservice.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
public class EntityRole {
    @Id
    @GeneratedValue //(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @NotBlank
    @Column(unique = true, nullable = false)
    private String name;
    @ManyToMany(mappedBy = "roles")
    List<EntityUser> users;
}
