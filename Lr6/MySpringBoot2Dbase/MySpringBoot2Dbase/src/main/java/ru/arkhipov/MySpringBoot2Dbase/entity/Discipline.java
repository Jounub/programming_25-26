package ru.arkhipov.MySpringBoot2Dbase.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DISCIPLINES")
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "teacher")
    private String teacher;

    @Column(name = "faculty")
    private String faculty;

    @Column(name = "hours")
    private Integer hours;

    @Column(name = "semester")
    private Integer semester;

    @Enumerated(EnumType.STRING)
    @Column(name = "control_type")
    private ControlType controlType;

    public enum ControlType{
        pass,
        exam
    }
}
