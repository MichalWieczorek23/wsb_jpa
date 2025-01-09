package com.jpacourse.dto;

import com.jpacourse.persistence.entity.MedicalTreatmentEntity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

// ! Czy klasa musi byc Serializable
public class VisitBasicsDto implements Serializable
{
    private Long id;
    private LocalDateTime time;
    private String doctorFirstName;
    private String doctorLastName;
    private List<String> medicalTreatments;

    public LocalDateTime getTime() {
        return time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getDoctorFirstName() {
        return doctorFirstName;
    }

    public void setDoctorFirstName(String doctorFirstName) {
        this.doctorFirstName = doctorFirstName;
    }

    public String getDoctorLastName() {
        return doctorLastName;
    }

    public void setDoctorLastName(String doctorLastName) {
        this.doctorLastName = doctorLastName;
    }

    public List<String> getMedicalTreatmentEntities() {
        return medicalTreatments;
    }

    public void setMedicalTreatmentEntities(List<String> medicalTreatments) {
        this.medicalTreatments = medicalTreatments;
    }
}
