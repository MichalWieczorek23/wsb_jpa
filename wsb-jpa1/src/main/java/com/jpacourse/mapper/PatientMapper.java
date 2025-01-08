package com.jpacourse.mapper;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitBasicsDto;
import com.jpacourse.persistence.entity.MedicalTreatmentEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;

import java.util.ArrayList;
import java.util.List;

public final class PatientMapper
{
    public static PatientTO mapToTO(final PatientEntity patientEntity)
    {
        if (patientEntity == null)
        {
            return null;
        }
        final PatientTO patientTO = new PatientTO();
        List<VisitBasicsDto> tempVisitBasicsDtoList = new ArrayList<>();

        List<VisitEntity> tempVisits = new ArrayList<>(patientEntity.getVisitEntities());
        for (VisitEntity el : tempVisits) {
            tempVisitBasicsDtoList.add(VisitMapper.mapToTO(el));
        }

        patientTO.setId(patientEntity.getId());
        patientTO.setVisitBasicsDtos(tempVisitBasicsDtoList);
        patientTO.setBMI(patientEntity.getBMI());

        return patientTO;
    }

//    public static PatientEntity mapToEntity(final PatientTO patientTO)
//    {
//        if(patientTO == null)
//        {
//            return null;
//        }
//        PatientEntity patientEntity = new PatientEntity();
//        patientEntity.setId(patientTO.getId());
//
//        List<VisitBasicsDto> tempVisitBasicsDtoList = new ArrayList<>(patientTO.getVisitEntityList());
//        List<VisitEntity> tempVisits = new ArrayList<>();
//
//        for (VisitBasicsDto el : tempVisitBasicsDtoList) {
//            VisitEntity tempVisitEntity = new VisitEntity();
//            tempVisitEntity.set
//            tempVisitBasicsDto.setTime(el.getTime());
//            tempVisitBasicsDto.setDoctorFirstName(el.getDoctorEntity().getFirstName());
//            tempVisitBasicsDto.setDoctorLastName(el.getDoctorEntity().getLastName());
//            List <MedicalTreatmentEntity> tempMedicalTList = new ArrayList<>(el.getMedicalTreatmentEntities());
//            tempVisitBasicsDto.setMedicalTreatmentEntities(tempMedicalTList);
//
//            tempVisitBasicsDtoList.add(tempVisitBasicsDto);
//        }
//
//        return patientEntity;
//    }
}
