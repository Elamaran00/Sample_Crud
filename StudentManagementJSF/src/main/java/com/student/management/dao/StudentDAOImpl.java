package com.student.management.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.student.management.entity.Student;
import com.student.management.exception.StudentException;
import com.student.management.util.JPAUtil;

public class StudentDAOImpl implements StudentDAO {

    @Override
    public void save(Student student) throws StudentException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(student);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new StudentException("Error saving student", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Student student) throws StudentException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(student);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new StudentException("Error updating student", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Long id) throws StudentException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            Student student = em.find(Student.class, id);
            if (student != null) {
                em.remove(student);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new StudentException("Error deleting student", e);
        } finally {
            em.close();
        }
    }

    @Override
    public Student findById(Long id) throws StudentException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.find(Student.class, id);
        } catch (Exception e) {
            throw new StudentException("Error finding student by id", e);
        } finally {
            em.close();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Student> findAll() throws StudentException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT s FROM Student s ORDER BY s.id DESC").getResultList();
        } catch (Exception e) {
            throw new StudentException("Error retrieving students", e);
        } finally {
            em.close();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Student> searchStudents(String query, String department, String status) throws StudentException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            StringBuilder sb = new StringBuilder("SELECT s FROM Student s WHERE 1=1 ");
            List<Object> params = new ArrayList<>();
            int paramIndex = 1;

            if (query != null && !query.trim().isEmpty()) {
                sb.append(" AND (LOWER(s.firstName) LIKE ?").append(paramIndex)
                  .append(" OR LOWER(s.lastName) LIKE ?").append(paramIndex)
                  .append(" OR LOWER(s.studentId) LIKE ?").append(paramIndex)
                  .append(" OR LOWER(s.email) LIKE ?").append(paramIndex).append(") ");
                params.add("%" + query.trim().toLowerCase() + "%");
                paramIndex++;
            }

            if (department != null && !department.trim().isEmpty()) {
                sb.append(" AND s.department = ?").append(paramIndex).append(" ");
                params.add(department);
                paramIndex++;
            }

            if (status != null && !status.trim().isEmpty()) {
                sb.append(" AND s.status = ?").append(paramIndex).append(" ");
                params.add(status);
                paramIndex++;
            }

            sb.append(" ORDER BY s.id DESC");

            Query q = em.createQuery(sb.toString());
            for (int i = 0; i < params.size(); i++) {
                q.setParameter(i + 1, params.get(i));
            }

            return q.getResultList();
        } catch (Exception e) {
            throw new StudentException("Error searching students", e);
        } finally {
            em.close();
        }
    }

    @Override
    public long countStudentsByStatus(String status) throws StudentException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            if (status == null) {
                return (long) em.createQuery("SELECT COUNT(s) FROM Student s").getSingleResult();
            } else {
                Query q = em.createQuery("SELECT COUNT(s) FROM Student s WHERE s.status = :status");
                q.setParameter("status", status);
                return (long) q.getSingleResult();
            }
        } catch (Exception e) {
            throw new StudentException("Error counting students", e);
        } finally {
            em.close();
        }
    }

    @Override
    public long countDepartments() throws StudentException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return (long) em.createQuery("SELECT COUNT(DISTINCT s.department) FROM Student s").getSingleResult();
        } catch (Exception e) {
            throw new StudentException("Error counting departments", e);
        } finally {
            em.close();
        }
    }
}
