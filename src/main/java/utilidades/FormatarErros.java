package utilidades;

import java.util.List;

public class FormatarErros {


    public static String unificarErros(List<String> erros){

        StringBuilder erroMsg = new StringBuilder();
        for (String erro : erros){
            erroMsg.append(erro + "\n");
        }

        return erroMsg.toString();
    }
}
