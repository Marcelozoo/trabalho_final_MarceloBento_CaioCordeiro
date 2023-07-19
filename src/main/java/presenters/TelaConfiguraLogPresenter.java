package presenters;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import views.ConfiguracoesLogView;

public class TelaConfiguraLogPresenter {

    private final ConfiguracoesLogView configura;
    private  String tipoArquivo;

    public TelaConfiguraLogPresenter() {

        this.configura = new ConfiguracoesLogView();
        this.configura.setVisible(true);
        this.configura.setLocationRelativeTo(null);
        
        configuraBtns();
    }

    private void configuraBtns() {
        this.configura.getBtnSalvar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                selecionaItemComboBox();
                configura.dispose();

            }
        });
    }

    private void selecionaItemComboBox() {

        JComboBox comboBox = configura.getjComboBox1();
        String tipoArquivoSelecionado = (String) comboBox.getSelectedItem();

        this.tipoArquivo = tipoArquivoSelecionado;
    }
    
    public String getTipoArquivo(){
        return this.tipoArquivo;
    }

}
