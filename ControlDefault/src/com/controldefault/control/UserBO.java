package com.controldefault.control;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.controldefault.dao.GenericDAO;
import com.controldefault.model.UserVO;
import com.controldefault.model.RoleVO;

// Anotação do Spring que representa um bean de serviço
@Service
public class UserBO {

	private UserVO userVO;
	private GenericDAO genericDAO;
	private List<UserVO> listUserVO;
	private List<Object> listObject;

	// Construtor do bean de serviço
	public UserBO() {
		userVO = new UserVO();
		listUserVO = new ArrayList<UserVO>();
		genericDAO = new GenericDAO(UserVO.class);
	}

	// Método de salvar ou atualizar do bean de serviço
	public int saveUpdateUser(UserVO pUserVO) throws Exception {
		if ((!pUserVO.getName().equals(""))) {
			userVO = pUserVO;
			if (genericDAO.saveUpdate(userVO)) {
				return 1;
			} else {
				return 0;
			}
		} else {
			return -1;
		}
	}

	// Método de remoção do bean de serviço
	public int removeUser(UserVO pUserVO) throws Exception {
		if (pUserVO != null) {
			if (genericDAO.remove(pUserVO)) {
				return 1;
			} else {
				return 0;
			}
		} else {
			return -1;
		}
	}

	// Método para listar por nome
	public List<UserVO> listUserByName(String pName, String pValue) {
		try {
			listObject = genericDAO.listByName(pName, pValue);
		} catch (Exception e) {			
			e.printStackTrace();
		}
		for (Object obj : listObject) {
			listUserVO.add((UserVO) obj);
		}
		return listUserVO;
	}
	
	// Método para listar como...
	public List<UserVO> listUserLike(String pName, String pValue) {
		listUserVO.clear();
		try {
			listObject = genericDAO.listLike(pName, pValue);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		for (Object obj : listObject) {
			listUserVO.add((UserVO) obj);
		}
		return listUserVO;
	}

	// Método para listar por ID
	public UserVO listUserById(int pId) throws Exception {
		if (pId == 0) {
			return null;
		}
		userVO = (UserVO) genericDAO.listById(pId);
		return userVO;
	}
	
	// Método para listar todos
	public List<UserVO> listUsers() {
		listUserVO = new ArrayList<UserVO>();
		try {
			listObject = genericDAO.listAll("from UserVO");			
		} catch (Exception ex) {			
			ex.printStackTrace();
		}
		for (Object obj : listObject) {
			listUserVO.add((UserVO) obj);			
		}		
		return listUserVO;
	}

}
