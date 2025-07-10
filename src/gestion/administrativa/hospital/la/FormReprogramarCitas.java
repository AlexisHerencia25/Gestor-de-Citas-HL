 
package gestion.administrativa.hospital.la;

import javax.swing.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Files;

public class FormReprogramarCitas extends javax.swing.JFrame {
    public FormReprogramarCitas() {
        initComponents();
        llenarComboCitas();
        llenarComboDias();

            
    }
    private void llenarComboCitas() {
        comboCitas.removeAllItems();
        try {
            String contenido = new String(Files.readAllBytes(Paths.get("src/gestion/administrativa/hospital/la/citas.json")));
            JSONArray citas = new JSONArray(contenido);
            for (Object obj : citas) {
                JSONObject cita = (JSONObject) obj;
                String id = cita.getString("cita_id");
                JSONObject paciente = cita.getJSONObject("paciente");
                String nombrePaciente = paciente.getString("nombres_completos");
                comboCitas.addItem(id + " - " + nombrePaciente);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void llenarComboDias() {
        String[] dias = { "lunes", "martes", "miércoles", "jueves", "viernes", "sábado" };
        comboDias.removeAllItems();
        for (String dia : dias) {
            comboDias.addItem(dia);
        }
    }
    private void llenarComboHoras(String dia) {
        comboHoras.removeAllItems();
        try {
            String contenido = new String(Files.readAllBytes(Paths.get("src/gestion/administrativa/hospital/la/medicos.json")));
            JSONArray medicos = new JSONArray(contenido);
            for (Object obj : medicos) {
                JSONObject medico = (JSONObject) obj;
                JSONObject horarios = medico.optJSONObject("horario_disponible");
                if (horarios != null && horarios.has(dia)) {
                    String hora = horarios.getString(dia);
                    comboHoras.addItem(hora);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     
    

     
     
    private void initComponents() {//GEN-BEGIN:initComponents

        BotonRegresar = new javax.swing.JLabel();
        comboCitas = new javax.swing.JComboBox<>();
        comboDias = new javax.swing.JComboBox<>();
        comboHoras = new javax.swing.JComboBox<>();
        btnReprogramar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Lbl_Titulo = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BotonRegresar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotonRegresarMouseClicked(evt);
            }
        });
        getContentPane().add(BotonRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 110, 80));

        comboCitas.setBackground(new java.awt.Color(255, 255, 255));
        comboCitas.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        comboCitas.setForeground(new java.awt.Color(0, 0, 0));
        comboCitas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboCitas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 255), 2));
        comboCitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCitasActionPerformed(evt);
            }
        });
        getContentPane().add(comboCitas, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 112, 247, 30));

        comboDias.setBackground(new java.awt.Color(255, 255, 255));
        comboDias.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        comboDias.setForeground(new java.awt.Color(0, 0, 0));
        comboDias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboDias.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 255), 2));
        comboDias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboDiasActionPerformed(evt);
            }
        });
        getContentPane().add(comboDias, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 170, 247, 30));

        comboHoras.setBackground(new java.awt.Color(255, 255, 255));
        comboHoras.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        comboHoras.setForeground(new java.awt.Color(0, 0, 0));
        comboHoras.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboHoras.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 255), 2));
        getContentPane().add(comboHoras, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 240, 247, 30));

        btnReprogramar.setBackground(new java.awt.Color(255, 255, 255));
        btnReprogramar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnReprogramar.setForeground(new java.awt.Color(0, 0, 0));
        btnReprogramar.setText("REPROGRAMAR CITA");
        btnReprogramar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 255), 3));
        btnReprogramar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReprogramarActionPerformed(evt);
            }
        });
        getContentPane().add(btnReprogramar, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 310, 212, 40));

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Seleccione cita:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 170, 31));

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Nuevo Día:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, 110, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Nueva Hora:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, -1, -1));

        Lbl_Titulo.setFont(new java.awt.Font("Chewy", 1, 36)); // NOI18N
        Lbl_Titulo.setForeground(new java.awt.Color(57, 74, 128));
        Lbl_Titulo.setText("REPROGRAMAR CITAS");
        getContentPane().add(Lbl_Titulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, 460, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gestion/administrativa/hospital/la/registro-citas.jpg"))); // NOI18N
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 510, 430));

        pack();
        setLocationRelativeTo(null);
    }//GEN-END:initComponents

    private void comboCitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCitasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboCitasActionPerformed

    private void comboDiasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDiasActionPerformed
        String diaSeleccionado = (String) comboDias.getSelectedItem();
        if (diaSeleccionado != null) {
            llenarComboHoras(diaSeleccionado);
        }
    }//GEN-LAST:event_comboDiasActionPerformed

    private void btnReprogramarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReprogramarActionPerformed
        String citaSeleccionada = (String) comboCitas.getSelectedItem();
        String nuevoDia = (String) comboDias.getSelectedItem();
        String nuevaHora = (String) comboHoras.getSelectedItem();

        if (citaSeleccionada == null || nuevoDia == null || nuevaHora == null) {
            JOptionPane.showMessageDialog(this, "Seleccione todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String citaId = citaSeleccionada.split(" - ")[0];

        Ticket ticket = new Ticket();
        boolean resultado = ticket.ReprogramarCita(citaId, nuevoDia, nuevaHora);

        if (resultado) {
            JOptionPane.showMessageDialog(this, " Cita reprogramada correctamente.");
            llenarComboCitas();  
        } else {
            JOptionPane.showMessageDialog(this, " No se pudo reprogramar la cita. Posible conflicto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnReprogramarActionPerformed

    private void BotonRegresarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotonRegresarMouseClicked
        this.dispose();
    }//GEN-LAST:event_BotonRegresarMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormReprogramarCitas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormReprogramarCitas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormReprogramarCitas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormReprogramarCitas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormReprogramarCitas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BotonRegresar;
    private javax.swing.JLabel Lbl_Titulo;
    private javax.swing.JButton btnReprogramar;
    private javax.swing.JComboBox<String> comboCitas;
    private javax.swing.JComboBox<String> comboDias;
    private javax.swing.JComboBox<String> comboHoras;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    // End of variables declaration//GEN-END:variables
   
}

