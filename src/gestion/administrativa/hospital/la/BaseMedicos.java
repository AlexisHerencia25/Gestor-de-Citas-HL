/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestion.administrativa.hospital.la;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
/**
 *
 * @author Alexis
 */
public class BaseMedicos {
    
    private List<JSONObject> listaMedicos;
    
    public BaseMedicos(String rutaJson){
        cargarMedicosDesdeJson(rutaJson);
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
}
