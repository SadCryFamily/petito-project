package com.petito.project.entity.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "details")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Details {

    @Id
    @Column(name = "user_login")
    private String login;

    @MapsId
    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_login")
    private User user;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "number_phone")
    private Integer numberPhone;
}
