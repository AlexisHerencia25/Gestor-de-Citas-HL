package gestion.administrativa.hospital.la;
import java.util.HashMap;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;


public class Login {
    
    static String rutaJSONAdministrador = "src/gestion/administrativa/hospital/la/admins.json";
    
    
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
                JSONObject objAdmin = arreglo.getJSONObject(i).getJSONObject("Administrador");

                String usuario = objAdmin.getString("Usuario");
                String contrasena = objAdmin.getString("Contraseña");

                if (usuario.equals(nombreUsuario) && contrasena.equals(contraseña)) {
                    return true; // Credenciales válidas
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
    public static String LecturaJsonUsuarios(String usuarioBuscado) {
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
                String usuario = objAdmin.getString("Usuario");
                if (usuario.equals(usuarioBuscado)) {
                    return usuario;
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
