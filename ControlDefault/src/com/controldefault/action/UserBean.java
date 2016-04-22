package com.controldefault.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.controldefault.control.UserBO;
import com.controldefault.control.RoleBO;
import com.controldefault.model.UserVO;
import com.controldefault.model.RoleVO;
import com.controldefault.util.ConstantsUtil;
import com.controldefault.util.InfoUtil;

// mapeamento spring
@Component("userBean")
@Scope("session")
public class UserBean implements Serializable {
	private Vector<String> rolesItens = new Vector<String>();
	private SelectItem[] rolesItensOpt;

	@Resource
	private UserBO userBO;
	private List<UserVO> listUserVO;
	private String name;
	private UserVO userVO;
	private UserVO newUserVO = new UserVO();
	private int controller = 0;

	public UserBean() {
	}

	@Autowired
	public UserBean(UserBO pUserBO) {
		this.userBO = pUserBO;
	}

	public List<UserVO> getListUserVO() {
		return listUserVO;
	}

	public void setListUserVO(List<UserVO> pListUserVO) {
		this.listUserVO = pListUserVO;
	}

	public String getName() {
		return name;
	}

	public void setName(String pName) {
		this.name = pName;
	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO pUserVO) {
		this.userVO = pUserVO;
	}

	public UserVO getNewUserVO() {
		return newUserVO;
	}

	public void setNewUserVO(UserVO pNewUserVO) {
		this.newUserVO = pNewUserVO;
	}

	public Vector<String> getRolesItens() {
		rolesItens.removeAllElements();
		RoleBO roleBO = new RoleBO();
		List<RoleVO> listRoleVO = roleBO.listRoles();
		for (RoleVO item : listRoleVO) {
			rolesItens.add(item.getDescription());
		}
		return rolesItens;
	}

	public void setRolesItens(Vector<String> pRolesItens) {
		this.rolesItens = pRolesItens;
	}

	// preenchendo combo roles de busca
	public SelectItem[] getRolesItensOpt() {
		getRolesItens();
		rolesItensOpt = createFilterOptions(rolesItens);
		return rolesItensOpt;
	}

	// preenche um objeto SelectItem
	private SelectItem[] createFilterOptions(Vector<String> data) {
		SelectItem[] options = new SelectItem[data.size() + 1];
		options[0] = new SelectItem("", "Select");
		for (int i = 0; i < data.size(); i++) {
			options[i + 1] = new SelectItem(data.get(i), data.get(i));
		}
		return options;
	}

	// Listando os itens existentes no banco ao iniciar a tabela
	public List<UserVO> getListUserVOInit() {
		if (controller == 0) {
			listAllUsers();
			controller++;
		}
		return listUserVO;
	}

	// Listando todas as linhas do banco sem parametro
	public void listAllUsers() {
		FacesMessage message = null;
		listUserVO = userBO.listUsers();
		message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Listando", "Todos da Lista");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	// Listando todas as linhas do banco com Action Event como par�metro
	public void listAllUsers(ActionEvent actionEvent) {
		listAllUsers();
	}

	// Listando todas as linhas do banco com Close Event como par�metro
	public void listAllUsers(CloseEvent actionEvent) {
		listAllUsers();
	}

	// Listando linhas do banco de acordo com a coluna e o valor selecionado
	// pelo usu�rio
	public List<UserVO> listUsersVOByName(String pName, String pValue) throws Exception {
		List<UserVO> foundUsersVO = userBO.listUserByName(pName, pValue);
		return foundUsersVO;
	}

	// Salvar ou Atualizar
	private void saveUpdateUser(String pType) {
		FacesMessage message = null;
		if (pType.equals(ConstantsUtil.SAVE)) {
			userVO = newUserVO;
		}
		if (validateDuplicityName(userVO)) {
			if (validateDuplicityLogin(userVO)) {
				try {
					userBO.saveUpdateUser(userVO);
					message = new FacesMessage(FacesMessage.SEVERITY_INFO, pType, "Usu�rio gravado com Sucesso");
				} catch (Exception e) {
					message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao " + pType + " Usu�rio", e.toString());
				}
			} else
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao " + pType + " Login", "Login " + userVO.getLogin() + " j� existe!");
		} else
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao " + pType + " Usu�rio", "Usu�rio " + userVO.getName() + " j� existe!");
		FacesContext.getCurrentInstance().addMessage(null, message);
		newUserVO = new UserVO();
		listAllUsers();
	}

	// Salvar Novo
	public void saveUser(ActionEvent actionEvent) {
		saveUpdateUser(ConstantsUtil.SAVE);
	}

	// Atualizar
	public void updateUser(ActionEvent actionEvent) {
		saveUpdateUser(ConstantsUtil.UPDATE);
	}

	// Remover
	public void removeUser() {
		FacesMessage message = null;
		String name = userVO.getName();
		try {
			userBO.removeUser(userVO);
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Remover", "Usu�rio " + name + " removido com Sucesso");
		} catch (Exception e) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao Remover Usu�rio " + name + " ", e.toString());
		}
		FacesContext.getCurrentInstance().addMessage(null, message);
		listAllUsers();
	}

	// Validar Duplicidade de nome
	public boolean validateDuplicityName(UserVO pUserVO) {
		List<UserVO> lListUserVO = userBO.listUserLike(ConstantsUtil.NAME, pUserVO.getName());
		for (UserVO lUserVO : lListUserVO) {
			if (lUserVO.getName().equals(pUserVO.getName()) && lUserVO.getId() != pUserVO.getId()) {
				return false;
			}
		}
		return true;
	}

	// Validar Duplicidade de nome
	public boolean validateDuplicityLogin(UserVO pUserVO) {
		List<UserVO> lListUserVO = userBO.listUserLike(ConstantsUtil.LOGIN, pUserVO.getLogin());
		for (UserVO lUserVO : lListUserVO) {
			if (lUserVO.getLogin().equals(pUserVO.getLogin()) && lUserVO.getId() != pUserVO.getId()) {
				return false;
			}
		}
		return true;
	}

	// pegar usu�rio corrente
	public void listUserCurrent() {
		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication(); 
		if(authentication != null){    
            Object obj = authentication.getPrincipal();    
                
            if (obj instanceof UserVO){    
                UserVO userCurrent =  (UserVO) obj;
                System.out.println("--------"+userCurrent.getName());
            } else System.out.println("-------- user null");           
        } else System.out.println("-------- authentication null");
	}

}
