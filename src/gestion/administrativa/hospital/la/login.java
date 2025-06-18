package gestion.administrativa.hospital.la;
import java.util.HashMap;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;


public class Login {
    
    static String rutaJSONAdministrador = "src/gestion/administrativa/hospital/la/admins.json";
    public String rolusuario = "";
    
    // HashMap para almacenar usuarios registrados
    private static final HashMap<String, Administrador> usuarios = new HashMap<>();

    // Iniciar sesión
    public boolean iniciarSesion(String nombreUsuario, String contraseña) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(rutaJSONAdministrador));
            StringBuilder contenido = new StringBuilder();
            String linea;
            while ((linea = reader.readLine()) != null) {
                contenido.append(linea);
            }
            reader.close();

            JSONArray arreglo = new JSONArray(contenido.toString());

            for (int i = 0; i < arreglo.length(); i++) {
                JSONObject objetoRol = arreglo.getJSONObject(i);

                // Detecta el rol dinámicamente (Administrador, Auditor, etc.)
                rolusuario = objetoRol.keys().next();
                JSONObject datosUsuario = objetoRol.getJSONObject(rolusuario);

                String usuario = datosUsuario.getString("Usuario");
                String contrasena = datosUsuario.getString("Contraseña");

                if (usuario.equals(nombreUsuario) && contrasena.equals(contraseña)) {
                    System.out.println("Inicio de sesión exitoso como: " + rolusuario);
                    return true;
                }
            }

        } catch (Exception e) {
            System.out.println("Error al verificar credenciales: " + e.getMessage());
        }
        return false;
    }

    // Recuperar contraseña
    public void recuperarContraseña(String nombreUsuario) {
        Administrador usuario = usuarios.get(nombreUsuario);
        if (usuario != null) {
            JOptionPane.showMessageDialog(null, "Enlace de recuperación enviado al correo registrado.", "Recuperar Contraseña", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Lectura de los usuarios de los administradores en el JSON
    public String LecturaJsonUsuarios(String usuarioBuscado) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(rutaJSONAdministrador));
            StringBuilder contenido = new StringBuilder();
            String linea;
            while ((linea = reader.readLine()) != null) {
                contenido.append(linea);
            }
            reader.close();

            JSONArray arreglo = new JSONArray(contenido.toString());

            for (int i = 0; i < arreglo.length(); i++) {
                JSONObject objetoRol = arreglo.getJSONObject(i);
                rolusuario = objetoRol.keys().next();
                JSONObject datos = objetoRol.getJSONObject(rolusuario);

                String usuario = datos.getString("Usuario");
                if (usuario.equals(usuarioBuscado)) {
                    return "Usuario encontrado: " + usuario + " (" + rolusuario + ")";
                }
            }
        } catch (Exception e) {
            System.out.println("Error al leer usuarios: " + e.getMessage());
        }
        return null;
    }
        
    // Lectura de los usuarios de los administradores en el JSON
    public static String LecturaJsonCorreo(String correoBuscado) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(rutaJSONAdministrador));
            StringBuilder contenido = new StringBuilder();
            String linea;
            while ((linea = reader.readLine()) != null) {
                contenido.append(linea);
            }
            reader.close();
            JSONArray arreglo = new JSONArray(contenido.toString());
            for (int i = 0; i < arreglo.length(); i++) {
                JSONObject objAdmin = arreglo.getJSONObject(i).getJSONObject("Administrador");
                String correo = objAdmin.getString("Correo electrónico");

                if (correo.equals(correoBuscado)) {
                    return correo;
                }
            }
        } catch (Exception e) {
            System.out.println("Error al leer correos: " + e.getMessage());
        }
        return null;
    }
}
