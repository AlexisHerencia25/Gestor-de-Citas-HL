package gestion.administrativa.hospital.la;

import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Login {

    static String rutaJSONAdministrador = "src/gestion/administrativa/hospital/la/admins.json";
    public static String rolusuario = "";
    private static final String[] VALID_ROLES = {"Administrador", "Medico", "Supervisor", "Auditor"};

    // Utility method to read JSON file
   private static JSONArray readJsonFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("Archivo no encontrado: " + filePath);
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder contenido = new StringBuilder();
        String linea;
        while ((linea = reader.readLine()) != null) {
            contenido.append(linea);
        }
        reader.close();
        return new JSONArray(contenido.toString());
    }

    // Utility method to write JSON file
   private static void writeJsonFile(String filePath, JSONArray jsonArray) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        writer.write(jsonArray.toString(4));
        writer.close();
    }


    public static String iniciarSesion(String nombreUsuario, String contraseña) {
        try {
            JSONArray arreglo = readJsonFile(rutaJSONAdministrador);

            for (int i = 0; i < arreglo.length(); i++) {
                JSONObject objetoRol = arreglo.getJSONObject(i);
                String rolusuario = objetoRol.keys().next();
                JSONObject datosUsuario = objetoRol.getJSONObject(rolusuario);

                String usuario = datosUsuario.getString("Usuario");
                String contrasena = datosUsuario.getString("Contraseña");
                boolean bloqueado = datosUsuario.optBoolean("Bloqueado", false);
                if (bloqueado) {
                    JOptionPane.showMessageDialog(null, "Este usuario está bloqueado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
                if (usuario.equals(nombreUsuario) && contrasena.equals(contraseña)) {
                    Login.rolusuario = rolusuario;
                    registrarAuditoria("Inicio de sesión", nombreUsuario, "Usuario " + nombreUsuario + " inició sesión como " + rolusuario);
                    return rolusuario;
                }
            }
            JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al leer el archivo de usuarios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al verificar credenciales: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public void recuperarContraseña(String nombreUsuario) {
        try {
            JSONArray arreglo = readJsonFile(rutaJSONAdministrador);
            for (int i = 0; i < arreglo.length(); i++) {
                JSONObject objetoRol = arreglo.getJSONObject(i);
                String rol = objetoRol.keys().next();
                JSONObject datos = objetoRol.getJSONObject(rol);
                if (datos.getString("Usuario").equals(nombreUsuario)) {
                    JOptionPane.showMessageDialog(null, "Enlace de recuperación enviado al correo registrado.", "Recuperar Contraseña", JOptionPane.INFORMATION_MESSAGE);
                    registrarAuditoria("Recuperación de contraseña", nombreUsuario, "Solicitud de recuperación para " + nombreUsuario);
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al leer el archivo de usuarios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al procesar la recuperación: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String LecturaJsonUsuarios(String usuarioBuscado) {
        try {
            JSONArray arreglo = readJsonFile(rutaJSONAdministrador);
            for (int i = 0; i < arreglo.length(); i++) {
                JSONObject objetoRol = arreglo.getJSONObject(i);
                String rol = objetoRol.keys().next();
                JSONObject datos = objetoRol.getJSONObject(rol);
                String usuario = datos.getString("Usuario");
                if (usuario.equals(usuarioBuscado)) {
                    return "Usuario encontrado: " + usuario + " (" + rol + ")";
                }
            }
            return null;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al leer el archivo de usuarios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al leer usuarios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public static String LecturaJsonCorreo(String correoBuscado) {
        try {
            JSONArray arreglo = readJsonFile(rutaJSONAdministrador);
            for (int i = 0; i < arreglo.length(); i++) {
                JSONObject objAdmin = arreglo.getJSONObject(i).getJSONObject(arreglo.getJSONObject(i).keys().next());
                String correo = objAdmin.getString("Correo electrónico");
                if (correo.equals(correoBuscado)) {
                    return correo;
                }
            }
            return null;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al leer el archivo de usuarios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al leer correos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    


    public static void registrarUsuario(String usuario, String correo, String contrasena, String rol) {
         try {
            JSONArray arreglo = readJsonFile(rutaJSONAdministrador);

            for (int i = 0; i < arreglo.length(); i++) {
                JSONObject obj = arreglo.getJSONObject(i);
                String rolExistente = obj.keys().next();
                JSONObject datos = obj.getJSONObject(rolExistente);
                if (datos.getString("Usuario").equalsIgnoreCase(usuario) ||
                    datos.getString("Correo electrónico").equalsIgnoreCase(correo)) {
                    JOptionPane.showMessageDialog(null, "El usuario o correo ya está registrado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Validate role before registration
            if (!isValidRole(rol)) {
                JOptionPane.showMessageDialog(null, "Rol no válido: " + rol + ". Debe ser Administrador, Medico, Supervisor o Auditor.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JSONObject nuevoUsuario = new JSONObject();
            JSONObject datos = new JSONObject();
            datos.put("Usuario", usuario);
            datos.put("Correo electrónico", correo);
            datos.put("Contraseña", contrasena);
            datos.put("Bloqueado", false);

            nuevoUsuario.put(rol, datos);
            arreglo.put(nuevoUsuario);

            writeJsonFile(rutaJSONAdministrador, arreglo);

            registrarAuditoria("Registro", usuario, "Registrado como " + rol);
            JOptionPane.showMessageDialog(null, "Usuario registrado con rol: " + rol, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al acceder al archivo de usuarios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al registrar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void cambiarPermisos(String usuario, String nuevoRol) {
       if (!rolusuario.equals("Administrador")) {
            JOptionPane.showMessageDialog(null, "Solo los administradores pueden cambiar roles.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Validate the new role
        if (!isValidRole(nuevoRol)) {
            JOptionPane.showMessageDialog(null, "Rol no válido: " + nuevoRol + ". Debe ser Administrador, Medico, Supervisor o Auditor.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            JSONArray arreglo = readJsonFile(rutaJSONAdministrador);
            boolean usuarioEncontrado = false;

            for (int i = 0; i < arreglo.length(); i++) {
                JSONObject objetoRol = arreglo.getJSONObject(i);
                String rolActual = objetoRol.keys().next();
                JSONObject datos = objetoRol.getJSONObject(rolActual);
                if (datos.getString("Usuario").equals(usuario)) {
                    JSONObject nuevoUsuario = new JSONObject();
                    datos.put("Bloqueado", datos.optBoolean("Bloqueado", false));
                    nuevoUsuario.put(nuevoRol, datos);
                    arreglo.remove(i);
                    arreglo.put(nuevoUsuario);
                    usuarioEncontrado = true;
                    break;
                }
            }

            if (!usuarioEncontrado) {
                JOptionPane.showMessageDialog(null, "Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            writeJsonFile(rutaJSONAdministrador, arreglo);

            registrarAuditoria("Cambio de permisos", usuario, "Rol cambiado a " + nuevoRol);
            JOptionPane.showMessageDialog(null, "Permisos actualizados: " + usuario + " ahora es " + nuevoRol, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al acceder al archivo de usuarios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cambiar permisos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void bloquearUsuario(String usuario, boolean bloquear) {
        if (!rolusuario.equals("Administrador")) {
            JOptionPane.showMessageDialog(null, "Solo los administradores pueden " + (bloquear ? "bloquear" : "desbloquear") + " usuarios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            JSONArray arreglo = readJsonFile(rutaJSONAdministrador);
            boolean usuarioEncontrado = false;

            for (int i = 0; i < arreglo.length(); i++) {
                JSONObject objetoRol = arreglo.getJSONObject(i);
                String rol = objetoRol.keys().next();
                JSONObject datos = objetoRol.getJSONObject(rol);
                if (datos.getString("Usuario").equals(usuario)) {
                    datos.put("Bloqueado", bloquear);
                    usuarioEncontrado = true;
                    break;
                }
            }

            if (!usuarioEncontrado) {
                JOptionPane.showMessageDialog(null, "Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            writeJsonFile(rutaJSONAdministrador, arreglo);

            String accion = bloquear ? "Bloqueo" : "Desbloqueo";
            registrarAuditoria(accion, usuario, usuario + " ha sido " + (bloquear ? "bloqueado" : "desbloqueado"));
            JOptionPane.showMessageDialog(null, "Usuario " + (bloquear ? "bloqueado" : "desbloqueado") + ": " + usuario, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al acceder al archivo de usuarios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al " + (bloquear ? "bloquear" : "desbloquear") + " usuario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    public static void eliminarUsuario(String usuario) {
        if (!rolusuario.equals("Administrador")) {
            JOptionPane.showMessageDialog(null, "Solo los administradores pueden eliminar usuarios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            JSONArray arreglo = readJsonFile(rutaJSONAdministrador);
            boolean usuarioEncontrado = false;

            for (int i = 0; i < arreglo.length(); i++) {
                JSONObject objetoRol = arreglo.getJSONObject(i);
                String rol = objetoRol.keys().next();
                JSONObject datos = objetoRol.getJSONObject(rol);
                if (datos.getString("Usuario").equals(usuario)) {
                    arreglo.remove(i);
                    usuarioEncontrado = true;
                    break;
                }
            }

            if (!usuarioEncontrado) {
                JOptionPane.showMessageDialog(null, "Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            writeJsonFile(rutaJSONAdministrador, arreglo);

            registrarAuditoria("Eliminación", usuario, "Usuario " + usuario + " ha sido eliminado");
            JOptionPane.showMessageDialog(null, "Usuario eliminado: " + usuario, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al acceder al archivo de usuarios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar usuario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void registrarAuditoria(String accion, String objetivo, String detalles) {
        try {
            String ruta = "src/gestion/administrativa/hospital/la/auditoria.json";
            File file = new File(ruta);
            JSONArray auditoria;

            if (file.exists()) {
                auditoria = readJsonFile(ruta);
            } else {
                auditoria = new JSONArray();
            }

            JSONObject log = new JSONObject();
            log.put("usuario", Login.rolusuario);
            log.put("acción", accion);
            if (objetivo != null) log.put("objetivo", objetivo);
            log.put("fecha_hora", ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
            log.put("detalles", detalles);

            auditoria.put(log);

            writeJsonFile(ruta, auditoria);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al escribir en el archivo de auditoría: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al registrar auditoría: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    private static boolean isValidRole(String rol) {
        for (String validRole : VALID_ROLES) {
            if (validRole.equals(rol)) {
                return true;
            }
        }
        return false;
    }
    public static void manejarIntentoFallido(String nombreUsuario) {
    try {
        JSONArray arreglo = readJsonFile(rutaJSONAdministrador);

        for (int i = 0; i < arreglo.length(); i++) {
            JSONObject objetoRol = arreglo.getJSONObject(i);
            String rol = objetoRol.keys().next();
            JSONObject datos = objetoRol.getJSONObject(rol);

            if (datos.getString("Usuario").equals(nombreUsuario)) {
                boolean bloqueado = datos.optBoolean("Bloqueado", false);
                if (bloqueado) {
                    JOptionPane.showMessageDialog(null, "Este usuario ya está bloqueado.", "Bloqueado", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int intentos = datos.optInt("IntentosFallidos", 0) + 1;
                datos.put("IntentosFallidos", intentos);

                if (intentos >= 3) {
                    datos.put("Bloqueado", true);
                    registrarAuditoria("Bloqueo automático", nombreUsuario, "Usuario bloqueado tras 3 intentos fallidos.");
                    JOptionPane.showMessageDialog(null, "Usuario bloqueado por múltiples intentos fallidos.", "Bloqueado", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Contraseña incorrecta. Intento " + intentos + " de 3.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
                arreglo.put(i, new JSONObject().put(rol, datos));
                writeJsonFile(rutaJSONAdministrador, arreglo);
                return;
            }
        }

        JOptionPane.showMessageDialog(null, "Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error al leer el archivo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al manejar intento fallido: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
}