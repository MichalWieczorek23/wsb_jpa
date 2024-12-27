package com.jpacourse.dto;

import java.io.Serializable;
import java.util.List;

public class PatientTO implements Serializable
{
    private Long id;
    private List<VisitBasicsDto> visitBasicsDtos;
    private double BMI;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<VisitBasicsDto> getVisitBasicsDtos() {
        return visitBasicsDtos;
    }

    public void setVisitBasicsDtos(List<VisitBasicsDto> visitBasicsDtos) {
        this.visitBasicsDtos = visitBasicsDtos;
    }

    public double getBMI() {
        return BMI;
    }

    public void setBMI(double BMI) {
        this.BMI = BMI;
    }
}
