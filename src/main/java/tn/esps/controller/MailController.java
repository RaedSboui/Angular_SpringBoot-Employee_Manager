package tn.esps.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esps.model.Mail;
import tn.esps.service.MailService;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class MailController {
	
	@Autowired
	MailService mailService;
	
	
	@GetMapping("mails")
	public ResponseEntity<List<Mail>> getAllMails() {
		List<Mail> mails = mailService.getAllMails();
		return new ResponseEntity<>(mails, HttpStatus.OK);
	}
	
	
	@GetMapping("mail/find/{id}")
	public ResponseEntity<Mail> getMail(@PathVariable("id") Long id) {
		Mail mail = mailService.getMailById(id);
		return new ResponseEntity<>(mail, HttpStatus.OK);
	}
	
	
	@PostMapping("mail/send")
	public ResponseEntity<Mail> sendMail(@RequestBody Mail mail) {
		Mail newMail = mailService.sendSimpleMail(mail);
		return new ResponseEntity<>(newMail, HttpStatus.OK);
	}
	
	
	@PostMapping("mail/sendwithattachment")
	public ResponseEntity<Mail> sendMailWithAttachments(@RequestBody Mail mail) throws IOException {
		Mail newMail = mailService.sendMailWithAttachments(mail);
		return new ResponseEntity<>(newMail, HttpStatus.OK);
	}
	
	
	@DeleteMapping("mail/delete/{id}")
	public ResponseEntity<Mail> deleteMail(@PathVariable("id") Long id) {
		mailService.deleteMail(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
