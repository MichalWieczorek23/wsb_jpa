insert into address (address_id, address_line1, address_line2, city, postal_code)
            values (1, 'x', 'y', 'city1', '62-030');
insert into address (address_id, address_line1, address_line2, city, postal_code)
            values (2, 'xx', 'yy', 'city2', '62-303');
insert into address (address_id, address_line1, address_line2, city, postal_code)
            values (3, 'xxx', 'yyy', 'city3', '26-030');

insert into address (address_id, address_line1, address_line2, city, postal_code)
            values (4, 'z', 'y', 'town', '50-303');
insert into address (address_id, address_line1, address_line2, city, postal_code)
            values (5, 'zz', 'yy', 'town', '51-303');
insert into address (address_id, address_line1, address_line2, city, postal_code)
            values (6, 'zzz', 'yyy', 'town', '52-303');

insert into doctor (doctor_id, doctor_number, email, first_name, last_name, specialization, telephone_number, address_id)
            values (1, '1', 'FrankSinatra@gmail.com', 'Frank', 'Sinatra', 'SURGEON', '+48693636258', 1);
insert into doctor (doctor_id, doctor_number, email, first_name, last_name, specialization, telephone_number, address_id)
            values (2, '2', 'FranzKafka@gmail.com', 'Franz', 'Kafka', 'DERMATOLOGIST', '+48659821475', 2);
insert into doctor (doctor_id, doctor_number, email, first_name, last_name, specialization, telephone_number, address_id)
            values (3, '3', 'MonicaBelluci@gmail.com', 'Monica', 'Belluci', 'OCULIST', '+48256938741', 3);

insert into patient (patient_id, date_of_birth, email, first_name, last_name, patient_number, telephone_number, address_id)
            values (1, '2001-01-01', 'MikeScott@gmail.com', 'Mike', 'Scott', '1', '+48259684713', 4);
insert into patient (patient_id, date_of_birth, email, first_name, last_name, patient_number, telephone_number, address_id)
            values (2, '2011-01-01', 'MikeBudd@gmail.com', 'Mike', 'Buddweiser', '2', '+48596582321', 5);
insert into patient (patient_id, date_of_birth, email, first_name, last_name, patient_number, telephone_number, address_id)
            values (3, '2012-01-01', 'MichelAngelo@gmail.com', 'Michel', 'Angello', '3', '+48596325874', 6);

-- Test czy relacja na pewno jest 1 do 1 (sfailuje jeżeli będzie odkomentowane)
--insert into doctor (doctor_id, doctor_number, email, first_name, last_name, specialization, telephone_number, address_id)
--            values (3, '1', 'FrankSinatra-v2@gmail.com', 'Frank2', 'Sinatra2', 'SURGEON', '+48693636258', 1);
--insert into patient (patient_id, date_of_birth, email, first_name, last_name, patient_number, telephone_number, address_id)
--            values (4, '2001-01-01', 'MikeScott-v2@gmail.com', 'Mike2', 'Scott2', '1', '+48259684713', 2);


insert into visit (visit_id, description, time, patient_id, doctor_id)
            values (1, 'Kontrolna wizyta', '2023-12-30 18:30:00', 1, 1);
insert into visit (visit_id, description, time, patient_id, doctor_id)
            values (2, 'Kontrolna wizyta', '2021-12-13 19:43:00', 2, 2);
insert into visit (visit_id, description, time, patient_id, doctor_id)
            values (3, 'Kontrolna wizyta', '2021-12-03 18:52:00', 3, 3);

insert into medical_treatment (medical_treatment_id, description, type, visit_id)
            values (1, 'Badanie ciśnienia krwi', 'USG',  1);
insert into medical_treatment (medical_treatment_id, description, type, visit_id)
            values (2, 'Badanie serca', 'EKG',  1);
insert into medical_treatment (medical_treatment_id, description, type, visit_id)
            values (3, 'Zdjecie zlamania piszczela', 'RTG',  2);
insert into medical_treatment (medical_treatment_id, description, type, visit_id)
            values (4, 'Zdjecie szczeki', 'RTG',  3);