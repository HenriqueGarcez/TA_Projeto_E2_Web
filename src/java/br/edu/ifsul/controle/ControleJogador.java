package br.edu.ifsul.controle;

import br.edu.ifsul.dao.CartaDAO;
import br.edu.ifsul.dao.PermissaoDAO;
import br.edu.ifsul.dao.JogadorDAO;
import br.edu.ifsul.modelo.Carta;
import br.edu.ifsul.modelo.Permissao;
import br.edu.ifsul.modelo.Jogador;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author eric_
 */
@Named(value = "controleJogador")
@ViewScoped
public class ControleJogador implements Serializable {
    
    @EJB
    private JogadorDAO<Jogador> dao;
    private Jogador objeto;
    private Boolean editando;
    @EJB
    private CartaDAO<Carta> daoCarta;
    @EJB
    private PermissaoDAO<Permissao> daoPermissao;
    private Permissao permissao;
    private Boolean editandoPermissao;
    
    public ControleJogador(){
        editando = false;
    }
    
    public String listar(){
        editando = false;
        return "/privado/jogador/listar?faces-redirect=true";
    }
    
    public String mostraDeck(){
        return "/privado/deck/listar?faces-redirect=true";
    }
    
    public void novo(){
        editando = true;
        editandoPermissao = false;
        objeto = new Jogador();
    }
    
    public void alterar(Object id){
        try {
            objeto = dao.getObjectById(id);
            editando = true;
            editandoPermissao = false;
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
    
    public void alteraSalva(Jogador jogador){
        try {
            dao.merge(jogador);
            Util.mensagemInformacao("Cartas salvas no seu deck!");
        } catch (Exception ex) {
            Logger.getLogger(ControleJogador.class.getName()).log(Level.SEVERE, null, ex);
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

    public void novaPermissao(){
        editandoPermissao = true;
    }
    
    public void salvarPermissao(){
        if(objeto.getPermissoes().contains(permissao)){
            Util.mensagemErro("Usuário já possui esta permissão!");
        } else {
            objeto.getPermissoes().add(permissao);
            Util.mensagemInformacao("Permissão adicionada com sucesso!");
        }
        editandoPermissao = false;
    }
    
    public void removerPermissao(Permissao obj){
        objeto.getPermissoes().remove(obj);
        Util.mensagemInformacao("Permissão removida com sucesso!");
    }
    
    public JogadorDAO<Jogador> getDao() {
        return dao;
    }

    public void setDao(JogadorDAO<Jogador> dao) {
        this.dao = dao;
    }

    public Jogador getObjeto() {
        return objeto;
    }

    public void setObjeto(Jogador objeto) {
        this.objeto = objeto;
    }

    public Boolean getEditando() {
        return editando;
    }

    public void setEditando(Boolean editando) {
        this.editando = editando;
    }

    public PermissaoDAO<Permissao> getDaoPermissao() {
        return daoPermissao;
    }

    public void setDaoPermissao(PermissaoDAO<Permissao> daoPermissao) {
        this.daoPermissao = daoPermissao;
    }

    public Permissao getPermissao() {
        return permissao;
    }

    public void setPermissao(Permissao permissao) {
        this.permissao = permissao;
    }

    public Boolean getEditandoPermissao() {
        return editandoPermissao;
    }

    public void setEditandoPermissao(Boolean editandoPermissao) {
        this.editandoPermissao = editandoPermissao;
    }

    public CartaDAO<Carta> getDaoCarta() {
        return daoCarta;
    }

    public void setDaoCarta(CartaDAO<Carta> daoCarta) {
        this.daoCarta = daoCarta;
    }

}
