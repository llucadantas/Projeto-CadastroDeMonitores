package Repository;

import Model.Coordenador;
import Repository.Interfaces.CoordenadorRepository;

import java.util.List;

public class CoordenadorRepositoryImp implements CoordenadorRepository {

    private List<Coordenador> coordenadores;
    private final Persistencia persistencia;
    private static final String ARQUIVO = "coordenador.xml";

    public CoordenadorRepositoryImp() {
        this.persistencia = new Persistencia();
        this.coordenadores = persistencia.recuperarDados(ARQUIVO);
    }

    @Override
    public Coordenador getCoordenador() {
        // Se a lista não for nula e não estiver vazia, retorna o primeiro coordenador salvo
        if (coordenadores != null && !coordenadores.isEmpty()) {
            return coordenadores.get(0);
        }
        return null;
    }

    // Método extra: Como sua interface não tinha método de salvar, criei este
    // para você poder definir/atualizar o coordenador e persistir no XML.
    public void cadastrarCoordenador(Coordenador coordenador) {
        this.coordenadores.clear(); // Limpa a lista para garantir que só exista 1 coordenador
        this.coordenadores.add(coordenador);
        persistencia.salvarDados(this.coordenadores, ARQUIVO);
    }
}