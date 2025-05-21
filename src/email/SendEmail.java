package email ;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 * Service complet de notification email pour la buvette
 */
public class SendEmail {

    // Configuration SMTP
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final int SMTP_PORT = 587;
    private static final String SMTP_USERNAME = "najiy514@gmail.com";
    private static final String SMTP_PASSWORD = "mfmm oxpp tfmg pdsj";
    
    // Configuration des emails
    private static final String DEFAULT_FROM = "najiy514@gmail.com";
    private static final String DEFAULT_TO = "mustaphabennasser8@gmail.com";
    private static final String DAILY_SUBJECT = "🆕 Rappel Quotidien Buvette";
    
    private Timer timer;

    // Méthode pour l'envoi quotidien automatique
    public void startDailyNotifications() {
        stopDailyNotifications(); // Arrêter si déjà en cours
        
        timer = new Timer("BuvetteEmailScheduler", true);
        
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                String content = "Bonjour!\n\nPensez à vérifier les nouveautés dans la buvette aujourd'hui!\n\nDate: " + date;
                
                envoyerEmail(DEFAULT_TO, DAILY_SUBJECT, content);
            }
        }, 0, 24 * 60 * 60 * 1000); // Toutes les 24h
        
        log("Service quotidien démarré. Prochain envoi dans 24h.");
    }
    
    public void stopDailyNotifications() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            log("Service quotidien arrêté.");
        }
    }

    // Méthode d'envoi d'email à la demande (version statique)
    public static void envoyerEmail(String destinataire, String email) {
        envoyerEmail(destinataire, "CHEK OUT THE NEW EXCITING THINGS", email);
    }

    // Méthode d'envoi d'email générique
    public static void envoyerEmail(String destinataire, String sujet, String email) {
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
            message.setText(email);

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
    public void startDailyNotificationsAtSpecificTime(int hour, int minute) {
    Timer timer = new Timer();
    
    // Calcul du délai jusqu'à la prochaine occurrence de l'heure cible
    Calendar now = Calendar.getInstance();
    Calendar target = Calendar.getInstance();
    target.set(Calendar.HOUR_OF_DAY, hour);
    target.set(Calendar.MINUTE, minute);
    target.set(Calendar.SECOND, 0);
    
    if(now.after(target)) {
        target.add(Calendar.DATE, 1);
    }
    
    long delay = target.getTimeInMillis() - now.getTimeInMillis();
    
    timer.scheduleAtFixedRate(new TimerTask() {
        @Override
        public void run() {
            String content = "Rappel quotidien - Vérifiez les nouveautés dans la buvette!";
            envoyerEmail(DEFAULT_TO, DAILY_SUBJECT, content);
        }
    }, delay, 24 * 60 * 60 * 1000); // Répéter toutes les 24h
}
    
    
    
    
    
    
    
    

    public static void main(String[] args) {
        // Exemple d'utilisation
        SendEmail service = new SendEmail();
        
        // 1. Démarrer le service quotidien
        service.startDailyNotifications();
        
        // 2. Envoyer un email immédiat (à la demande)
        envoyerEmail("destinataire@example.com", "Contenu de test");
        
        // Garder le programme en vie
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            service.stopDailyNotifications();
        }
    }
}

