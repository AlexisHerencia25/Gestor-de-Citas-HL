/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestion.administrativa.hospital.la;
import java.io.File;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.SecureRandom;
import java.util.*;
/**
 *
 * @author Alexis
 */
public class BaseMedicos {
    
    private List<JSONObject> listaMedicos;
    private File jsonmedicos;
    
    public BaseMedicos(String rutaJson){
        cargarMedicosDesdeJson(rutaJson);
        jsonmedicos = new File(rutaJson);
    }
    private void cargarMedicosDesdeJson(String rutaJson) {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(rutaJson)));
            JSONArray array = new JSONArray(contenido);
            listaMedicos = new ArrayList<>();

            for (int i = 0; i < array.length(); i++) {
                listaMedicos.add(array.getJSONObject(i));
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar el archivo JSON." + e.toString());
        }
    }

    public void llenarComboMedicos(JComboBox<String> combo, String especialidad) {
        combo.removeAllItems();
        combo.addItem("---------Sin seleccionar---------");
        for (JSONObject medico : listaMedicos) {
            if (medico.getString("especialidad").equals(especialidad)) {
                combo.addItem(medico.getString("nombre"));
            }
        }
    }
    
    public void MostrarTablaMedicos(JTextField campoBusqueda, JTable tabla){
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        model.setRowCount(0);
        
        String texto = campoBusqueda.getText().toLowerCase().trim();
        Boolean datos_existe = false;
        
        if (texto.isEmpty()) return;
        
        
        for (JSONObject medico : listaMedicos){
            String nombre = medico.getString("nombre");
            if (nombre.toLowerCase().contains(texto)){
                String id = medico.getString("id");
                String nombre_medico = medico.optString("nombre");
                String especialidad = medico.getString("especialidad");

                JSONObject info = medico.getJSONObject("informacion_personal");
                String contacto = info.optString("Telefono", "Sin telefono");
                model.addRow(new Object[]{id, nombre_medico, contacto, especialidad});
                datos_existe = true;
                /*for (String numero : info.keySet()) {
                    String contacto = info.getString(numero);
                    model.addRow(new Object[]{id, nombre_medico, contacto, especialidad});
                    datos_existe = true;
                }*/
            }
        }
    }

    public void llenarComboEspecialidades(JComboBox<String> combo) {
        Set<String> especialidadesUnicas = new HashSet<>();
        for (JSONObject medico : listaMedicos) {
            especialidadesUnicas.add(medico.getString("especialidad"));
        }
        for (String especialidad : especialidadesUnicas) {
            combo.addItem(especialidad);
        }
    }

    public void llenarTablaMedicoSeleccionado(JTable tabla, String nombreSeleccionado) {
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        model.setRowCount(0); // Limpiar

        for (JSONObject medico : listaMedicos) {
            if (medico.getString("nombre").equals(nombreSeleccionado)) {
                String id = medico.getString("id");
                String nombre = medico.getString("nombre");
                String especialidad = medico.getString("especialidad");

                JSONObject horarios = medico.getJSONObject("horario_disponible");
                for (String dia : horarios.keySet()) {
                    String hora = horarios.getString(dia);
                    model.addRow(new Object[]{nombre, id, dia, hora, especialidad});
                }
                break;
            }
        }
    }
    
    public boolean RegistrarNuevosMedicos(String nombresCompletos, String fechaNacimiento, String genero, 
            String dni, String telefono, String especialidad, String cargo, String tipoSangre, 
            String area, String ID){
        try {
            String contenido = new String(Files.readAllBytes(jsonmedicos.toPath()));
            JSONArray medicos = new JSONArray(contenido);

            JSONObject informacionPersonal = new JSONObject();
            informacionPersonal.put("Nombres completos", nombresCompletos);
            informacionPersonal.put("Fecha de nacimiento", fechaNacimiento);
            informacionPersonal.put("DNI", dni);
            informacionPersonal.put("Teléfono", "51+ " + telefono);
            informacionPersonal.put("Cargo", cargo);
            informacionPersonal.put("Tipo de sangre", tipoSangre);
            informacionPersonal.put("Área", area);

            JSONObject nuevoMedico = new JSONObject();
            nuevoMedico.put("id", "M-" + ID);
            if (genero.equals("Femenino"))
                nuevoMedico.put("nombre", "Dra. " + nombresCompletos);
            else if (genero.equals("Masculino"))
                nuevoMedico.put("nombre", "Dr. " + nombresCompletos);
            nuevoMedico.put("especialidad", especialidad);
            nuevoMedico.put("informacion_personal", informacionPersonal);
            
            JSONObject horariosdisponibles = new JSONObject();
            nuevoMedico.put("horario_disponible", horariosdisponibles);
            medicos.put(nuevoMedico);

            Files.write(Paths.get(jsonmedicos.getPath()), medicos.toString(2).getBytes(), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

            System.out.println("Médico agregado correctamente: " + ID);
            return true;
        } catch (IOException e) {
            System.err.println("Error de archivo: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error general: " + e.getMessage());
            return false;
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
                String contenido = new String(Files.readAllBytes(jsonmedicos.toPath()));
                JSONArray pacientes = new JSONArray(contenido);
                for (Object obj : pacientes) {
                    JSONObject medico = (JSONObject) obj;
                    String id = medico.optString("id", null);
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
