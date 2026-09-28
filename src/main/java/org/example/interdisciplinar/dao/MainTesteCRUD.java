package org.example.interdisciplinar.dao;

import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dao.*;
import org.example.interdisciplinar.model.*;
import java.util.*;

import java.sql.Connection;
import java.time.LocalDate;

public class MainTesteCRUD {
    public static void main(String[] args) {
        Connection conn = GerenteConexao.conectar();
    }
}