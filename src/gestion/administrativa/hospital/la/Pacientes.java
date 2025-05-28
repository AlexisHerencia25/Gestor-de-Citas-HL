
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


public class Pacientes {
    public void BuscarYMostrarPacientes(File archivoJson, JTable tabla, JTextField txtID, JTextField txtBuscar) {
    try {
        String contenido = new String(Files.readAllBytes(archivoJson.toPath()));
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
}
