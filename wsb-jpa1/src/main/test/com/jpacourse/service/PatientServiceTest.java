package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitBasicsDto;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.MedicalTreatmentEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.persistence.enums.TreatmentType;
import com.jpacourse.service.impl.PatientServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientServiceTest
{
    @Autowired
    private PatientService patientService;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    @Test
    public void testShouldFindPatientById(){
        PatientTO patientTO = patientService.findById(1L);

        assertNotNull(patientTO);
        // String temp = patientTO.getVisitBasicsDtos().get(0).getTime().toLocalDate().toString();
        assertEquals("2023-12-30", patientTO.getVisitBasicsDtos().get(0).getTime().toLocalDate().toString());
        assertEquals("Frank", patientTO.getVisitBasicsDtos().get(0).getDoctorFirstName());
        assertEquals("Sinatra", patientTO.getVisitBasicsDtos().get(0).getDoctorLastName());

        List<MedicalTreatmentEntity> tempMedList = patientTO.getVisitBasicsDtos().get(0).getMedicalTreatmentEntities();

        for (MedicalTreatmentEntity el : tempMedList) {
            assertNotNull(el.getType());
        }

        assertEquals(21.0, patientTO.getBMI(), 0.1);
    }

    @Transactional
    @Test
    public void testShouldDeletePatient(){
        // given
        PatientEntity patientEntity = entityManager.createQuery("SELECT  p FROM PatientEntity p", PatientEntity.class).getResultList().get(0);
        List<VisitEntity> visitEntityList = new ArrayList<>(patientEntity.getVisitEntities());
        List<DoctorEntity> doctorEntityTempList = entityManager.createQuery("SELECT  d FROM DoctorEntity d", DoctorEntity.class).getResultList();

        // when
        patientService.deletePatient(1L);

        //then
        assertNull(patientService.findById(1L));


    }
}
