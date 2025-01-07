package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitBasicsDto;
import com.jpacourse.persistence.dao.AddressDao;
import com.jpacourse.persistence.dao.DoctorDao;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.dao.VisitDao;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientServiceTest
{
    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private DoctorDao doctorDao;

    @Autowired
    private VisitDao visitDao;

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
    public void testFindAllVisitsByPatientID() {
        PatientTO patientTO = patientService.findById(1L);
        List<Long> actualVisitsIdx = new ArrayList<>();
        List<Long> returnedVisitsIdx = new ArrayList<>();
        for (VisitBasicsDto loopVisit : patientTO.getVisitBasicsDtos()) {
            actualVisitsIdx.add(loopVisit.getId());
        }

        List<VisitEntity> visitEntityList = patientDao.findAllVisitsByPatientID(1L);
//        System.out.println(visitEntityList.toString());

        for (VisitEntity loopVisit : visitEntityList) {
            returnedVisitsIdx.add(loopVisit.getId());
        }
        assertThat(returnedVisitsIdx.size()).isEqualTo(actualVisitsIdx.size());
        for (Long loopVisitIdx : actualVisitsIdx) {
            returnedVisitsIdx.remove(loopVisitIdx);
        }
        assertThat(returnedVisitsIdx.size()).isEqualTo(0);
    }

    @Transactional
    @Test
    public void testShouldDeletePatient() {
        // given
        List<DoctorEntity> doctorEntityTempList = doctorDao.findAll();
        List<PatientEntity> patientEntities = patientDao.findAll();

        PatientTO patientTO = patientService.findById(1L);
        assertNotNull(patientTO);

        PatientEntity patientEntity = patientDao.findOne(1L);

        List<VisitEntity> visitEntityList = new ArrayList<>(patientEntity.getVisitEntities());

        System.out.println(patientService.findById(1L).getId());

        // when
        patientService.deletePatient(1L);

        // then
        assertNull(patientService.findById(1L));

        List<PatientEntity> patientEntitiesAfter = patientDao.findAll();
        List<DoctorEntity> doctorEntityTempListAfter = doctorDao.findAll();
        List<VisitEntity> allVisitEntityListAfter = visitDao.findAll();

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
