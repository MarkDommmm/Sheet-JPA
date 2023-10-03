package hh.model.dto.request;

import hh.model.entity.Department;
import hh.model.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmployeeRequest {

    private String name;
    private String  email;

    private Long department;
    private Set<Long> role;
    private boolean status;
}
