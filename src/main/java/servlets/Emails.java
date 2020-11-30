package servlets;//es necesario usar la libreria jakarta.mail.java

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Emails {
    private final Properties props;


    public Emails() {
        props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        //username: correo de origen
        /*el correo de origen debe tener habilitado en el apartado seguridad
        el acceso para aplicaciones poco seguras
         */
        props.put("from", "pruebasdejalea@gmail.com");
        props.put("username", "pruebasdejalea@gmail.com");
        props.put("password", "Kotto9Contra12");
    }

    public void enviar(String to, String subject, String content) throws MessagingException {
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(props.getProperty("username"), props.getProperty("password"));
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(props.getProperty("from")));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(content);
        Transport.send(message);

        System.out.println("Mensaje enviado!");
    }

    public void enviarCorreo() throws MessagingException {
        Emails emails = new Emails();
        String contenido="Examinando, en fin,\n" +
                "\n" +
                "sus encontradas piezas, su retrete,\n" +
                "\n" +
                "su desesperación, al terminar su día atroz, borrándolo...\n" +
                "\n" +
                "Comprendiendo\n" +
                "\n" +
                "que él sabe que le quiero,\n" +
                "\n" +
                "que le odio con afecto y me es, en suma, indiferente...\n" +
                "\n" +
                "Considerando sus documentos generales\n" +
                "\n" +
                "y mirando con lentes aquel certificado\n" +
                "\n" +
                "que prueba que nació muy pequeñito...\n" +
                "\n" +
                "le hago una seña,\n" +
                "\n" +
                "viene, y le doy un abrazo, emocionado.\n" +
                "\n" +
                "¡Qué más da! Emocionado... Emocionado...\n"+
                "";
        //correo de destino, motivo, contenido del correo
        emails.enviar("garcia.josea@pucp.edu.pe", "Hola! Soy un mensaje enviado desde Java!",contenido);
    }
}