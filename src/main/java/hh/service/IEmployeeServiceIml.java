package hh.service;

import hh.Repository.IEmployeeRepo;
import hh.model.dto.request.EmployeeRequest;
import hh.model.dto.response.EmployeeResponse;
import hh.model.entity.Employee;
import hh.exception.CustomsException;
import hh.model.entity.RoleName;
import hh.service.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class IEmployeeServiceIml implements IGenericService<EmployeeResponse, EmployeeRequest, Long> {

    @Autowired
    private IEmployeeRepo iEmployeeRepo;

    @Autowired
    private EmployeeMapper employeeMapper;


    @Override
    public Page<EmployeeResponse> getEmployees(Pageable pageable, String employeeName) {
        if (StringUtils.hasText(employeeName)) {
            return iEmployeeRepo.findAllByNameContaining(employeeName, pageable)
                    .map(employeeMapper::toResponse);
        } else {
            return iEmployeeRepo.findAll(pageable)
                    .map(employeeMapper::toResponse);
        }
    }

    @Override
    public List<EmployeeResponse> getEmployees() {
        return iEmployeeRepo.findAll(PageRequest.of(0, 2)).stream()
                .map(e -> employeeMapper.toResponse(e)).collect(Collectors.toList());
    }

    @Override
    public EmployeeResponse save(EmployeeRequest employeeRequest) throws CustomsException {

        return employeeMapper.toResponse(iEmployeeRepo.save(employeeMapper.toEntity(employeeRequest)));
    }

    @Override
    public EmployeeResponse update(EmployeeRequest employeeRequest, Long aLong) throws CustomsException {
        Optional<Employee> check = iEmployeeRepo.findById(aLong);
        if (check.isPresent()) {
            Employee brand = employeeMapper.toEntity(employeeRequest);
            brand.setId(aLong);
            return employeeMapper.toResponse(iEmployeeRepo.save(brand));
        }
        throw new CustomsException("Employee not found");
    }

    @Override
    public EmployeeResponse findById(Long aLong) throws CustomsException {
        Optional<Employee> b = iEmployeeRepo.findById(aLong);
        if (b.isPresent()) {
            return employeeMapper.toResponse(b.get());
        }
        throw new CustomsException("Employee not found");

    }

    @Override
    public EmployeeResponse remove(Long aLong) throws CustomsException {
        Optional<Employee> brand = iEmployeeRepo.findById(aLong);
        if (brand.isPresent()) {
            iEmployeeRepo.deleteById(aLong);
            return employeeMapper.toResponse(brand.get());
        }
        throw new CustomsException("Employee not found");
    }
}
