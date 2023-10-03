package hh.Repository;

import hh.model.entity.Department;
import hh.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDepartment  extends JpaRepository<Department,Long> {
}
