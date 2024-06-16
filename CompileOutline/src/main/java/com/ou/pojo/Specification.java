package com.ou.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "specification")
@NamedQueries({
        @NamedQuery(name = "Specification.findAssignmentsById", query = "SELECT s FROM Specification s WHERE s.assignments.id = :assignmentId")
})

public class Specification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "credits", nullable = false)
    private Integer credits;

    @Column(name = "createdAt")
    private Instant createdAt;

    @Lob
    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "Assignments_id", nullable = false)
    private Assignments assignments;

}