package Projeto;


import javax.swing.UIManager;

public class Main2 {

	public static void main(String[] args) {
        try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch (Exception ignored) {}

        Persistencia p = new Persistencia();

        CentralDeInformacoes c = p.recuperarCentral();
        if (c == null) {
            c = new CentralDeInformacoes();
        }
        
        if(c.getCoordenador() == null) {
        	new TelaCadastroCoordenador(c, p);
        }
        else {
        	new TelaLogin(c, p);
        }
    }
}