/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.JogadorDAO;
import br.edu.ifsul.modelo.Jogador;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author eric_
 */
@Named(value = "controleLogin")
@SessionScoped
public class ControleLogin implements Serializable{
    
    private Jogador jogadorAutenticado;
    @EJB
    private JogadorDAO<Jogador> dao;
    private String nick;
    private String password;
    
    public ControleLogin(){        
    }

    public String paginaLogin(){
        return "/login?faces-redirect-true";
    }
    
    public String efetuarLogin(){
        try{
            HttpServletRequest request= (HttpServletRequest)
                    FacesContext.getCurrentInstance().getExternalContext().getRequest();
            request.login(this.getNick(), this.getPassword());
            if(request.getUserPrincipal() != null){
                jogadorAutenticado= dao.localizaPorNickJogador(request.getUserPrincipal().getName());
                Util.mensagemInformacao("Login efetuado com sucesso!");
                setNick("");
                setPassword("");
            }
            return "/index";
        }catch(Exception e){
            Util.mensagemErro("Usuário ou password inválidos!"+Util.getMensagemErro(e));
            return "/login";
        }
    }
    
    public String efetuarLogout(){
        try{
            jogadorAutenticado = null;
            HttpServletRequest request= (HttpServletRequest) 
                    FacesContext.getCurrentInstance().getExternalContext().getRequest();
            request.logout();
        }catch(Exception e){
            Util.mensagemErro("Erro: "+ Util.getMensagemErro(e));
        }
        return "/index";
    }
    
    public Jogador getJogadorAutenticado() {
        return jogadorAutenticado;
    }

    public void setJogadorAutenticado(Jogador jogadorAutenticado) {
        this.jogadorAutenticado = jogadorAutenticado;
    }

    public JogadorDAO<Jogador> getDao() {
        return dao;
    }

    public void setDao(JogadorDAO<Jogador> dao) {
        this.dao = dao;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
