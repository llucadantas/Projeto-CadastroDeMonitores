package Repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import Model.Aluno;
import Model.Coordenador;
import Model.Disciplina;
import Model.EditalDeMonitoria;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Persistencia {

    // 1. Variável estática que guarda a instância única
    private static Persistencia instancia;

    private final XStream xstream;

    // 2. Construtor PRIVADO. Ninguém fora dessa classe pode fazer 'new Persistencia()'
    private Persistencia() {
        xstream = new XStream(new DomDriver());
        xstream.addPermission(com.thoughtworks.xstream.security.NoTypePermission.NONE);

        xstream.allowTypesByWildcard(new String[] {
                "Model.**",
                "java.util.**",
                "java.time.**"
        });

        xstream.alias("Aluno", Aluno.class);
        xstream.alias("Coordenador", Coordenador.class);
        xstream.alias("Edital", EditalDeMonitoria.class);
        xstream.alias("Disciplina", Disciplina.class);
        xstream.alias("Lista", java.util.ArrayList.class);
    }

    // 3. Método global de acesso à instância única
    public static Persistencia getInstance() {
        if (instancia == null) {
            instancia = new Persistencia(); // Cria apenas na primeira vez que for chamado
        }
        return instancia;
    }

    public <T> void salvarDados(List<T> dados, String nomeArquivo) {
        File file = new File(nomeArquivo);
        String xml = xstream.toXML(dados);

        try (PrintWriter writer = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            writer.print(xml);
        } catch (IOException e){
            System.err.println("Erro ao tentar salvar o arquivo: " + nomeArquivo);
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> recuperarDados(String nomeArquivo) {
        File file = new File(nomeArquivo);

        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        try (InputStreamReader reader = new InputStreamReader(
                new FileInputStream(file), StandardCharsets.UTF_8)) {
            return (List<T>) xstream.fromXML(reader);
        } catch (IOException e) {
            System.err.println("Erro de I/O ao ler o arquivo: " + nomeArquivo);
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}