package com.jpacourse.mapper;

import com.jpacourse.dto.VisitBasicsDto;
import com.jpacourse.persistence.entity.VisitEntity;

import java.util.ArrayList;

public final class VisitMapper
{
    public static VisitBasicsDto mapToTO(final VisitEntity visitEntity)
    {
        if (visitEntity == null)
        {
            return null;
        }
        final VisitBasicsDto visitBasicsDto = new VisitBasicsDto();
        visitBasicsDto.setId(visitEntity.getId());
        visitBasicsDto.setTime(visitEntity.getTime());
        visitBasicsDto.setDoctorFirstName(visitEntity.getDoctorEntity().getFirstName());
        visitBasicsDto.setDoctorLastName(visitEntity.getDoctorEntity().getLastName());
        visitBasicsDto.setMedicalTreatmentEntities(new ArrayList<>(visitEntity.getMedicalTreatmentEntities()));

        return visitBasicsDto;
    }

}
