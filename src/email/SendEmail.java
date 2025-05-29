package email;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class SendEmail {
    // Configuration SMTP (should be loaded from config file/environment)
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final int SMTP_PORT = 587;
    private static final String SMTP_USERNAME = System.getenv("EMAIL_USERNAME"); // Use environment variables
    private static final String SMTP_PASSWORD = System.getenv("EMAIL_PASSWORD"); // Use environment variables
    
    // Email configuration
    private static final String DEFAULT_FROM = System.getenv("EMAIL_FROM");
    private static final String DEFAULT_TO = System.getenv("EMAIL_TO");
    private static final String DAILY_SUBJECT = "🆕 Rappel Quotidien Buvette";
    
    private Timer timer;

    public void startDailyNotifications() {
        stopDailyNotifications(); // Stop if already running
        
        timer = new Timer("BuvetteEmailScheduler");
        
        // Calculate initial delay to next 8:00 AM
        Calendar now = Calendar.getInstance();
        Calendar nextRun = Calendar.getInstance();
        nextRun.set(Calendar.HOUR_OF_DAY, 8);
        nextRun.set(Calendar.MINUTE, 0);
        nextRun.set(Calendar.SECOND, 0);
        nextRun.set(Calendar.MILLISECOND, 0);
        
        if (now.after(nextRun)) {
            nextRun.add(Calendar.DAY_OF_MONTH, 1);
        }
        
        long initialDelay = nextRun.getTimeInMillis() - now.getTimeInMillis();
        
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                String content = "Bonjour!\n\nPensez à vérifier les nouveautés dans la buvette aujourd'hui!\n\nDate: " + date;
                
                envoyerEmail(DEFAULT_TO, DAILY_SUBJECT, content);
            }
        }, initialDelay, TimeUnit.DAYS.toMillis(1)); // Every 24 hours
        
        log("Service quotidien démarré. Prochain envoi à " + nextRun.getTime());
    }
    
    public void stopDailyNotifications() {
        if (timer != null) {
            timer.cancel();
            timer = null;
            log("Service quotidien arrêté.");
        }
    }

    public static void envoyerEmail(String destinataire, String email) {
        envoyerEmail(destinataire, "CHEK OUT THE NEW EXCITING THINGS", email);
    }

    public static void envoyerEmail(String destinataire, String sujet, String contenu) {
        if (SMTP_USERNAME == null || SMTP_PASSWORD == null) {
            logError("Les identifiants SMTP ne sont pas configurés");
            return;
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(DEFAULT_FROM));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire));
            message.setSubject(sujet);
            message.setText(contenu);

            Transport.send(message);
            log("Email envoyé à " + destinataire);

        } catch (MessagingException e) {
            logError("Échec d'envoi à " + destinataire + ": " + e.getMessage());
        }
    }

    private static void log(String message) {
        System.out.println("[BuvetteEmail] " + LocalDateTime.now() + " - " + message);
    }
    
    private static void logError(String message) {
        System.err.println("[BuvetteEmail-ERROR] " + LocalDateTime.now() + " - " + message);
    }

    public static void main(String[] args) {
        SendEmail service = new SendEmail();
        
        // Start daily notifications
        service.startDailyNotifications();
        
        // Keep the program running (better than Thread.sleep)
        Runtime.getRuntime().addShutdownHook(new Thread(service::stopDailyNotifications));
        
        // Simple way to keep the program running
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}