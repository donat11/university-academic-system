package rw.ac.university.academic.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * JOIN ENTITY between Student and Course (M : N with extra attributes).
 * A plain join table cannot hold registration date, academic year, semester,
 * status and grade, so the many-to-many is split into two many-to-one links.
 * A student can register for the same course only once per academic year and semester.
 */
@Entity
@Table(
        name = "registrations",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_registration_student_course_term",
                columnNames = {"student_id", "course_id", "academic_year", "semester"}
        )
)
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "registration_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    // e.g. "2026/2027"
    @Column(name = "academic_year", nullable = false, length = 9)
    private String academicYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "semester", nullable = false, length = 20)
    private Semester semester;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 10)
    private RegistrationStatus status = RegistrationStatus.PENDING;

    // Mark out of 100, null until the course is graded.
    @Column(name = "grade", precision = 5, scale = 2)
    private BigDecimal grade;

    public Registration() {
    }

    public Registration(Student student, Course course, LocalDate registrationDate,
                        String academicYear, Semester semester, RegistrationStatus status) {
        this.student = student;
        this.course = course;
        this.registrationDate = registrationDate;
        this.academicYear = academicYear;
        this.semester = semester;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public LocalDate getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }

    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }

    public Semester getSemester() { return semester; }
    public void setSemester(Semester semester) { this.semester = semester; }

    public RegistrationStatus getStatus() { return status; }
    public void setStatus(RegistrationStatus status) { this.status = status; }

    public BigDecimal getGrade() { return grade; }
    public void setGrade(BigDecimal grade) { this.grade = grade; }
}
