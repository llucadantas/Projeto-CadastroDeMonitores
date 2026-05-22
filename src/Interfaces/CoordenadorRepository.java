package Interfaces;

import Model.Coordenador;

public interface CoordenadorRepository {
    Coordenador getCoordenador();
    void cadastrarCoordenador(Coordenador c);

}
