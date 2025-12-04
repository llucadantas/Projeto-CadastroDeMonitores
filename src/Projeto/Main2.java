package Projeto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;
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
        
        if(c.getCoodernador() == null) {
        	new TelaCadastroCoordenador(c, p);
        }
        else {
        	new TelaLogin(c, p);
        }
    }
}