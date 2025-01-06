package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import java.util.List;

import java.time.LocalDateTime;

public interface PatientDao extends Dao<PatientEntity, Long> {
    void addVisitToPatient(
        Long patientId,
        Long doctorId,
        LocalDateTime visitDate,
        String visitDescription
    );

    List<PatientEntity> findPatientsByLastName(String lastName);
    List<VisitEntity> findAllVisitsByPatientID(Long patientID);
    List<PatientEntity> findPatientsWithMoreVisitsThan(int numberOfVisits);
    List<PatientEntity> findPatientsByBMI(double BMI);
}