package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.AddressEntity;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.persistence.enums.Specialization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class PatientDaoTest {

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private DoctorDao doctorDao;

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

        addressDao.save(addressEntity);

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
}
