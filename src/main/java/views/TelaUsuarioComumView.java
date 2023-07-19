package views;

import javax.swing.JButton;
import javax.swing.JLabel;


public class TelaUsuarioComumView extends javax.swing.JInternalFrame {

    
    public TelaUsuarioComumView() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        btnAlterarSenha = new javax.swing.JButton();
        labelNomeUsuario = new javax.swing.JLabel();
        labelTipoUsuario = new javax.swing.JLabel();
        btnNotificacao = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("jRadioButtonMenuItem1");

        setClosable(true);
        setVisible(true);

        btnAlterarSenha.setFont(new java.awt.Font("Noto Serif Tangut", 0, 16)); // NOI18N
        btnAlterarSenha.setText("Alterar Senha");
        btnAlterarSenha.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        labelNomeUsuario.setText("jLabel1");

        labelTipoUsuario.setText("jLabel1");

        btnNotificacao.setText("Notificacoes");
        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(btnAlterarSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(labelNomeUsuario)
                        .addGap(154, 154, 154)
                        .addComponent(labelTipoUsuario)
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnNotificacao)
                        .addGap(81, 81, 81))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(btnAlterarSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                .addComponent(btnNotificacao, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNomeUsuario)
                    .addComponent(labelTipoUsuario))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterarSenha;
    private javax.swing.JButton btnNotificacao;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JLabel labelNomeUsuario;
    private javax.swing.JLabel labelTipoUsuario;
    // End of variables declaration//GEN-END:variables
    
    public JButton getBtnAlterarSenha(){
        return btnAlterarSenha;
    }
    
    public JButton getBtnNotificacao(){
        return btnNotificacao;
    }
    
    public JLabel getLabelNomeUsuario(){
        return labelNomeUsuario;
    }
    
    public JLabel getLabelTipoUsuario(){
        return labelTipoUsuario;
    }
    

}
