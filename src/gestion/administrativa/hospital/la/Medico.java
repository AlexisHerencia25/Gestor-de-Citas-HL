
package gestion.administrativa.hospital.la;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
public class Medico {
    private List<JSONObject> listaMedicos;
    private int id;
    private String nombre;
    private String apellido;
    private String especialidad;
    private String horarioDisponible;

    public Medico(int id, String nombre, String apellido, String especialidad, String horarioDisponible) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.horarioDisponible = horarioDisponible;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getHorarioDisponible() {
        return horarioDisponible;
    }

    public void setHorarioDisponible(String horarioDisponible) {
        this.horarioDisponible = horarioDisponible;
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
