package rw.ac.university.academic.model;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * The personal profile of exactly one student (1 : 1).
 * Owning side of the relationship: the FK student_id (UNIQUE) lives here.
 */
@Entity
@Table(name = "student_profiles")
public class StudentProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long id;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false, length = 10)
    private Gender gender;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "residential_address", length = 255)
    private String residentialAddress;

    @OneToOne
    @JoinColumn(name = "student_id", nullable = false, unique = true)
    private Student student;

    public StudentProfile() {
    }

    public StudentProfile(LocalDate dateOfBirth, Gender gender, String phoneNumber, String residentialAddress) {
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.residentialAddress = residentialAddress;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getResidentialAddress() { return residentialAddress; }
    public void setResidentialAddress(String residentialAddress) { this.residentialAddress = residentialAddress; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
}
