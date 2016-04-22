package com.controldefault.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.model.SelectItem;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.controldefault.model.UserVO;
import com.controldefault.util.ConstantsUtil;


@Controller
@Scope("session")
public class LoginBO implements Serializable {

        private static final long serialVersionUID = 1L;

        private UserVO usuario;
        private List<UserVO> listaUsuarios;

        private List<SelectItem> autoridades;
        private String confirmacaoSenha;

        @Resource
        private UserBO usuarioService;
        
        public String start() {
                atualizarTela();
                return "cadastroUsuario.xhtml";
        }

        /**
         * Limpa os campos input e atualiza a lista de usuários cadastrados
         */
        public void atualizarTela() {
                usuario = new UserVO();
                listaUsuarios = new ArrayList<UserVO>();
                confirmacaoSenha = "";
                listaUsuarios = usuarioService.listUsers();
        }

        /**
         * Grava novo registro ou atualiza um registro
         */
/*        public void gravar() {
                try {
                        if (usuario.getPassword().equals(confirmacaoSenha)                                        
                                        && !usuario.getLogin().isEmpty()
                                        && !usuario.getName().isEmpty()
                                        && !usuario.getPassword().isEmpty()) {

                                usuarioService.gravar(getUsuario());
                                MessagesController.msgUsuarioCadastrado();
                                atualizarTela();
                        } else if (!usuario.getPassword().equals(confirmacaoSenha)) {
                                MessagesController.msgSenhaNaoConfere();
                        } else {
                                MessagesController.msgFormIncorreto();
                        }
                } catch (Exception e) {

                        e.printStackTrace();
                }

        }*/

        /**
         * Exclui um registro da tabela usuario
         * @throws Exception 
         */
        public void excluir() throws Exception {
                usuarioService.removeUser(getUsuario());
                atualizarTela();
        }

        public List<UserVO> getListaUsuarios() {
                return listaUsuarios;
        }
        
        public UserVO procuraPor(UserVO usuario) throws Exception {
                return this.usuarioService.listUserById(usuario.getId());
        }

        public void setListaUsuarios(List<UserVO> listaUsuarios) {
                this.listaUsuarios = listaUsuarios;
        }

        public UserVO getUsuario() {
                return usuario;
        }

        public void setUsuario(UserVO usuario) {
                this.usuario = usuario;
        }

        public void setAutoridades(List<SelectItem> autoridades) {
                this.autoridades = autoridades;
        }

        public List<SelectItem> getAutoridades() {
                this.autoridades = new ArrayList<SelectItem>();
                this.autoridades.add(new SelectItem(ConstantsUtil.PERFIL_USER));
                this.autoridades.add(new SelectItem(ConstantsUtil.PERFIL_CLIENT));
                this.autoridades.add(new SelectItem(ConstantsUtil.PERFIL_DEVELOPER));
                this.autoridades.add(new SelectItem(ConstantsUtil.PERFIL_ADMIN));
                return autoridades;
        }

        public String getConfirmacaoSenha() {
                return confirmacaoSenha;
        }

        public void setConfirmacaoSenha(String confirmacaoSenha) {
                this.confirmacaoSenha = confirmacaoSenha;
        }
        
}