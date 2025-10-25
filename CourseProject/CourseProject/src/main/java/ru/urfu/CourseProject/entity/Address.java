package ru.urfu.CourseProject.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "addresses")
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private String street;
    private String building;
    private String apartment;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
