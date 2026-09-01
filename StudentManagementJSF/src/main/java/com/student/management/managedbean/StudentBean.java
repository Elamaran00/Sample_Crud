package com.student.management.managedbean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.student.management.entity.Student;
import com.student.management.service.StudentService;
import com.student.management.service.StudentServiceImpl;
import com.student.management.exception.StudentException;

@ManagedBean(name = "studentBean")
@ViewScoped
public class StudentBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private StudentService studentService;
    private Student currentStudent;
    private List<Student> studentsList;
    private String searchQuery;
    private String filterDepartment;
    private String filterStatus;
    
    // Dashboard metrics
    private long totalStudents;
    private long activeStudents;
    private long inactiveStudents;
    private long totalDepartments;

    @PostConstruct
    public void init() {
        studentService = new StudentServiceImpl();
        currentStudent = new Student();
        loadStudents();
        loadDashboardMetrics();
    }

    public void loadStudents() {
        try {
            studentsList = studentService.searchStudents(searchQuery, filterDepartment, filterStatus);
        } catch (StudentException e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
        }
    }

    public void loadDashboardMetrics() {
        try {
            totalStudents = studentService.getTotalStudentsCount();
            activeStudents = studentService.getActiveStudentsCount();
            inactiveStudents = studentService.getInactiveStudentsCount();
            totalDepartments = studentService.getTotalDepartmentsCount();
        } catch (StudentException e) {
            System.err.println("Error loading metrics: " + e.getMessage());
        }
    }

    public void search() {
        loadStudents();
    }

    public String saveStudent() {
        try {
            if (currentStudent.getId() == null) {
                studentService.addStudent(currentStudent);
                addMessage(FacesMessage.SEVERITY_INFO, "Success", "Student added successfully.");
            } else {
                studentService.updateStudent(currentStudent);
                addMessage(FacesMessage.SEVERITY_INFO, "Success", "Student updated successfully.");
            }
            return "students?faces-redirect=true";
        } catch (StudentException e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
            return null;
        }
    }

    public String prepareEdit(Long id) {
        try {
            currentStudent = studentService.getStudentById(id);
            return "student-edit?faces-redirect=true";
        } catch (StudentException e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
            return null;
        }
    }

    public String viewStudent(Long id) {
        try {
            currentStudent = studentService.getStudentById(id);
            return "student-details?faces-redirect=true";
        } catch (StudentException e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
            return null;
        }
    }

    public void deleteStudent(Long id) {
        try {
            studentService.deleteStudent(id);
            addMessage(FacesMessage.SEVERITY_INFO, "Success", "Student deleted successfully.");
            loadStudents();
            loadDashboardMetrics();
        } catch (StudentException e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
        }
    }

    public String prepareCreate() {
        currentStudent = new Student();
        return "student-form?faces-redirect=true";
    }

    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }

    // Getters and Setters
    public Student getCurrentStudent() { return currentStudent; }
    public void setCurrentStudent(Student currentStudent) { this.currentStudent = currentStudent; }
    public List<Student> getStudentsList() { return studentsList; }
    public void setStudentsList(List<Student> studentsList) { this.studentsList = studentsList; }
    public String getSearchQuery() { return searchQuery; }
    public void setSearchQuery(String searchQuery) { this.searchQuery = searchQuery; }
    public String getFilterDepartment() { return filterDepartment; }
    public void setFilterDepartment(String filterDepartment) { this.filterDepartment = filterDepartment; }
    public String getFilterStatus() { return filterStatus; }
    public void setFilterStatus(String filterStatus) { this.filterStatus = filterStatus; }
    public long getTotalStudents() { return totalStudents; }
    public long getActiveStudents() { return activeStudents; }
    public long getInactiveStudents() { return inactiveStudents; }
    public long getTotalDepartments() { return totalDepartments; }
}
