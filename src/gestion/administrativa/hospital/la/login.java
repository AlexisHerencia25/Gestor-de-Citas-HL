package login;
import java.util.HashMap;
import javax.swing.JOptionPane;
public class login {
    // HashMap para almacenar usuarios registrados
    private static final HashMap<String, usuario> usuarios = new HashMap<>();

    // Iniciar sesión
    public boolean iniciarSesion(String nombreUsuario, String contraseña) {
        if (nombreUsuario.isEmpty() || contraseña.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios para iniciar sesión.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        usuario usuario = usuarios.get(nombreUsuario);

        if (usuario != null && usuario.getContraseña().equals(contraseña)) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Credenciales incorrectas.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Recuperar contraseña
    public void recuperarContraseña(String nombreUsuario) {
        usuario usuario = usuarios.get(nombreUsuario);
        if (usuario != null) {
            JOptionPane.showMessageDialog(null, "Enlace de recuperación enviado al correo registrado.", "Recuperar Contraseña", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
