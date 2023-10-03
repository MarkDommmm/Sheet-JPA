package hh.controller;


import hh.model.dto.request.EmployeeRequest;
import hh.model.dto.response.EmployeeResponse;
import hh.model.entity.Employee;
import hh.exception.CustomsException;

import hh.service.IEmployeeServiceIml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    @Autowired
    private IEmployeeServiceIml employeeServiceIml;

    @GetMapping("/search")
    public ResponseEntity<Page<EmployeeResponse>> searchEmployee(
            @PageableDefault(page = 0, size = 3) Pageable pageable,
            @RequestParam(value = "search", defaultValue = "") String search
    ) {
        Page<EmployeeResponse> employeePage = employeeServiceIml.getEmployees(pageable, search);
        return new ResponseEntity<>(employeePage, HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<List<EmployeeResponse>> getEmployees() {
        return new ResponseEntity<>(employeeServiceIml.getEmployees(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeesById(@PathVariable Long id) throws CustomsException {
        return new ResponseEntity<>(employeeServiceIml.findById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> addEmployee(@RequestBody EmployeeRequest employee) throws CustomsException {
        employeeServiceIml.save(employee);
        return new ResponseEntity<>("Create Employee Success!!!", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequest employee) throws CustomsException {
        employeeServiceIml.update(employee, id);
        return new ResponseEntity<>("Update employee success!!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) throws CustomsException {
        employeeServiceIml.remove(id);
        return new ResponseEntity<>("Delete employee success!!!", HttpStatus.OK);
    }
}
