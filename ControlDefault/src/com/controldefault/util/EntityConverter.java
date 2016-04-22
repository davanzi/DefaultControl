package com.controldefault.util;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.springframework.context.annotation.Scope;

import com.controldefault.dao.GenericDAO;
import com.controldefault.model.RoleVO;

@FacesConverter("entityConverter")
@Scope("session")
public class EntityConverter implements Converter{
	private GenericDAO genericDAO =  new GenericDAO(RoleVO.class);

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Integer code = Integer.parseInt(value);
        try {
            return genericDAO.listById(code);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EntityConverter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EntityConverter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(EntityConverter.class.getName()).log(Level.SEVERE, null, ex);			
		}
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        RoleVO role = (RoleVO) value;
        return String.valueOf(role.getId());
    }
}
