package br.edu.ifsul.converters;

import br.edu.ifsul.modelo.Carta;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author eric_
 */
@FacesConverter(value = "converterCarta")
public class ConverterCarta implements Serializable, Converter {
    
    @PersistenceContext(unitName = "TA_Projeto_E2_WebPU")
    private EntityManager em;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string == null || string.equals("Selecione um registro")){
            return null;
        }
        return em.find(Carta.class, Integer.parseInt(string));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o == null){
            return null;
        }
        Carta obj = (Carta) o;
        return obj.getId().toString();
    }

}
