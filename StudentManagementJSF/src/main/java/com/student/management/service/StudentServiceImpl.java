package com.student.management.service;

import java.util.List;

import com.student.management.dao.StudentDAO;
import com.student.management.dao.StudentDAOImpl;
import com.student.management.entity.Student;
import com.student.management.exception.StudentException;

public class StudentServiceImpl implements StudentService {

    private StudentDAO studentDAO;

    public StudentServiceImpl() {
        this.studentDAO = new StudentDAOImpl();
    }

    @Override
    public void addStudent(Student student) throws StudentException {
        if (student == null) {
            throw new StudentException("Student data is missing");
        }
        studentDAO.save(student);
    }

    @Override
    public void updateStudent(Student student) throws StudentException {
        if (student == null || student.getId() == null) {
            throw new StudentException("Invalid student for update");
        }
        studentDAO.update(student);
    }

    @Override
    public void deleteStudent(Long id) throws StudentException {
        if (id == null) {
            throw new StudentException("Student ID is missing");
        }
        studentDAO.delete(id);
    }

    @Override
    public Student getStudentById(Long id) throws StudentException {
        if (id == null) {
            throw new StudentException("Student ID is missing");
        }
        return studentDAO.findById(id);
    }

    @Override
    public List<Student> getAllStudents() throws StudentException {
        return studentDAO.findAll();
    }

    @Override
    public List<Student> searchStudents(String query, String department, String status) throws StudentException {
        return studentDAO.searchStudents(query, department, status);
    }

    @Override
    public long getActiveStudentsCount() throws StudentException {
        return studentDAO.countStudentsByStatus("ACTIVE");
    }

    @Override
    public long getInactiveStudentsCount() throws StudentException {
        return studentDAO.countStudentsByStatus("INACTIVE");
    }

    @Override
    public long getTotalStudentsCount() throws StudentException {
        return studentDAO.countStudentsByStatus(null);
    }

    @Override
    public long getTotalDepartmentsCount() throws StudentException {
        return studentDAO.countDepartments();
    }
}
