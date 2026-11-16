package rw.ac.university.academic.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A course offered by one department.
 * - belongs to one department     (N : 1  Course -> Department, FK department_id)
 * - taught by many lecturers      (M : N  Course <-> Lecturer, JOIN TABLE course_lecturers, no entity)
 * - has many registrations        (1 : N  Course -> Registration)
 */
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    @Column(name = "course_code", nullable = false, unique = true, length = 20)
    private String code;

    @Column(name = "course_title", nullable = false, length = 150)
    private String title;

    @Column(name = "credits", nullable = false)
    private Integer credits;

    @Column(name = "description", length = 500)
    private String description;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    // Owning side of the many-to-many. No separate entity: Hibernate manages the join table.
    @ManyToMany
    @JoinTable(
            name = "course_lecturers",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "lecturer_id")
    )
    private Set<Lecturer> lecturers = new HashSet<>();

    @OneToMany(mappedBy = "course")
    private List<Registration> registrations = new ArrayList<>();

    public Course() {
    }

    public Course(String code, String title, Integer credits, String description) {
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public Set<Lecturer> getLecturers() { return lecturers; }
    public void setLecturers(Set<Lecturer> lecturers) { this.lecturers = lecturers; }

    public List<Registration> getRegistrations() { return registrations; }
    public void setRegistrations(List<Registration> registrations) { this.registrations = registrations; }
}
