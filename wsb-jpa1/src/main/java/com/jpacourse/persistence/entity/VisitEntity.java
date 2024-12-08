package com.jpacourse.persistence.entity;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.*;

@Entity
@Table(name = "VISIT")
public class VisitEntity {

	// Relacja dwustronna
	// Doktor jest nullowalny żeby oddać pełną kontrolę pacjentowi do zarządzania swoimi danymi
	// W ten sposób usunięcie danych lekarza nie wymusza usunięcia medycznych danych pajenta
	@ManyToOne(
			fetch = FetchType.LAZY
	)
	@JoinColumn(name = "DOCTOR_ID", nullable = true)
	private DoctorEntity doctorEntity;

	// Relacja jednostronna ze strony rodzica
	@OneToMany(
			fetch = FetchType.LAZY,
			orphanRemoval = true
	)
	@JoinColumn(name = "VISIT_ID")
	private Collection<MedicalTreatmentEntity> medicalTreatmentEntities;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "VISIT_ID")
	private Long id;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private LocalDateTime time;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

}
