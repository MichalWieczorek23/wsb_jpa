package com.jpacourse.persistence.dao.impl;

import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao {

    @Override
    @Transactional
    public void addVisitToPatient(Long patientId, Long doctorId, LocalDateTime visitDate, String visitDescription) {
        PatientEntity patient = entityManager.find(PatientEntity.class, patientId);
        if (patient == null) {
            throw new IllegalArgumentException("Patient with ID " + patientId + " not found.");
        }

        DoctorEntity doctor = entityManager.find(DoctorEntity.class, doctorId);
        if (doctor == null) {
            throw new IllegalArgumentException("Doctor with ID " + doctorId + " not found.");
        }

        VisitEntity visit = new VisitEntity();
        visit.setDoctorEntity(doctor);
        visit.setTime(visitDate);
        visit.setDescription(visitDescription);
        ArrayList<VisitEntity> visitEntities = null;
        if(patient.getVisitEntities() != null) {
            visitEntities = new ArrayList<VisitEntity>(patient.getVisitEntities());
        }
        else {
            visitEntities = new ArrayList<>();
        }

        visitEntities.add(visit);

        patient.setVisitEntities(visitEntities);

        entityManager.merge(patient);
    }

    @Override
    public List<PatientEntity> findPatientsRegisteredAtClinicAfterDate(LocalDate dateOfRegistrationAtClinic) {
        return entityManager.createQuery(
                        "select pat from PatientEntity pat " +
                                "where pat.dateOfRegistrationAtClinic > :registeredAfterDate " +
                                "order by pat.dateOfRegistrationAtClinic ASC",
                        PatientEntity.class)
                .setParameter("registeredAfterDate", dateOfRegistrationAtClinic)
                .getResultList();
    }

    @Override
    public List<PatientEntity> findPatientsByLastName(String lastName){
        return entityManager.createQuery("select pat from PatientEntity pat " +
                "where pat.lastName = :lname ", PatientEntity.class)
                .setParameter("lname", lastName)
                .getResultList();
    }

    @Override
    public List<VisitEntity> findAllVisitsByPatientID(Long patientID){
        return entityManager.createQuery("select visit from PatientEntity pat " +
        "join pat.visitEntities visit where pat.id = :pid ", VisitEntity.class)
        .setParameter("pid", patientID)
        .getResultList();
    }

    @Override
    public List<PatientEntity> findPatientsWithMoreVisitsThan(int numberOfVisits){
        return entityManager.createQuery("select pat from PatientEntity pat " +
                "join pat.visitEntities visits " +
                "group by pat " +
                "having count (visits) > :num ", PatientEntity.class)
                .setParameter("num", Long.valueOf(numberOfVisits))
                .getResultList();
    }

    @Override
    public List<PatientEntity> findPatientsWithBMILowerThan(double BMI){
        return entityManager.createQuery("select pat from PatientEntity pat " +
                "where pat.BMI < :num ", PatientEntity.class)
                .setParameter("num", BMI)
                .getResultList();
    }
}
