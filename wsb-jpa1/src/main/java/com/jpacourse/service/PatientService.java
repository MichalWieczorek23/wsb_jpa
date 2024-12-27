package com.jpacourse.service;


import com.jpacourse.dto.PatientTO;
import com.jpacourse.persistence.entity.PatientEntity;

import java.util.List;

public interface PatientService
{
    public PatientTO findById(final Long id);
    public List<PatientTO> findAllPatients();
    public PatientTO createPatient(PatientEntity patientEntity);
    public PatientTO updatePatient(PatientEntity patientEntity);
    public void deletePatient(Long patientId);
}
