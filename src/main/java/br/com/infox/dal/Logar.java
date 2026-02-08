/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.infox.dal;

import br.com.infox.telas.TelaPrincipal;
import br.com.infox.dal.ModuloConexao;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author jeruso
 */
public class Logar {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public void logar(String usuario, String senha) {
        
        conexao = ModuloConexao.conector();

        if (conexao == null) {
            JOptionPane.showMessageDialog(null, "Sem conexão com o banco");
            return;
        }

        String sql = "select * from tbusuarios where login=? and senha=?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, usuario);
            pst.setString(2, senha);

            rs = pst.executeQuery();

            if (rs.next()) {
                // a linha baixo obtem o conteúdo do campo perfil da tabela tbusuario
                String perfil = rs.getString(6);
                //System.out.println(perfil)
                // Fazendo tratamento do perfil do usuário
                if (perfil.equals("admin")) {
                    TelaPrincipal principal = new TelaPrincipal();
                    principal.setVisible(true);
                    //liberar itens do nemu que estava desativado
                    TelaPrincipal.menRel.setEnabled(true);
                    TelaPrincipal.menCadUsu.setEnabled(true);
                    TelaPrincipal.lblUsuario.setText(rs.getString(2));
                    TelaPrincipal.lblUsuario.setForeground(Color.red);

                } else {
                    TelaPrincipal principal = new TelaPrincipal();
                    principal.setVisible(true);
                    TelaPrincipal.lblUsuario.setText(rs.getString(2));

                }

            } else {
                JOptionPane.showMessageDialog(null, "Usuário e/ou senha inválido(s)");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao logar: " + e.getMessage());
        }
    }

}
