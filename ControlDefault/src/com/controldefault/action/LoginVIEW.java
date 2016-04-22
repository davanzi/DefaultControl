package com.controldefault.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.controldefault.control.LoginBO;
import com.controldefault.control.UserBO;
import com.controldefault.model.UserVO;
import com.controldefault.util.ConstantsUtil;
import com.controldefault.util.FacesUtil;
import com.controldefault.util.LoggerUtil;

@Controller("loginView")
@Scope("request")
public class LoginVIEW {
	private String username;
	private List<UserVO> listUserVO;
	private UserVO userVO;
	private LoginBO loginBO;

	@Resource
	private UserBO userBO;

	//@Resource
	//private UserController userController;

	private boolean admin;

	public String getUsername() throws Exception {
		userVO = new UserVO();
		userVO.setLogin((SecurityContextHolder.getContext().getAuthentication().getName()));
		listUserVO = userBO.listUserByName("login", userVO.getLogin());
		this.username = listUserVO.get(0).getName();
		return username;
	}

	public boolean getAdmin() throws Exception {
		userVO = new UserVO();
		userVO.setLogin((SecurityContextHolder.getContext()
				.getAuthentication().getName()));
		userVO = userBO.listUserById(userVO.getId());
		this.username = userVO.getRoleVO().getName();
		if (this.username.equals(ConstantsUtil.PERFIL_ADMIN)) {
			admin = true;
		} else {
			admin = false;
		}
		return admin;
	}

	public String logar() {
		try {
			RequestDispatcher dispatcher = FacesUtil.getServletRequest()
					.getRequestDispatcher("/j_spring_security_check");
			dispatcher.forward(FacesUtil.getServletRequest(),
					FacesUtil.getServletResponse());
			FacesContext.getCurrentInstance().responseComplete();

			LoggerUtil.info(SecurityContextHolder.getContext()
					.getAuthentication().getName()
					+ " efetuou Login em " + new Date(), getClass());
			loginBO.start();

		} catch (Exception ex) {
			FacesUtil.exibirMensagemErro(ex.getMessage());
			return null;
		}
		return null;
	}

	public String logout() {
		try {
			RequestDispatcher dispatcher = FacesUtil.getServletRequest()
					.getRequestDispatcher("/j_spring_security_logout");
			dispatcher.forward(FacesUtil.getServletRequest(),
					FacesUtil.getServletResponse());
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception ex) {
			FacesUtil.exibirMensagemErro("Erro ao sair do sistema");
			return null;
		}
		return null;
	}

}