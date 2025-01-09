package com.jpacourse.persistance.dao;

import com.jpacourse.persistence.dao.MedicalTreatmentDao;
import com.jpacourse.persistence.entity.MedicalTreatmentEntity;
import com.jpacourse.persistence.enums.TreatmentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class MedicalTreatmentDaoTest {

    @Autowired
    private MedicalTreatmentDao medicalTreatmentDao;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TransactionTemplate transactionTemplate;


    @Test
    public void testOptimisticLockWithSeparateTransactions() {

        Long existingId = transactionTemplate.execute(status -> {
            MedicalTreatmentEntity medicalTreatment = new MedicalTreatmentEntity();
            medicalTreatment.setDescription("Initial desc");
            medicalTreatment.setType(TreatmentType.RTG);
            medicalTreatmentDao.save(medicalTreatment);
            return medicalTreatment.getId();
        });

        MedicalTreatmentEntity medicalTreatment1 = transactionTemplate.execute(
                status -> medicalTreatmentDao.findOne(existingId)
        );
        MedicalTreatmentEntity medicalTreatment2 = transactionTemplate.execute(
                status -> medicalTreatmentDao.findOne(existingId)
        );

        assertNotNull(medicalTreatment1);
        assertNotNull(medicalTreatment2);

        medicalTreatment2.setDescription("v2 description");
        medicalTreatment1.setDescription("v1 description");

        transactionTemplate.execute(status -> {
            medicalTreatmentDao.update(medicalTreatment2);
            return null;
        });

        // Test 1 -> wrapped OptimisticLockException error
        assertThrows(ObjectOptimisticLockingFailureException.class, () -> {
            transactionTemplate.execute(status -> {
                medicalTreatmentDao.update(medicalTreatment1);
                return null;
            });
        });

        // Test 2 -> direct OptimisticLockException error
        assertThrows(OptimisticLockException.class, () -> {
            transactionTemplate.execute(status -> {
                entityManager.merge(medicalTreatment1);
                return null;
            });
        });

        medicalTreatmentDao.delete(existingId);
    }
}