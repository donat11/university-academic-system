package rw.ac.university.academic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.annotation.Rollback;
import rw.ac.university.academic.model.*;
import rw.ac.university.academic.repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests every relationship by saving sample data into university_academic_db.
 * The data is COMMITTED (Rollback false) so it can be seen in pgAdmin for the screenshots.
 * Running it again first removes the old sample data.
 */
@SpringBootTest
class EntityMappingTests {

    @Autowired private DepartmentRepository departmentRepository;
    @Autowired private LecturerRepository lecturerRepository;
    @Autowired private CourseRepository courseRepository;
    @Autowired private StudentRepository studentRepository;
    @Autowired private StudentProfileRepository studentProfileRepository;
    @Autowired private RegistrationRepository registrationRepository;

    @Test
    @Transactional
    @Rollback(false)
    void saveAndReadAllRelationships() {
        // clean old sample data (children first)
        registrationRepository.deleteAll();
        courseRepository.deleteAll();
        studentProfileRepository.deleteAll();
        studentRepository.deleteAll();
        departmentRepository.findAll().forEach(d -> d.setHead(null));
        departmentRepository.flush();
        lecturerRepository.deleteAll();
        departmentRepository.deleteAll();
        departmentRepository.flush();

        // Department
        Department ict = departmentRepository.save(new Department("ICT", "Information and Communication Technology"));

        // Lecturers (N : 1 Department)
        Lecturer l1 = new Lecturer("L001", "Jean", "Mugabo", "j.mugabo@univ.ac.rw", "PhD");
        l1.setDepartment(ict);
        Lecturer l2 = new Lecturer("L002", "Alice", "Uwase", "a.uwase@univ.ac.rw", "MSc");
        l2.setDepartment(ict);
        lecturerRepository.save(l1);
        lecturerRepository.save(l2);

        // Head of department (1 : 1)
        ict.setHead(l1);

        // Courses (N : 1 Department) + lecturers (M : N, join table course_lecturers)
        Course web = new Course("WEB301", "Web Technology and Internet", 4, "Spring Boot and Hibernate");
        web.setDepartment(ict);
        web.getLecturers().add(l1);
        web.getLecturers().add(l2);
        Course db = new Course("DBS201", "Database Systems", 3, "Relational databases and SQL");
        db.setDepartment(ict);
        db.getLecturers().add(l2);
        courseRepository.save(web);
        courseRepository.save(db);

        // Student (N : 1 Department) + profile (1 : 1)
        Student s = new Student("27091", "Donat", "Nshimiyimana", "donat@student.univ.ac.rw", LocalDate.of(2024, 9, 1));
        s.setDepartment(ict);
        s.setProfile(new StudentProfile(LocalDate.of(2003, 5, 14), Gender.MALE, "+250780000000", "Kigali, Rwanda"));
        studentRepository.save(s);

        // Registrations (join entity Student <-> Course)
        Registration r1 = new Registration(s, web, LocalDate.now(), "2026/2027", Semester.SEMESTER_ONE, RegistrationStatus.APPROVED);
        r1.setGrade(new BigDecimal("85.50"));
        Registration r2 = new Registration(s, db, LocalDate.now(), "2026/2027", Semester.SEMESTER_ONE, RegistrationStatus.PENDING);
        registrationRepository.save(r1);
        registrationRepository.save(r2);
        registrationRepository.flush();

        // ---- checks ----
        assertEquals("L001", departmentRepository.findByCode("ICT").orElseThrow().getHead().getStaffNumber());
        assertEquals(2, lecturerRepository.findByDepartmentId(ict.getId()).size());
        assertEquals(2, courseRepository.findByDepartmentId(ict.getId()).size());
        assertEquals(2, courseRepository.findByLecturersId(l2.getId()).size());
        assertEquals("Kigali, Rwanda",
                studentProfileRepository.findByStudentId(s.getId()).orElseThrow().getResidentialAddress());
        assertEquals(2, registrationRepository.findByStudentId(s.getId()).size());
        assertEquals(1, registrationRepository.findByStatus(RegistrationStatus.APPROVED).size());
    }
}
