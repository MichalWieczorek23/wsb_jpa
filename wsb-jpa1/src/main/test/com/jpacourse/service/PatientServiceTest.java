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
import java.time.LocalDate;
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
    public void testShouldDeletePatient() {
        // given
        List<DoctorEntity> doctorEntityTempList = entityManager.createQuery("SELECT d FROM DoctorEntity d", DoctorEntity.class).getResultList();
        List<PatientEntity> patientEntities = entityManager.createQuery("SELECT p FROM PatientEntity p", PatientEntity.class).getResultList();

        PatientTO patientTO = patientService.findById(1L);
        assertNotNull(patientTO);

        PatientEntity patientEntity = entityManager.createQuery("SELECT p FROM PatientEntity p", PatientEntity.class).getResultList().get(0);
        List<VisitEntity> visitEntityList = new ArrayList<>(patientEntity.getVisitEntities());

        System.out.println(patientService.findById(1L).getId());

        // when
        patientService.deletePatient(1L);

        // then
        assertNull(patientService.findById(1L));


        List<PatientEntity> patientEntitiesAfter = entityManager.createQuery("SELECT p FROM PatientEntity p", PatientEntity.class).getResultList();
        List<DoctorEntity> doctorEntityTempListAfter = entityManager.createQuery("SELECT d FROM DoctorEntity d", DoctorEntity.class).getResultList();
        List<VisitEntity> allVisitEntityListAfter = entityManager.createQuery("SELECT v FROM VisitEntity v", VisitEntity.class).getResultList();

        assertEquals( "The number of patients should decrease by exactly by 1 after removing patient", patientEntities.size(), patientEntitiesAfter.size()+1);

        assertEquals( "The number of doctors should remain the same", doctorEntityTempList.size(), doctorEntityTempListAfter.size());
        for (DoctorEntity doctor : doctorEntityTempList) {
            assertTrue("DoctorEntity should still exist: " + doctor.getId(), doctorEntityTempListAfter.contains(doctor));
        }

        for (VisitEntity visit : visitEntityList) {
            assertFalse("VisitEntity related to deleted patient should be removed: " + visit.getId(), allVisitEntityListAfter.contains(visit));
        }
    }
}
