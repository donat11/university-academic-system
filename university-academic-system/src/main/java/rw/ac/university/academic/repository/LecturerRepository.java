package rw.ac.university.academic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.university.academic.model.Lecturer;

import java.util.List;
import java.util.Optional;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {

    Optional<Lecturer> findByStaffNumber(String staffNumber);

    List<Lecturer> findByDepartmentId(Long departmentId);
}