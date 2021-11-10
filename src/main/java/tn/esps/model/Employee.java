package tn.esps.model;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "t_employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, updatable = false)
	private Long id;

	@NotBlank
	@Size(max = 50)
	private String name;

	@NotBlank
	@Size(min = 8, max = 30)
	private String jobTitle;

	@NotBlank
	@Size(min = 11, max = 15)
	private String phone;

	@NotBlank
	private String imageUrl;

	@Column(nullable = false, updatable = false)
	private String employeeCode;
	
	@NotBlank
	private Date joinDate;
	
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private User user;

}
