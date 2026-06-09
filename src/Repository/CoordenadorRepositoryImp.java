package Repository;

import Model.Coordenador;
import Interfaces.CoordenadorRepository;

import java.util.List;

public class CoordenadorRepositoryImp implements CoordenadorRepository {

    private List<Coordenador> coordenadores;
    private final PersistenciaSingleton persistenciaSingleton;
    private static final String ARQUIVO = "coordenador.xml";

    public CoordenadorRepositoryImp() {
        this.persistenciaSingleton = PersistenciaSingleton.getInstance();
        this.coordenadores = persistenciaSingleton.recuperarDados(ARQUIVO);
    }

    @Override
    public Coordenador getCoordenador() {
        if (coordenadores != null && !coordenadores.isEmpty()) {
            return coordenadores.get(0);
        }
        return null;
    }

    public void cadastrarCoordenador(Coordenador coordenador) {
        this.coordenadores.clear(); // Limpa a lista para garantir que só exista 1 coordenador
        this.coordenadores.add(coordenador);
        persistenciaSingleton.salvarDados(this.coordenadores, ARQUIVO);
    }
}