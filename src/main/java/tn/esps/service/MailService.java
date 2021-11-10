package tn.esps.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import tn.esps.exceptions.NotFoundException;
import tn.esps.model.Mail;
import tn.esps.repository.MailRepository;

@Service
public class MailService {
	
	@Autowired
	private MailRepository mailRepository;
	
	@Autowired
	private JavaMailSenderImpl javaMailSender;


	public Mail getMailById(Long id) {
		return mailRepository.findMailById(id)
				.orElseThrow(() -> new NotFoundException("Mail by id: " + id + ", not found!"));
	}

	public List<Mail> getAllMails() {
		return mailRepository.findAll();
	}

	public Mail sendSimpleMail(Mail mail) {

		try {
			// email params
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setTo(mail.getUser().getEmail());
			msg.setSubject(mail.getSubject());
			msg.setText(mail.getText());

			// send mail
			javaMailSender.getJavaMailProperties().put("mail.mime.charset", "UTF-8");
			javaMailSender.send(msg);

			// save mail
			return mailRepository.save(mail);
			
		} catch (MailException e) {
			throw new MailParseException(e);
		}
	}

	public Mail sendMailWithAttachments(Mail mail) {
		// email params
		MimeMessage message = javaMailSender.createMimeMessage();
		javaMailSender.getJavaMailProperties().put("mail.mime.charset", "UTF-8");

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setTo(mail.getUser().getEmail());
			helper.setFrom("raedsboui1995@gmail.tn");
			helper.setSubject(mail.getSubject());
			helper.setText(String.format(mail.getText()));

			try {
				URL url = new URL(mail.getJoinFile());
				String filename = new File(url.getPath().toString()).getName();
				File out = new File("C:\\Users\\lenovo\\Documents\\workspace-spring-tool-suite-4-4.12.0.RELEASE\\emplyeemanager\\src\\main\\resources\\Attachments" + filename);
				HttpURLConnection http = (HttpURLConnection) url.openConnection();
				double filesize = (double) http.getContentLength();
				BufferedInputStream in = new BufferedInputStream(http.getInputStream());
				FileOutputStream fos = new FileOutputStream(out);
				BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
				byte[] data = new byte[1024];
				double downloaded = 0.00;
				int read = 0;
				double percentDownloded = 0.00;

				while ((read = in.read(data, 0, 1024)) >= 0) {
					bout.write(data, 0, read);
					downloaded += read;
					percentDownloded = (downloaded * 100) / filesize;
					String percent = String.format("%.4f", percentDownloded);
					System.out.println("Downloaded: " + percent + " % of a file.");
				}
				bout.close();
				in.close();
				System.out.println("Download complete.");
				FileSystemResource fileAttach = new FileSystemResource("C:\\Users\\lenovo\\Documents\\workspace-spring-tool-suite-4-4.12.0.RELEASE\\emplyeemanager\\src\\main\\resources\\Attachments" + filename);
				helper.addAttachment(fileAttach.getFilename(), fileAttach);
				
			} catch (IOException ex) {
				ex.printStackTrace();
			}


		} catch (MessagingException e) {
			throw new MailParseException(e);
		}

		// send mail
		javaMailSender.send(message);

		// save mail
		return mailRepository.save(mail);
	}

	public void deleteMail(Long id) {
		mailRepository.deleteMailById(id);
	}

}
