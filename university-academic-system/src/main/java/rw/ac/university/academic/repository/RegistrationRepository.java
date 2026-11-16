package rw.ac.university.academic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.university.academic.model.Registration;
import rw.ac.university.academic.model.RegistrationStatus;
import rw.ac.university.academic.model.Semester;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    List<Registration> findByStudentId(Long studentId);

    List<Registration> findByCourseId(Long courseId);

    List<Registration> findByStatus(RegistrationStatus status);

    List<Registration> findByAcademicYearAndSemester(String academicYear, Semester semester);
}