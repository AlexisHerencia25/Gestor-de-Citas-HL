/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gestion.administrativa.hospital.la;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormRegistro extends javax.swing.JFrame {
   

    public FormRegistro() {
       setTitle("Registro de Usuario");
        setSize(400, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        updateRoleOptions(); // Set role options based on current user
    }
    private void updateRoleOptions() {
        // Debug to check current role
        System.out.println("Rol actual al cargar FormRegistro: " + Login.rolusuario);
        if (Login.rolusuario == null || Login.rolusuario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se ha iniciado sesión. Por favor, inicie sesión primero.", "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
            return;
        }
        if (Login.rolusuario.equals("Administrador") || Login.rolusuario.equals("Supervisor")) {
            JComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Administrador", "Medico", "Supervisor", "Auditor"}));
        } else {
            JComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Auditor"}));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {//GEN-BEGIN:initComponents

        BotonRegresar = new javax.swing.JLabel();
        logo = new javax.swing.JLabel();
        LblHospital = new javax.swing.JLabel();
        LblLoayza = new javax.swing.JLabel();
        Background = new javax.swing.JLabel();
        lblusuario = new javax.swing.JLabel();
        txtusuario = new javax.swing.JTextField();
        lblcontraseña = new javax.swing.JLabel();
        txtcontraseña = new javax.swing.JPasswordField();
        JComboBox = new javax.swing.JComboBox<>();
        lblcorreo = new javax.swing.JLabel();
        txtcorreo = new javax.swing.JTextField();
        btnRegistro = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BotonRegresar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotonRegresarMouseClicked(evt);
            }
        });
        getContentPane().add(BotonRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 550, 110, 80));

        logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gestion/administrativa/hospital/la/logo.jpeg"))); // NOI18N
        getContentPane().add(logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 90, 90));

        LblHospital.setFont(new java.awt.Font("Corbel", 1, 24)); // NOI18N
        LblHospital.setForeground(new java.awt.Color(6, 5, 235));
        LblHospital.setText("HOSPITAL ARZOBISPO");
        getContentPane().add(LblHospital, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, -1, -1));

        LblLoayza.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        LblLoayza.setForeground(new java.awt.Color(255, 39, 40));
        LblLoayza.setText("LOAYZA NACIONAL");
        getContentPane().add(LblLoayza, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 52, -1, -1));

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gestion/administrativa/hospital/la/registroadmins.png"))); // NOI18N
        getContentPane().add(Background, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 0, 500, 660));

        lblusuario.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblusuario.setForeground(new java.awt.Color(0, 0, 0));
        lblusuario.setText("USUARIO");
        getContentPane().add(lblusuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 185, 80, 20));

        txtusuario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        getContentPane().add(txtusuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 223, 330, 37));

        lblcontraseña.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblcontraseña.setForeground(new java.awt.Color(0, 0, 0));
        lblcontraseña.setText("CONTRASEÑA");
        getContentPane().add(lblcontraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 348, 120, -1));

        txtcontraseña.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        getContentPane().add(txtcontraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 385, 330, 34));

        JComboBox.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        JComboBox.setForeground(new java.awt.Color(0, 0, 0));
        JComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Supervisor", "Auditor", "Medico" }));
        JComboBox.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 255), 2));
        JComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JComboBoxActionPerformed(evt);
            }
        });
        getContentPane().add(JComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 473, 169, 37));

        lblcorreo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblcorreo.setForeground(new java.awt.Color(0, 0, 0));
        lblcorreo.setText("CORREO");
        getContentPane().add(lblcorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 278, 80, 20));

        txtcorreo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        getContentPane().add(txtcorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 304, 330, 37));

        btnRegistro.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRegistro.setForeground(new java.awt.Color(0, 0, 0));
        btnRegistro.setText("REGISTRAR");
        btnRegistro.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 255), 2));
        btnRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistroActionPerformed(evt);
            }
        });
        getContentPane().add(btnRegistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 471, 180, 42));

        pack();
    }//GEN-END:initComponents

    private void JComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JComboBoxActionPerformed
        String selectedRole = JComboBox.getSelectedItem().toString();
        Login.registrarAuditoria("Selección de rol", Login.rolusuario, "Rol seleccionado: " + selectedRole);
        if (selectedRole.equals("Administrador")) {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Está seleccionando el rol 'Administrador'. ¿Desea continuar?", 
                "Confirmar Rol", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE);
            if (confirm != JOptionPane.YES_OPTION) {
                JComboBox.setSelectedIndex(0); // Revert to first option
            }
        }
    }//GEN-LAST:event_JComboBoxActionPerformed

    private void btnRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistroActionPerformed
        try {
            String usuario = txtusuario.getText().trim();
            String correo = txtcorreo.getText().trim();
            String contraseña = new String(txtcontraseña.getPassword()).trim();
            String rolSeleccion = JComboBox.getSelectedItem().toString(); // Use JComboBox for role selection

            if (usuario.isEmpty() || correo.isEmpty() || contraseña.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }

            Login.registrarUsuario(usuario, correo, contraseña, rolSeleccion);
            JOptionPane.showMessageDialog(this, "Usuario registrado como " + rolSeleccion);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al registrar: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnRegistroActionPerformed

    private void BotonRegresarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotonRegresarMouseClicked
        this.dispose();
    }//GEN-LAST:event_BotonRegresarMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
         SwingUtilities.invokeLater(() -> new FormRegistro().setVisible(true));
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        /* Set the Nimbus look and feel */
        //<editor-fold desc=" Look and feel setting code (optional) ">
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
            java.util.logging.Logger.getLogger(FormRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormRegistro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Background;
    private javax.swing.JLabel BotonRegresar;
    private javax.swing.JComboBox<String> JComboBox;
    private javax.swing.JLabel LblHospital;
    private javax.swing.JLabel LblLoayza;
    private javax.swing.JButton btnRegistro;
    private javax.swing.JLabel lblcontraseña;
    private javax.swing.JLabel lblcorreo;
    private javax.swing.JLabel lblusuario;
    private javax.swing.JLabel logo;
    private javax.swing.JPasswordField txtcontraseña;
    private javax.swing.JTextField txtcorreo;
    private javax.swing.JTextField txtusuario;
    // End of variables declaration//GEN-END:variables
}
