/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestion.administrativa.hospital.la;
import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
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
        String rutaArchivo = "src/gestion/administrativa/hospital/la/admins.json";

        try {
            // Leer contenido del archivo JSON
            String contenido = new String(Files.readAllBytes(Paths.get(rutaArchivo)));

            // Convertir a JSONArray
            JSONArray jsonArray = new JSONArray(contenido);

            // Buscar al usuario "admin02" y cambiar su contraseña
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                JSONObject admin = obj.getJSONObject("Administrador");

                if (admin.getString("Correo electrónico").equals(correo)) {
                    admin.put("Contraseña", nuevaClave);
                    break;
                }
            }

            // Guardar el JSON actualizado en el mismo archivo
            Files.write(Paths.get(rutaArchivo), jsonArray.toString(2).getBytes(StandardCharsets.UTF_8));

            System.out.println("Contraseña actualizada correctamente.");

        } catch (IOException e) {
            System.out.println("Error leyendo o escribiendo el archivo: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error procesando el JSON: " + e.getMessage());
        }
    }
}
