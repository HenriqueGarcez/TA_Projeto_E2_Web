package br.edu.ifsul.controle;

import br.edu.ifsul.dao.CartaDAO;
import br.edu.ifsul.modelo.Carta;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author eric_
 */
@Named(value = "controleCarta")
@ViewScoped
public class ControleCarta implements Serializable {
    
    @EJB
    private CartaDAO<Carta> dao;
    private Carta objeto;
    private Boolean editando;
    
    public ControleCarta(){
        editando = false;
    }
    
    public String listar(){
        editando = false;
        return "/privado/carta/listar?faces-redirect=true";
    }
    
    public String listarCartaProduto(){
        return "/privado/cartaProduto/listar?faces-redirect=true";
    }
    
    public void novo(){
        editando = true;
        objeto = new Carta();
    }
    
    public void alterar(Object id){
        try {
            objeto = dao.getObjectById(id);
            editando = true;
        } catch (Exception e){
            Util.mensagemErro("Erro ao recuperar objeto: " + 
                    Util.getMensagemErro(e));
        } 
    }
    
    public void excluir(Object id){
        try {
            objeto = dao.getObjectById(id);
            dao.remover(objeto);
            Util.mensagemInformacao("Objeto removido com sucesso!");
        } catch (Exception e){
            Util.mensagemErro("Erro ao remover objeto: " + 
                    Util.getMensagemErro(e));
        }
    }
    
    public void salvar(){
        try {
            if (objeto.getId() == null){
                dao.persist(objeto);
            } else {
                dao.merge(objeto);
            }
            Util.mensagemInformacao("Objeto persistido com sucesso!");
            editando = false;
        } catch(Exception e){
            Util.mensagemErro("Erro ao persistir objeto: " + 
                    Util.getMensagemErro(e));
        }
    }

    public CartaDAO<Carta> getDao() {
        return dao;
    }

    public void setDao(CartaDAO<Carta> dao) {
        this.dao = dao;
    }

    public Carta getObjeto() {
        return objeto;
    }

    public void setObjeto(Carta objeto) {
        this.objeto = objeto;
    }

    public Boolean getEditando() {
        return editando;
    }

    public void setEditando(Boolean editando) {
        this.editando = editando;
    }

}