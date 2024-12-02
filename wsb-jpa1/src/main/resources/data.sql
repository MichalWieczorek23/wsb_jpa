insert into address (address_id, address_line1, address_line2, city, postal_code)
            values (1, 'xx', 'yy', 'city', '62-030');
insert into address (address_id, address_line1, address_line2, city, postal_code)
            values (2, 'zz', 'yy', 'town', '26-303');
insert into doctor (doctor_id, doctor_number, email, first_name, last_name, specialization, telephone_number, address_id)
            values (1, '1', 'FrankSinatra@gmail.com', 'Frank', 'Sinatra', 'SURGEON', '+48693636258', 1);
insert into patient (patient_id, date_of_birth, email, first_name, last_name, patient_number, telephone_number, address_id)
            values (1, '2001-01-01', 'MikeScott@gmail.com', 'Mike', 'Scott', '1', '+48259684713', 2);
insert into visit (visit_id, description, time, patient_id, doctor_id)
            values (1, 'Lorem Ipsum', '2023-12-30 18:30:00', 1, 1);
insert into medical_treatment (medical_treatment_id, description, type, visit_id)
            values (1, 'Lorem Ipsum', 'USG',  1);