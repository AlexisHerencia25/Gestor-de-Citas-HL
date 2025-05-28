/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestion.administrativa.hospital.la;
import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
/**
 *
 * @author Alexis
 */
public class RecuperacionContraseña {
    public String GenerarCodigo(){
        StringBuilder codigo = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int digito = (int)(Math.random() * 9) + 1; // Genera entre 1 y 9
            codigo.append(digito);
        }
        return codigo.toString();
    }
    
    public void Enviar_Codigo(String correo, String codigo){
        final String contraseña = "oygm uovl dgqc yxnv"; // Ir a https://myaccount.google.com/apppasswords, generar una contraseña de aplicación y reemplazarla

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
            new jakarta.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(correo, contraseña);
                }
            });

        try {
            Message mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(correo));
            mensaje.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(correo));
            mensaje.setSubject("Código de recuperación de contraseña");
            mensaje.setText("Su código es: " + codigo + "\nPor favor no lo comparta con nadie.");

            Transport.send(mensaje);
            System.out.println("Correo enviado correctamente.");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    
    public static void cambiarContrasena(String correo, String nuevaClave) {
        String ruta = "src/gestion/administrativa/hospital/la/admins.json";
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(ruta)), "UTF-8");
            JSONArray arreglo = new JSONArray(contenido);
            for (int i = 0; i < arreglo.length(); i++) {
                JSONObject admin = arreglo.getJSONObject(i).getJSONObject("Administrador");
                if (admin.getString("Correo electrónico").equalsIgnoreCase(correo)) {
                    admin.put("Contraseña", nuevaClave);
                    Files.write(Paths.get(ruta), arreglo.toString(2).getBytes("UTF-8"));
                    System.out.println("Contraseña actualizada correctamente.");
                    return;
                }
            }
            System.out.println("No se encontró el correo.");

        } catch (IOException e) {
            System.out.println("Error leyendo o escribiendo el archivo: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error procesando el JSON: " + e.getMessage());
        }
    }
}
