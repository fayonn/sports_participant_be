package com.example.sports_participant_be_main.models;

import com.example.sports_participant_be_main.dto.OwnerDto;
import com.example.sports_participant_be_main.utils.GlobalEntityProperties;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.*;

@Entity
@Table(name = "owners")
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Owner extends GlobalEntityProperties {

    @Id
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator(name = "hibernate-uuid", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    @EqualsAndHashCode.Include
    @ToString.Include
    private UUID id;

    @Column(name = "firstname", nullable = false, length = 20)
    @EqualsAndHashCode.Include
    @ToString.Include
    private String firstname;

    @Column(name = "lastname", nullable = false, length = 20)
    @EqualsAndHashCode.Include
    @ToString.Include
    private String lastname;

    @Email(regexp = ".+@.+\\..+", message = "Invalid email format")
    @Column(name = "email", length = 60, nullable = false, unique = true)
    @EqualsAndHashCode.Include
    @ToString.Include
    private String email;

    @Column(name = "password", nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private String password;

    @Column(name = "country")
    @EqualsAndHashCode.Include
    @ToString.Include
    private String country;

    @Column(name = "city")
    @EqualsAndHashCode.Include
    @ToString.Include
    private String city;

    @Column(name = "phone_number", nullable = false, unique = true)
    @EqualsAndHashCode.Include
    @ToString.Include
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "owner_role_id", nullable = false, foreignKey = @ForeignKey(name = "fk_owner_role"))
    private Role role;

    @Column(name = "status")
    @EqualsAndHashCode.Include
    @ToString.Include
    @Enumerated(EnumType.STRING)
    private Owner.Status status;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    private Set<GymBrand> gymBrands = new HashSet<>();

    @AllArgsConstructor
    public enum Status {
        ACTIVE("ACTIVE"),
        BANNED("BANNED"),
        DISABLED("DISABLED"),
        FROZEN("FROZEN"),
        ;

        private final String value;
    }

    public OwnerDto ofDto() {
        return OwnerDto.builder()
                .id(this.id)
                .firstname(this.firstname)
                .lastname(this.lastname)
                .email(this.email)
                .password(this.password)
                .country(this.country)
                .city(this.city)
                .phoneNumber(this.phoneNumber)
                .status(this.status)
                .build()
                ;
    }


}
