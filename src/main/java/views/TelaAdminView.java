package views;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;

public class TelaAdminView extends javax.swing.JInternalFrame {

    public TelaAdminView() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnListarUsuarios = new javax.swing.JButton();
        btnAlterarSenha = new javax.swing.JButton();
        btnEnviarNotificacao = new javax.swing.JButton();
        labelNomeUsuario = new javax.swing.JLabel();
        LabelTipoUsuario = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaUsuarios = new javax.swing.JTable();
        btnAutenticar = new javax.swing.JButton();

        setClosable(true);
        setVisible(true);

        btnListarUsuarios.setText("Listar Usuários");

        btnAlterarSenha.setText("Alterar Senha");

        btnEnviarNotificacao.setText("Enviar Notificação");

        labelNomeUsuario.setText("NomeUsuario");

        LabelTipoUsuario.setText("tipoUser");

        tabelaUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nome", "Data Cadastro", "Notificações enviadas", "Notificações lidas"
            }
        ));
        jScrollPane1.setViewportView(tabelaUsuarios);

        btnAutenticar.setText("Autenticar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(566, Short.MAX_VALUE)
                .addComponent(labelNomeUsuario)
                .addGap(132, 132, 132)
                .addComponent(LabelTipoUsuario)
                .addGap(21, 21, 21))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnEnviarNotificacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAlterarSenha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnListarUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAutenticar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 631, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnListarUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAlterarSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(btnEnviarNotificacao, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(btnAutenticar, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNomeUsuario)
                    .addComponent(LabelTipoUsuario))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelTipoUsuario;
    private javax.swing.JButton btnAlterarSenha;
    private javax.swing.JButton btnAutenticar;
    private javax.swing.JButton btnEnviarNotificacao;
    private javax.swing.JButton btnListarUsuarios;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelNomeUsuario;
    private javax.swing.JTable tabelaUsuarios;
    // End of variables declaration//GEN-END:variables
    public JLabel getLabelTipoUsuario() {
        return LabelTipoUsuario;
    }

   
    public JButton getBtnAlterarSenha() {
        return btnAlterarSenha;
    }

    public JButton getBtnEnviarNotificacao() {
        return btnEnviarNotificacao;
    }

    public JButton getBtnListarUsuarios() {
        return btnListarUsuarios;
    }

    public JLabel getLabelNomeUsuario() {
        return labelNomeUsuario;
    }

    public JTable getTabelaUsuarios() {
        return tabelaUsuarios;
    }

    public JButton getBtnAutenticar() {
        return btnAutenticar;
    }
    
    

}
