package rw.ac.university.academic.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A student admitted into the university.
 * - assigned to one department     (N : 1  Student -> Department, FK department_id)
 * - has exactly one profile        (1 : 1  Student -> StudentProfile, FK student_id in student_profiles)
 * - has many registrations         (1 : N  Student -> Registration)
 */
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;

    @Column(name = "registration_number", nullable = false, unique = true, length = 20)
    private String registrationNumber;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "admission_date", nullable = false)
    private LocalDate admissionDate;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    // Inverse side; the FK is in student_profiles.student_id.
    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private StudentProfile profile;

    @OneToMany(mappedBy = "student")
    private List<Registration> registrations = new ArrayList<>();

    public Student() {
    }

    public Student(String registrationNumber, String firstName, String lastName, String email, LocalDate admissionDate) {
        this.registrationNumber = registrationNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.admissionDate = admissionDate;
    }

    /** Keeps both sides of the one-to-one in sync. */
    public void setProfile(StudentProfile profile) {
        this.profile = profile;
        if (profile != null) {
            profile.setStudent(this);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getAdmissionDate() { return admissionDate; }
    public void setAdmissionDate(LocalDate admissionDate) { this.admissionDate = admissionDate; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public StudentProfile getProfile() { return profile; }

    public List<Registration> getRegistrations() { return registrations; }
    public void setRegistrations(List<Registration> registrations) { this.registrations = registrations; }
}
