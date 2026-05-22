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

    private final XStream xstream;

    public Persistencia() {
        xstream = new XStream(new DomDriver());
        xstream.addPermission(com.thoughtworks.xstream.security.NoTypePermission.NONE);

        xstream.allowTypes(new Class[] {
                Aluno.class,
                Coordenador.class,
                EditalDeMonitoria.class,
                Disciplina.class,
                java.util.ArrayList.class,
                java.time.LocalDate.class
        });

        // Aliases mais limpos para o XML
        xstream.alias("Aluno", Aluno.class);
        xstream.alias("Coordenador", Coordenador.class);
        xstream.alias("Edital", EditalDeMonitoria.class);
        xstream.alias("Disciplina", Disciplina.class);
        xstream.alias("Lista", java.util.ArrayList.class);
    }

    /**
     * Método genérico para salvar qualquer lista de entidades em um arquivo XML.
     */
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

    /**
     * Método genérico para recuperar qualquer lista de entidades de um arquivo XML.
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> recuperarDados(String nomeArquivo) {
        File file = new File(nomeArquivo);

        // Se o arquivo não existe, retorna uma lista vazia pronta para ser usada
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