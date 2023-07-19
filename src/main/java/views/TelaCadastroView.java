/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package views;

import javax.swing.JButton;
import javax.swing.JTextField;

/**
 *
 * @author ascherit
 */
public class TelaCadastroView extends javax.swing.JInternalFrame {

    /**
     * Creates new form CadastroView
     */
    public TelaCadastroView() {
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelNome = new javax.swing.JLabel();
        labelSenha = new javax.swing.JLabel();
        labelSenhaNovamente = new javax.swing.JLabel();
        campoTextoNome = new javax.swing.JTextField();
        btnRealizarCadastro = new javax.swing.JButton();
        campoTextoSenha = new javax.swing.JPasswordField();
        campoTextoSenhaNovamente = new javax.swing.JPasswordField();

        setVisible(true);

        labelNome.setText("Nome:");

        labelSenha.setText("Senha:");

        labelSenhaNovamente.setText("Digite a senha novamente:");

        btnRealizarCadastro.setText("Realizar Cadastro");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labelSenha)
                            .addComponent(labelNome))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(campoTextoNome)
                            .addComponent(campoTextoSenha)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnRealizarCadastro))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelSenhaNovamente)
                                .addGap(18, 18, 18)
                                .addComponent(campoTextoSenhaNovamente, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)))))
                .addGap(40, 40, 40))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNome)
                    .addComponent(campoTextoNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSenha)
                    .addComponent(campoTextoSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSenhaNovamente)
                    .addComponent(campoTextoSenhaNovamente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addComponent(btnRealizarCadastro)
                .addContainerGap(59, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRealizarCadastro;
    private javax.swing.JTextField campoTextoNome;
    private javax.swing.JPasswordField campoTextoSenha;
    private javax.swing.JPasswordField campoTextoSenhaNovamente;
    private javax.swing.JLabel labelNome;
    private javax.swing.JLabel labelSenha;
    private javax.swing.JLabel labelSenhaNovamente;
    // End of variables declaration//GEN-END:variables

    public JButton getBtnRealizarCadastro(){
        return btnRealizarCadastro;
    }
   
    public JTextField getCampoTextoNome(){
        return campoTextoNome;
    }
    public JTextField getCampoTextoSenha(){
        return campoTextoSenha;
    }
    public JTextField getCampoTextoSenhaNovamente(){
        return campoTextoSenhaNovamente;
    }

}
