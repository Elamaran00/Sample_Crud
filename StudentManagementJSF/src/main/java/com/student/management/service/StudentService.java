package com.student.management.service;

import java.util.List;
import com.student.management.entity.Student;
import com.student.management.exception.StudentException;

public interface StudentService {
    void addStudent(Student student) throws StudentException;
    void updateStudent(Student student) throws StudentException;
    void deleteStudent(Long id) throws StudentException;
    Student getStudentById(Long id) throws StudentException;
    List<Student> getAllStudents() throws StudentException;
    List<Student> searchStudents(String query, String department, String status) throws StudentException;
    long getActiveStudentsCount() throws StudentException;
    long getInactiveStudentsCount() throws StudentException;
    long getTotalStudentsCount() throws StudentException;
    long getTotalDepartmentsCount() throws StudentException;
}
