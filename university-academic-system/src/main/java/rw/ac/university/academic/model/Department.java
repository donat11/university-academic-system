package rw.ac.university.academic.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A department of the university.
 * - offers many courses            (1 : N  Department -> Course)
 * - employs many lecturers         (1 : N  Department -> Lecturer)
 * - has many students              (1 : N  Department -> Student)
 * - has exactly one head lecturer  (1 : 1  Department -> Lecturer, FK head_lecturer_id)
 */
@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long id;

    @Column(name = "department_code", nullable = false, unique = true, length = 20)
    private String code;

    @Column(name = "department_name", nullable = false, unique = true, length = 100)
    private String name;

    // One-to-one: the head of department. The FK lives in the departments table.
    @OneToOne
    @JoinColumn(name = "head_lecturer_id", unique = true)
    private Lecturer head;

    @OneToMany(mappedBy = "department")
    private List<Lecturer> lecturers = new ArrayList<>();

    @OneToMany(mappedBy = "department")
    private List<Course> courses = new ArrayList<>();

    @OneToMany(mappedBy = "department")
    private List<Student> students = new ArrayList<>();

    public Department() {
    }

    public Department(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Lecturer getHead() { return head; }
    public void setHead(Lecturer head) { this.head = head; }

    public List<Lecturer> getLecturers() { return lecturers; }
    public void setLecturers(List<Lecturer> lecturers) { this.lecturers = lecturers; }

    public List<Course> getCourses() { return courses; }
    public void setCourses(List<Course> courses) { this.courses = courses; }

    public List<Student> getStudents() { return students; }
    public void setStudents(List<Student> students) { this.students = students; }
}
