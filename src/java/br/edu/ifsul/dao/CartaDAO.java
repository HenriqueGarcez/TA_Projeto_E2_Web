package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Carta;
import java.io.Serializable;
import javax.ejb.Stateful;

/**
 *
 * @author eric_
 */
@Stateful
public class CartaDAO<TIPO> extends DAOGenerico<Carta> implements Serializable {

    public CartaDAO(){
        super();
        classePersistente = Carta.class;
        ordem = "nome";
    }
}
