package com.controldefault.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

// Defini��o de entidade
@Entity
// Rela��o da entidade com o banco
@Table(name = "role")
public class RoleVO implements Serializable {
	// Defini��es para persistencia de auto increment
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column
	private String name;

	@Column
	private String description;

	@Column
	private String remark;

	@OneToMany(mappedBy = "roleVO", fetch = FetchType.LAZY)
	@Cascade(CascadeType.ALL)
	private List<UserVO> listEmployeeVO = new ArrayList<UserVO>();

	// Construtor vazio
	public RoleVO() {
	}

	// Construtor com todos os par�metros
	public RoleVO(int pId, String pName, String pRemark) {
		this.id = pId;
		this.name = pName;
		this.remark = pRemark;
	}

	// Getters e Setters
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String pRemark) {
		this.remark = pRemark;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<UserVO> getListEmployeeVO() {
		return listEmployeeVO;
	}

	public void setListEmployeeVO(List<UserVO> listEmployeeVO) {
		this.listEmployeeVO = listEmployeeVO;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoleVO other = (RoleVO) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		return true;
	}

}
