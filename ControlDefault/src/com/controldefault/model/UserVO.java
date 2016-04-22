package com.controldefault.model;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

import com.controldefault.control.RoleBO;
import com.controldefault.util.EncryptionUtil;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

// Defini��o de entidade
@Entity
// Rela��o da entidade com o banco
@Table(name = "user")
public class UserVO implements Serializable {

	// Defini��es para persistencia de auto increment
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column
	private String name;

	@Column
	private String cpf;

	@Column
	private String remark;

	@Column
	private String login;

	@Column(name = "passwd")
	private String password;

	// Defini��es para persist�ncia de chave estrangeira
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", insertable = true, updatable = true, nullable = false)
    @Fetch(FetchMode.JOIN)
    //@Cascade(CascadeType.ALL)
	private RoleVO roleVO;

	// Construtor Vazio
	public UserVO() {
	}

	// Construtor com todos os par�metros
	public UserVO(int pId, String pName, String pCpf, String pRemark, String pLogin, String pPassword, RoleVO pRoleVO) {
		this.id = pId;
		this.name = pName;
		this.cpf = pCpf;
		this.remark = pRemark;
		this.login = pLogin;
		this.password = pPassword;
		this.roleVO = pRoleVO;
	}

	// Getters e Setters do bean Empregado (User)
	public int getId() {
		return id;
	}

	public void setId(int pId) {
		this.id = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String pName) {
		this.name = pName;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String pCpf) {
		this.cpf = pCpf.replace(".", "").replace("-", "");
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String pRemark) {
		this.remark = pRemark;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String pLogin) {
		this.login = pLogin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String pPassword) throws NoSuchAlgorithmException {
		if (pPassword==null) {
			pPassword = "control@10";
			this.password = pPassword;
		} else this.password = EncryptionUtil.toPassword(pPassword);

	}

	public RoleVO getRoleVO() {
		return roleVO;
	}

	public void setRoleVO(RoleVO pRoleVO) {
		this.roleVO = pRoleVO;
	}

}
