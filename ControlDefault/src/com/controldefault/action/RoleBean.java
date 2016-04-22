package com.controldefault.action;

import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.controldefault.control.RoleBO;
import com.controldefault.model.RoleVO;
import com.controldefault.model.UserVO;
import com.controldefault.util.ConstantsUtil;
import com.controldefault.util.InfoUtil;

// mapeamento spring
@Component("roleBean")
@Scope("session")
public class RoleBean implements Serializable {
	@Resource
	private RoleBO roleBO;
	private List<RoleVO> listRoleVO;
	private String name;
	private RoleVO roleVO;
	private RoleVO newRoleVO = new RoleVO();
	private int response;
	List<SelectItem> listItens;
	private int controller = 0;

	public RoleBean() {
	}

	@Autowired
	public RoleBean(RoleBO pRoleBO) {
		this.roleBO = pRoleBO;
	}

	public List<RoleVO> getListRoleVO() {
		return listRoleVO;
	}

	public void setListRoleVO(List<RoleVO> pListRoleVO) {
		this.listRoleVO = pListRoleVO;
	}

	public String getName() {
		return name;
	}

	public void setName(String pName) {
		this.name = pName;
	}

	public RoleVO getRoleVO() {
		return roleVO;
	}

	public void setRoleVO(RoleVO pRoleVO) {
		this.roleVO = pRoleVO;
	}

	public RoleVO getNewRoleVO() {
		return newRoleVO;
	}

	public void setNewRoleVO(RoleVO pNewRoleVO) {
		this.newRoleVO = pNewRoleVO;
	}

	public List<SelectItem> getListItens() {
		return listItens;
	}

	public void setListItens(List<SelectItem> listItens) {
		this.listItens = listItens;
	}

	// Listando os itens existentes no banco ao iniciar a tabela
	public List<RoleVO> getListRoleVOInit() {
		if (controller == 0) {
			listAllRoles();
			controller++;
		}
		return listRoleVO;
	}

	// Listando todas as permiss�es do banco sem parametro
	public void listAllRoles() {
		FacesMessage message = null;
		listRoleVO = roleBO.listRoles();
		message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Listando", "Todos da Lista");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	// Listando todas as permiss�es do banco com Action Event como par�metro
	public void listAllRoles(ActionEvent actionEvent) {
		listAllRoles();
	}

	// Listando todas as permiss�es do banco com Action Event como par�metro
	public void listAllRolesSelectItem(ActionEvent actionEvent) throws Exception {
		FacesMessage message = null;
		listItens = roleBO.listRolesAndName();
		message = new FacesMessage(FacesMessage.SEVERITY_INFO, ConstantsUtil.UPDATE , "Adicionando Permiss�o ao Combo");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	// Listando todas as permiss�es do banco com Close Event como par�metro
	public void listAllRoles(CloseEvent actionEvent) {
		listAllRoles();
	}

	// Listando permiss�es do banco de acordo com a coluna e o valor selecionado
	// pelo usu�rio
	public List<String> listRolesVOByName(String pName, String pValue) throws Exception {
		List<RoleVO> foundRolesVO = roleBO.listRoleByName(pName, pValue);
		List<String> names = new ArrayList<String>();
		for (RoleVO m : foundRolesVO)
			names.add(m.getName());
		return names;
	}

	// Salvar ou Atualizar
	private void saveUpdateRole(String pType) {
		FacesMessage message = null;
		if (pType.equals(ConstantsUtil.SAVE)) {
			roleVO = newRoleVO;
		}
		if (validateName(roleVO)) {
			try {
				roleBO.saveUpdateRole(roleVO);
				message = new FacesMessage(FacesMessage.SEVERITY_INFO, pType, "Permiss�o gravado com Sucesso");
			} catch (Exception e) {
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao " + pType + " Permiss�o", e.toString());
			}
		} else {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao " + pType + " Permiss�o", "Permiss�o " + roleVO.getName() + " j� existe!");
		}
		FacesContext.getCurrentInstance().addMessage(null, message);
		newRoleVO = new RoleVO();
		listAllRoles();
	}
	
	// Salvar Permiss�o
	public void saveRole(ActionEvent actionEvent) {
		saveUpdateRole(ConstantsUtil.SAVE);
	}

	// Salvar Permiss�o na Inser��o de Funcion�rios
	public void saveRoleUser(ActionEvent actionEvent) throws Exception {
		saveRole(actionEvent);
		listAllRolesSelectItem(actionEvent);
	}

	// Atualizar Permiss�o
	public void updateRole() {
		saveUpdateRole(ConstantsUtil.UPDATE);
	}

	// Remover Permiss�o
	public void removeRole() {
		FacesMessage message = null;
		try {
			roleBO.removeRole(roleVO);
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Deletar", "Permiss�o removida com Sucesso");
		} catch (Exception e) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao Remover Permiss�o", e.toString());
		}
		FacesContext.getCurrentInstance().addMessage(null, message);
		listAllRoles();
	}
	
	// Validar Duplicidade de nomes
	public boolean validateName(RoleVO pRoleVO) {
		List<RoleVO> lListRoleVO = roleBO.listRoleLike("name", pRoleVO.getName());
		for (RoleVO lRoleVO : lListRoleVO) {
			if (lRoleVO.getName().equals(pRoleVO.getName()) && lRoleVO.getId() != pRoleVO.getId()) {
				return false;
			}
		}
		return true;
	}
}
