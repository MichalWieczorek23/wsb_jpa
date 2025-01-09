package com.jpacourse.service.impl;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitBasicsDto;
import com.jpacourse.mapper.PatientMapper;
import com.jpacourse.mapper.VisitMapper;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.MedicalTreatmentEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PatientServiceImpl implements PatientService
{
    private final PatientDao patientDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public PatientServiceImpl(PatientDao patientDao) {
        this.patientDao = patientDao;
    }

    @Override
    public PatientTO findById(Long id) {
        final PatientEntity entity = patientDao.findOne(id);
        return PatientMapper.mapToTO(entity);
    }

    @Override
    public List<VisitBasicsDto> findAllVisitsByThePatientsId(Long id) {
        List<VisitBasicsDto> visitBasicsDtoList = new ArrayList<>();

        List<VisitEntity> visitEntityList = patientDao.findAllVisitsByPatientID(id);
        for (VisitEntity vel : visitEntityList) {
            visitBasicsDtoList.add(VisitMapper.mapToTO(vel));
        }
        return visitBasicsDtoList;
    }

    @Override
    public List<PatientTO> findAllPatients() {
        List<PatientEntity> entities = patientDao.findAll();
        List<PatientTO> patientTOS = new ArrayList<>();

        if (entities.isEmpty()){
            return null;
        }

        for (PatientEntity el : entities) {
            patientTOS.add(PatientMapper.mapToTO(el));
        }

        return patientTOS;
    }

    @Override
    public PatientTO createPatient(PatientEntity patientEntity) {
        PatientEntity entity = patientDao.findOne(patientEntity.getId());
        if (entity != null){
            throw new IllegalArgumentException("Patient has already DB ID, action is not permitted!");
        }

        return PatientMapper.mapToTO(patientDao.save(patientEntity));
    }

    @Override
    public PatientTO updatePatient(PatientEntity patientEntity) {
        PatientEntity entity = patientDao.findOne(patientEntity.getId());
        if (entity == null){
            throw new IllegalArgumentException("Patient does not exist in DB, action is not permitted!");
        }

        return PatientMapper.mapToTO(patientDao.save(patientEntity));
    }

    @Transactional
    @Override
    public void deletePatient(Long patientId) {
        PatientEntity patient = patientDao.findOne(patientId);
        if (patient == null) {
            throw new IllegalArgumentException("Patient does not exist");
        }

        if (patient.getVisitEntities() != null) {
            for (VisitEntity visit : patient.getVisitEntities()) {
                visit.setMedicalTreatmentEntities(null);
            }
        }

        patientDao.delete(patient);
    }
}
