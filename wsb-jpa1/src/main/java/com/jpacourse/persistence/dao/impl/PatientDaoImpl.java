package com.jpacourse.persistence.dao.impl;

import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao {

    @PersistenceContext
    private EntityManager entityManager;

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
    public List<PatientEntity> findPatientsByLastName(String lastName){
        return entityManager.createQuery("select pat from PatientEntity pat " +
                "where pat.lastName = :lname ", PatientEntity.class)
                .setParameter("lname", lastName)
                .getResultList();
    }

    @Override
    public List<VisitEntity> findAllVisitsByPatientID(Long patientID){
        return new ArrayList<>();
    }

    @Override
    public List<PatientEntity> findPatientsWithMoreVisitsThan(int numberOfVisits){
        return new ArrayList<>();
    }

    @Override
    public List<PatientEntity> findPatientsByBMI(double BMI){
        return new ArrayList<>();
    }
}
