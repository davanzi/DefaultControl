package com.controldefault.control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.stereotype.Service;

import com.controldefault.dao.GenericDAO;
import com.controldefault.model.RoleVO;

// Anota��o do Spring que representa um bean de servi�o
@Service
public class RoleBO {

	private RoleVO roleVO;
	private GenericDAO genericDAO;
	private List<RoleVO> listRoleVO;
	private List<Object> listObject;

	// Construtor do bean de servi�o de Permiss�es
	public RoleBO() {
		roleVO = new RoleVO();
		genericDAO = new GenericDAO(RoleVO.class);
		listRoleVO = new ArrayList<RoleVO>();
	}

	// M�todo de salvar ou atualizar do bean de servi�o Permiss�o
	public int saveUpdateRole(RoleVO pRoleVO) throws Exception {
		if ((!pRoleVO.getName().equals(""))) {
			roleVO = pRoleVO;
			if (genericDAO.saveUpdate(roleVO)) {
				return 1;
			} else {
				return 0;
			}
		} else {
			return -1;
		}
	}

	// M�todo de remo��o do bean de servi�o Permiss�o
	public int removeRole(RoleVO pRoleVO) throws Exception {
		if (pRoleVO != null) {
			if (genericDAO.remove(pRoleVO)) {
				return 1;
			} else {
				return 0;
			}
		} else {
			return -1;
		}
	}

	// M�todo para listar por nome
	public List<RoleVO> listRoleByName(String pName, String pValue) throws Exception {
		listObject = genericDAO.listByName(pName, pValue);
		for (Object obj : listObject) {
			listRoleVO.add((RoleVO) obj);
		}
		return listRoleVO;
	}

	// M�todo para listar como...
	public List<RoleVO> listRoleLike(String pName, String pValue) {
		try {
			listObject = genericDAO.listLike(pName, pValue);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		for (Object obj : listObject) {
			listRoleVO.add((RoleVO) obj);
		}
		return listRoleVO;
	}

	// M�todo para listar por ID
	public RoleVO listRoleById(int pId) throws Exception {
		if (pId == 0) {
			return null;
		}
		roleVO = (RoleVO) genericDAO.listById(pId);
		return roleVO;
	}

	// M�todo para listar todos
	public List<RoleVO> listRoles() {
		listRoleVO = new ArrayList<RoleVO>();
		try {
			listObject = genericDAO.listAll("from RoleVO");			
		} catch (Exception ex) {			
			ex.printStackTrace();
		}
		for (Object obj : listObject) {
			listRoleVO.add((RoleVO) obj);
		}		
		return listRoleVO;
	}
	
	public List<SelectItem> listRolesAndName() throws Exception {
		List<SelectItem> listItens = new LinkedList<SelectItem>();
		RoleVO lRoleVO = null;
		for (Object obj : genericDAO.listAll("from RoleVO")) {
			lRoleVO = (RoleVO)obj;
			listItens.add(new SelectItem(lRoleVO, lRoleVO.getName()));
		}
		return listItens;
	}

}
