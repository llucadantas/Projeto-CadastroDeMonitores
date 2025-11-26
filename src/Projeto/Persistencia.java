package Projeto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Persistencia {
	
    private final XStream xstream; 
    private final File file = new File("banco.xml");
	
    public Persistencia() {
        xstream = new XStream(new DomDriver());
        xstream.addPermission(com.thoughtworks.xstream.security.NoTypePermission.NONE); 
        
        xstream.allowTypes(new Class[] {
                CentralDeInformacoes.class,
                Aluno.class,
                SexoLista.class,           
                java.util.ArrayList.class,
             
                EditalDeMonitoria.class, 
                Disciplina.class,          
                java.time.LocalDate.class  
            });
        
        xstream.alias("Central", CentralDeInformacoes.class);
        xstream.alias("Aluno", Aluno.class);
        xstream.alias("Edital", EditalDeMonitoria.class);
        xstream.alias("Disciplina", Disciplina.class);
    }
	
    public void salvarCentral(CentralDeInformacoes central) {
        String xml = xstream.toXML(central);

        try (PrintWriter writer = new PrintWriter(
                                      new OutputStreamWriter(
                                          new FileOutputStream(file), StandardCharsets.UTF_8))) {
			 
            writer.print(xml);
        }
        catch (IOException e){
            System.err.println("Erro ao tentar salvar o arquivo ");
            e.printStackTrace();
        }
    }
	
    public CentralDeInformacoes recuperarCentral() {
        if (!file.exists() || file.length() == 0) {
            return new CentralDeInformacoes();
        }
		
        try (InputStreamReader reader = new InputStreamReader(
                                          new FileInputStream(file), StandardCharsets.UTF_8)) {
            
            return (CentralDeInformacoes) xstream.fromXML(reader);
        } 

        catch (IOException e) {
            System.err.println("Erro de I/O ao ler o arquivo 'banco.xml':");
            e.printStackTrace();
        }
        
        return new CentralDeInformacoes();
    }
}
		
