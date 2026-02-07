/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.infox.dal;

import java.sql.*;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

public class Consultar {
    
    private void carregarPerfis(JComboBox<String> cboPerfil) {
        cboPerfil.removeAllItems();
        cboPerfil.addItem("admin");
        cboPerfil.addItem("user");
    }


    // Consulta o usuário pelo ID e preenche os campos
    public boolean consultarUsuario(
            String idUser,
            JTextField txtUsuNome,
            JTextField txtUsuLogin,
            JTextField txtUsuFone,
            JComboBox<String> cboPerfil
    ) {
        Connection conexao = ModuloConexao.conector();
        if (conexao == null) {
            JOptionPane.showMessageDialog(null, "Sem conexão com o banco");
            return false;
        }

        String sql = "SELECT * FROM tbusuarios WHERE iduser = ?";

        try (PreparedStatement pst = conexao.prepareStatement(sql)) {
            pst.setString(1, idUser);  // se iduser for INT, usar pst.setInt
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                
                carregarPerfis(cboPerfil);
                
                txtUsuNome.setText(rs.getString("nome"));
                txtUsuLogin.setText(rs.getString("login"));
                txtUsuFone.setText(rs.getString("fone"));
                cboPerfil.setSelectedItem(rs.getString("perfil"));
                return true;
            } else {
                limparCampos(txtUsuNome, txtUsuLogin, txtUsuFone, cboPerfil);
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar: " + e.getMessage());
            return false;
        }
    }

    private void limparCampos(JTextField nome, JTextField login, JTextField fone, JComboBox<String> perfil) {
        nome.setText("");
        login.setText("");
        fone.setText("");
        perfil.setSelectedIndex(0);
    }
}
