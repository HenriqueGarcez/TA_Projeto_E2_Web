package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Jogador;
import java.io.Serializable;
import javax.ejb.Stateful;
import javax.persistence.Query;

/**
 *
 * @author eric_
 */
@Stateful
public class JogadorDAO<TIPO> extends DAOGenerico<Jogador> implements Serializable {

    public JogadorDAO(){
        super();
        classePersistente = Jogador.class;
        ordem = "nick"; // define a ordem padrão do DAO
        maximoObjetos = 3;
    }
    
    public Jogador getObjectById(Object id) throws Exception {
        Jogador obj = em.find(Jogador.class, id);
        /**
         * A linha obj.getPermissoes().size(); é necessária para inicializar a coleção
         * para quando ela for exibida na tela não gerar um erro de 
         * lazyInicializationException
         */
        obj.getPermissoes().size();
        obj.getDeck().size();
        return obj;
    }    
    
    public Jogador localizaPorNickJogador(String nickUsuario) {
        Query query= em.createQuery("from Jogador where upper(nick) = :nickUsuario");
        query.setParameter("nickUsuario", nickUsuario.toUpperCase());
        Jogador obj= (Jogador) query.getSingleResult();
        obj.getPermissoes().size();
        obj.getDeck().size();
        return obj;
    }
}
