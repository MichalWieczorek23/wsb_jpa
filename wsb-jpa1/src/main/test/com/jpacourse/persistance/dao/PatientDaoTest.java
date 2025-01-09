package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.dao.impl.PatientDaoImpl;
import com.jpacourse.persistence.entity.AddressEntity;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.persistence.enums.Specialization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@SpringBootTest
public class PatientDaoTest {
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private DoctorDao doctorDao;

    @Transactional
    @Test
    public void testAddVisitToPatient() {
        // given
        DoctorEntity doctor = new DoctorEntity();
        doctor.setDoctorNumber("Doc-004");
        doctor.setEmail("magic.doktor@gmail.com");
        doctor.setFirstName("Antoni");
        doctor.setLastName("Morriartii");
        doctor.setTelephoneNumber("+485634230423");
        doctor.setSpecialization(Specialization.SURGEON);

        doctorDao.save(doctor);

        System.out.println("Created doctor: "+doctor.getId().toString());

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddressLine1("line1");
        addressEntity.setAddressLine2("line2");
        addressEntity.setCity("City1");
        addressEntity.setPostalCode("66-666");

        PatientEntity patient = new PatientEntity();
        patient.setFirstName("Jan");
        patient.setLastName("Kowalski");
        patient.setDateOfBirth(LocalDate.parse("1995-01-01"));
        patient.setBMI(31);
        patient.setEmail("jan.kowalski@gmail.com");
        patient.setTelephoneNumber("+48762334100");
        patient.setAddress(addressEntity);
        patient.setPatientNumber("P-1001");

        patientDao.save(patient);

        Long patientId = patient.getId();
        Long doctorId = doctor.getId();
        LocalDateTime visitDate = LocalDateTime.parse("2025-01-01T10:15:00");
        String visitDescription = "Routine check-up";

        // When
        patientDao.addVisitToPatient(patientId, doctorId, visitDate, visitDescription);

        // Then

        PatientEntity updatedPatient = patientDao.findOne(patientId);
        assertThat(updatedPatient).isNotNull();
        assertThat(updatedPatient.getVisitEntities()).hasSize(1);

        List<VisitEntity> visitEntityList = new ArrayList<>(updatedPatient.getVisitEntities());
        assertThat(visitEntityList.size()).isGreaterThan(0);

        VisitEntity visit = visitEntityList.get(0);
        assertThat(visit.getDoctorEntity().getId()).isEqualTo(doctorId);
        assertThat(visit.getTime()).isEqualTo(visitDate);
        assertThat(visit.getDescription()).isEqualTo(visitDescription);
    }

    @Transactional
    @Test
    public void testFindPatientsByLastName() {
        //given
        // 2 Scotts defined in SQL file

        //when
        List<PatientEntity> patientEntityList = patientDao.findPatientsByLastName("Scott");

        //then
        assertThat(patientEntityList.size()).isEqualTo(2);
        for (PatientEntity loopPatient : patientEntityList) {
            assertThat(loopPatient.getLastName()).isEqualTo("Scott");
        }
    }

    @Transactional
    @Test
    public void testFindPatientsWithMoreVisitsThan() {
        List<PatientEntity> patientEntityList = patientDao.findPatientsWithMoreVisitsThan(2);

        assertThat(patientEntityList.size()).isEqualTo(1);
        assertThat(patientEntityList.get(0).getId()).isEqualTo(1L);
        for (PatientEntity patient : patientEntityList) {
            assertThat(patient.getVisitEntities().size()).isGreaterThan(1);
        }
    }

    @Transactional
    @Test
    public void testFindPatientsWithBmiAbove() {
        List<PatientEntity> patientEntityList = patientDao.findPatientsWithBmiGraterThen(21);

        assertThat(patientEntityList.size()).isEqualTo(2);
        for (PatientEntity patient : patientEntityList) {
            assertThat(patient.getBMI()).isGreaterThan(21);
        }
    }

    @Transactional
    @Test
    public void testFindPatientsWithBMILowerThan() {
        List<PatientEntity> patientEntityList = patientDao.findPatientsWithBMILowerThan(25.0);
        System.out.println(patientEntityList);

        for (PatientEntity patient : patientEntityList) {
            assertThat(patient.getBMI()).isLessThan(25.0);
        }
    }
}
