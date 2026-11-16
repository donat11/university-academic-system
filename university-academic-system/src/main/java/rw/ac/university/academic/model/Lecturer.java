package rw.ac.university.academic.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * A lecturer employed by one department.
 * - belongs to one department          (N : 1  Lecturer -> Department, FK department_id)
 * - teaches many courses               (M : N  Lecturer <-> Course, join table course_lecturers)
 */
@Entity
@Table(name = "lecturers")
public class Lecturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecturer_id")
    private Long id;

    @Column(name = "staff_number", nullable = false, unique = true, length = 20)
    private String staffNumber;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "qualification", length = 50)
    private String qualification;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    // Inverse side of the many-to-many; the join table is defined in Course.
    @ManyToMany(mappedBy = "lecturers")
    private Set<Course> courses = new HashSet<>();

    public Lecturer() {
    }

    public Lecturer(String staffNumber, String firstName, String lastName, String email, String qualification) {
        this.staffNumber = staffNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.qualification = qualification;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStaffNumber() { return staffNumber; }
    public void setStaffNumber(String staffNumber) { this.staffNumber = staffNumber; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public Set<Course> getCourses() { return courses; }
    public void setCourses(Set<Course> courses) { this.courses = courses; }
}
