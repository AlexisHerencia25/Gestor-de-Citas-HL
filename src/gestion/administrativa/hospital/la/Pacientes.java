
package gestion.administrativa.hospital.la;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.util.ArrayList;
import java.util.Collections;

import java.security.SecureRandom;


public class Pacientes {
    File jsonpacientes = new File("src/gestion/administrativa/hospital/la/pacientes.json");
    
    public void BuscarYMostrarPacientes(JTable tabla, JTextField txtID, JTextField txtBuscar) {
    try {
        String contenido = new String(Files.readAllBytes(jsonpacientes.toPath()));
        JSONArray pacientes = new JSONArray(contenido);

        DefaultTableModel modelo = new DefaultTableModel(new Object[]{"Nombre", "Detalle"}, 0);

        String idBuscado = txtID.getText().trim().toLowerCase();
        String nombreBuscado = txtBuscar.getText().trim().toLowerCase();

        // Solo buscar si hay al menos un campo lleno
        if (idBuscado.isEmpty() && nombreBuscado.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese un ID o Nombre para buscar.");
            return;
        }

        for (int i = 0; i < pacientes.length(); i++) {
            JSONObject paciente = pacientes.getJSONObject(i);
            JSONObject infoPersonal = paciente.getJSONObject("Información personal");
            String id = infoPersonal.getString("ID").toLowerCase();
            String nombre = infoPersonal.getString("Nombres completos").toLowerCase();

            boolean coincideID = !idBuscado.isEmpty() && id.contains(idBuscado);
            boolean coincideNombre = !nombreBuscado.isEmpty() && nombre.contains(nombreBuscado);

            if (coincideID || coincideNombre) {
                StringBuilder info = new StringBuilder();

                for (String key : paciente.keySet()) {
                    JSONObject seccion = paciente.getJSONObject(key);
                    info.append("[").append(key).append("]\n");
                    for (String campo : seccion.keySet()) {
                        info.append(campo).append(": ").append(seccion.getString(campo)).append("\n");
                    }
                    info.append("\n");
                }

                modelo.addRow(new Object[]{infoPersonal.getString("Nombres completos"), info.toString()});
            }
        }

        tabla.setModel(modelo);
        tabla.setRowHeight(150);
        tabla.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                JTextArea area = new JTextArea();
                area.setText(value != null ? value.toString() : "");
                area.setWrapStyleWord(true);
                area.setLineWrap(true);
                area.setOpaque(true);
                area.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
                area.setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
                return area;
            }
        });

    } catch (IOException | JSONException e) {
        JOptionPane.showMessageDialog(null, "Error al leer el archivo JSON: " + e.getMessage());
        e.printStackTrace();
    }
}
    public void RegistrarNuevosPacientes(String nombresCompletos, String fechaNacimiento, String genero, 
            String dni, String telefono, String alergias, String enfermedadesCronicas, String tipoSangre, 
            String notasUrgentes, String ID){
        try {
            String contenido = new String(Files.readAllBytes(jsonpacientes.toPath()));
            JSONArray pacientes = new JSONArray(contenido);

            // Crear objetos
            JSONObject infoPersonal = new JSONObject();
            infoPersonal.put("ID", "P-" + ID);
            infoPersonal.put("Nombres completos", nombresCompletos);
            infoPersonal.put("Fecha de nacimiento", fechaNacimiento);
            infoPersonal.put("Género", genero);
            infoPersonal.put("DNI", dni);
            infoPersonal.put("Teléfono", telefono);

            JSONObject datosMedicos = new JSONObject();
            datosMedicos.put("Alergias", alergias); // Por defecto
            datosMedicos.put("Enfermedades Crónicas", enfermedadesCronicas);
            datosMedicos.put("Tipo de Sangre", tipoSangre);
            datosMedicos.put("Notas médicas urgentes", notasUrgentes);

            JSONObject ultimaAtencion = new JSONObject();
            ultimaAtencion.put("Fecha", ""); // Vacío inicialmente
            ultimaAtencion.put("motivo de consulta", "");
            ultimaAtencion.put("diagnóstico", "");
            ultimaAtencion.put("tratamiento o receta indicada", "");
            ultimaAtencion.put("Medico encargado", "");

            JSONObject cita = new JSONObject();
            cita.put("Próxima cita", "");

            // Armar paciente
            JSONObject nuevoPaciente = new JSONObject();
            nuevoPaciente.put("Información personal", infoPersonal);
            nuevoPaciente.put("Datos médicos", datosMedicos);
            nuevoPaciente.put("Última atención médica", ultimaAtencion);
            nuevoPaciente.put("Citas programadas", cita);

            // Agregar a lista
            pacientes.put(nuevoPaciente);

            // Guardar
            Files.write(Paths.get(jsonpacientes.getPath()), pacientes.toString(2).getBytes(), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

            
            System.out.println("✅ Paciente agregado correctamente: " + ID);

        } catch (IOException e) {
            System.err.println("❌ Error de archivo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Error general: " + e.getMessage());
        }
    }
    public String generarIDAlfanumerico() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int longitud = 30;
        SecureRandom random = new SecureRandom();
        ArrayList<String> idsExistentes = obtenerIDsOrdenados();
        Collections.sort(idsExistentes);

        String nuevoID;
        do {
            StringBuilder id = new StringBuilder();
            for (int i = 0; i < longitud; i++) {
                int indice = random.nextInt(caracteres.length());
                id.append(caracteres.charAt(indice));
            }
            nuevoID = id.toString();
        } while (Collections.binarySearch(idsExistentes, nuevoID) >= 0);
        return nuevoID;
    }
    private ArrayList<String> obtenerIDsOrdenados() {
            ArrayList<String> ids = new ArrayList<>();
            try{
                String contenido = new String(Files.readAllBytes(jsonpacientes.toPath()));
                JSONArray pacientes = new JSONArray(contenido);
                for (Object obj : pacientes) {
                    JSONObject paciente = (JSONObject) obj;
                    JSONObject info = (JSONObject) paciente.get("Información personal");
                    String id = (String) info.get("ID");
                    if (id != null) {
                        ids.add(id);
                    }
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        return ids;
    }
}
