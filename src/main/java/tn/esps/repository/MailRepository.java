package tn.esps.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esps.model.Mail;

public interface MailRepository extends JpaRepository<Mail, Long>{

	Optional<Mail> findMailById(Long id);

	@Transactional
	void deleteMailById(Long id);

}
