
package gestion.administrativa.hospital.la;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

import java.awt.*;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.util.ArrayList;
import java.util.Collections;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;


public class Pacientes {
    private File jsonpacientes = new File("src/gestion/administrativa/hospital/la/pacientes.json");
    
    public void BuscarYMostrarPacientes(JTable tabla, JTextField txtID, JTextField txtBuscar) {
    try {
        String contenido = new String(Files.readAllBytes(jsonpacientes.toPath()));
        JSONArray pacientes = new JSONArray(contenido);

        DefaultTableModel modelo = new DefaultTableModel(new Object[]{"ID" ,"Nombre", "Detalle"}, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 🔒 Esto bloquea edición en TODAS las celdas
            }
        };

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
                info.append("Nombre: ").append(infoPersonal.getString("Nombres completos")).append("\n");
                info.append("DNI: ").append(infoPersonal.getString("DNI")).append("\n");
                info.append("Teléfono: ").append(infoPersonal.getString("Teléfono")).append("\n");
                info.append("Fecha de nacimiento: ").append(infoPersonal.getString("Fecha de nacimiento")).append("\n");
                info.append("Género: ").append(infoPersonal.getString("Género")).append("\n");

                modelo.addRow(new Object[]{
                    infoPersonal.getString("ID"),
                    infoPersonal.getString("Nombres completos"), 
                    info.toString()});
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
            infoPersonal.put("Teléfono", "51+ " + telefono);

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

            
            System.out.println("Paciente agregado correctamente: " + ID);

        } catch (IOException e) {
            System.err.println("Error de archivo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error general: " + e.getMessage());
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
    public void EliminarPaciente(String IDEliminar){
        try {
            String contenido = new String(Files.readAllBytes(jsonpacientes.toPath()));
            JSONArray pacientes = new JSONArray(contenido);
            JSONArray pacientesFiltrados = new JSONArray();

            boolean eliminado = false;
            for (int i = 0; i < pacientes.length(); i++) {
                JSONObject paciente = pacientes.getJSONObject(i);
                JSONObject infoPersonal = paciente.getJSONObject("Información personal");
                String idPaciente = infoPersonal.getString("ID");

                if (!idPaciente.equals(IDEliminar))
                    pacientesFiltrados.put(paciente);
                else
                    eliminado = true;
            }

            if (eliminado) {
                FileWriter writer = new FileWriter(jsonpacientes);
                writer.write(pacientesFiltrados.toString(4)); // 4 espacios de indentación
                writer.close();
                System.out.print("Se ha eliminado correctamente los datos del paciente");
            } else
                System.out.print("No se pudo eliminar los datos");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void EditarPaciente(String IDEditar, JPanel content){
        try {
            String contenido = new String(Files.readAllBytes(jsonpacientes.toPath()));
            JSONArray pacientes = new JSONArray(contenido);
            JSONArray pacientesFiltrados = new JSONArray();

            String nombresCompletos = "", fechaNacimiento = "", genero = "", dni = "", telefono = "";
            String proximaCita = "", alergias = "", notasUrgentes = "", enfermedadesCronicas = "", tipoSangre = "";
            String fechaUltimaAtencion = "", motivoConsulta = "", tratamiento = "", diagnostico = "", medicoEncargado = "";

            for (int i = 0; i < pacientes.length(); i++) {
                JSONObject paciente = pacientes.getJSONObject(i);
                JSONObject infoPersonal = paciente.getJSONObject("Información personal");
                String idPaciente = infoPersonal.getString("ID");

                if (!idPaciente.equals(IDEditar)) {
                    pacientesFiltrados.put(paciente);
                } else {

                    // Información Personal
                    nombresCompletos = infoPersonal.getString("Nombres completos");
                    fechaNacimiento = infoPersonal.getString("Fecha de nacimiento");
                    genero = infoPersonal.getString("Género");
                    dni = infoPersonal.getString("DNI");
                    telefono = infoPersonal.getString("Teléfono");

                    // Citas Programadas
                    JSONObject citas = paciente.getJSONObject("Citas programadas");
                    proximaCita = citas.getString("Próxima cita");

                    // Datos Médicos
                    JSONObject datosMedicos = paciente.getJSONObject("Datos médicos");
                    alergias = datosMedicos.getString("Alergias");
                    notasUrgentes = datosMedicos.getString("Notas médicas urgentes");
                    enfermedadesCronicas = datosMedicos.getString("Enfermedades Crónicas");
                    tipoSangre = datosMedicos.getString("Tipo de Sangre");

                    // Última Atención Médica
                    JSONObject ultimaAtencion = paciente.getJSONObject("Última atención médica");
                    fechaUltimaAtencion = ultimaAtencion.getString("Fecha");
                    motivoConsulta = ultimaAtencion.getString("motivo de consulta");
                    tratamiento = ultimaAtencion.getString("tratamiento o receta indicada");
                    diagnostico = ultimaAtencion.getString("diagnóstico");
                    medicoEncargado = ultimaAtencion.getString("Medico encargado");
                    
                    JPanel panel = new JPanel();
                    PanelEditarPaciente pacientesform = new PanelEditarPaciente(IDEditar, nombresCompletos, fechaNacimiento, genero, dni, telefono, alergias, tipoSangre, enfermedadesCronicas, notasUrgentes, panel);
                    pacientesform.setSize(740, 530);
                    pacientesform.setLocation(0,0);
        
                    content.setLayout(new BorderLayout());
                    content.removeAll();
                    content.add(pacientesform, BorderLayout.CENTER);
                    content.revalidate();
                    content.repaint();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void ConfirmarEdicion(String idModificar, String nombresCompletos, String fechaNacimiento, String genero, 
            String dni, String telefono, String alergias, String enfermedadesCronicas, String tipoSangre, 
            String notasUrgentes){
        try {
        // Leer archivo
        String contenido = new String(Files.readAllBytes(jsonpacientes.toPath()));
        JSONArray pacientes = new JSONArray(contenido);

        // Buscar y reemplazar
        boolean modificado = false;
        for (int i = 0; i < pacientes.length(); i++) {
            JSONObject paciente = pacientes.getJSONObject(i);
            JSONObject infoPersonal = paciente.getJSONObject("Información personal");
            JSONObject datosMedicos = paciente.getJSONObject("Datos médicos");
            String idPaciente = infoPersonal.getString("ID");

            if (idPaciente.equals(idModificar)) {
                infoPersonal.put("Nombres completos", nombresCompletos);
                infoPersonal.put("Género", genero);
                infoPersonal.put("DNI", dni);
                infoPersonal.put("Teléfono", telefono);
                infoPersonal.put("Fecha de nacimiento", fechaNacimiento);
                
                datosMedicos.put("Alergias", alergias);
                datosMedicos.put("Notas médicas urgentes", notasUrgentes);
                datosMedicos.put("Enfermedades Crónicas", enfermedadesCronicas);
                datosMedicos.put("Tipo de Sangre", tipoSangre);
    
                modificado = true;
                break;
            }
        }

        // Guardar cambios
        if (modificado) {
            FileWriter writer = new FileWriter(jsonpacientes);
            writer.write(pacientes.toString(4)); // con indentación de 4 espacios
            writer.close();
            System.out.println("✅ Paciente actualizado correctamente.");
        } else {
            System.out.println("⚠️ No se encontró un paciente con ese ID.");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    }
    public void BuscarYMostrarHistorialClinico(JTable tabla, JTextField txtID, JTextField txtBuscar) {
    try {
        String contenido = new String(Files.readAllBytes(jsonpacientes.toPath()));
        JSONArray pacientes = new JSONArray(contenido);

        DefaultTableModel modelo = new DefaultTableModel(new Object[]{"ID" ,"Nombre", "Historial clínico"}, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 🔒 Esto bloquea edición en TODAS las celdas
            }
        };

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
                JSONObject datosMedicos = paciente.getJSONObject("Datos médicos");

                info.append("Alergias: ").append(datosMedicos.getString("Alergias")).append("\n");
                info.append("Notas médicas urgentes: ").append(datosMedicos.getString("Notas médicas urgentes")).append("\n");
                info.append("Enfermedades crónicas: ").append(datosMedicos.getString("Enfermedades Crónicas")).append("\n");
                info.append("Tipo de sangre: ").append(datosMedicos.getString("Tipo de Sangre")).append("\n");

                modelo.addRow(new Object[]{
                    infoPersonal.getString("ID"),
                    infoPersonal.getString("Nombres completos"), 
                    info.toString()});
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
    
    public static void CargarNombresMedicos(JComboBox<String> comboBox) {
        try {
            String ruta = "src/gestion/administrativa/hospital/la/pacientes.json"; // Ajusta la ruta a tu archivo
            String contenido = new String(Files.readAllBytes(Paths.get(ruta)));
            JSONArray pacientes = new JSONArray(contenido);

            comboBox.removeAllItems(); // Limpiar antes de agregar
            comboBox.addItem("---------Sin seleccionar---------");
            for (int i = 0; i < pacientes.length(); i++) {
                JSONObject paciente = pacientes.getJSONObject(i);
                JSONObject infoPersonal = paciente.getJSONObject("Información personal");

                String nombreCompleto = infoPersonal.optString("Nombres completos", "Sin nombre");
                comboBox.addItem(nombreCompleto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
