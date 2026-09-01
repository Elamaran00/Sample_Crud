package com.student.management.dao;

import java.util.List;
import com.student.management.entity.Student;
import com.student.management.exception.StudentException;

public interface StudentDAO {
    void save(Student student) throws StudentException;
    void update(Student student) throws StudentException;
    void delete(Long id) throws StudentException;
    Student findById(Long id) throws StudentException;
    List<Student> findAll() throws StudentException;
    List<Student> searchStudents(String query, String department, String status) throws StudentException;
    long countStudentsByStatus(String status) throws StudentException;
    long countDepartments() throws StudentException;
}
