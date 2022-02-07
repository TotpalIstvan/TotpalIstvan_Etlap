package hu.petrik.etlap.db;

import hu.petrik.etlap.Etlap;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Etlapdb {
    Connection conn;

    public Etlapdb() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/etlapdb", "root", "");
        }catch (SQLException e){
            System.out.println(e);
        }
    }
    public List<Etlap> getEtlap() throws SQLException {
        List<Etlap> menu = new ArrayList<>();
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM etlap;";
        ResultSet result = stmt.executeQuery(sql);
        while (result.next()) {
            int id = result.getInt("id");
            String name = result.getString("nev");
            String description = result.getString("leiras");
            int cost = result.getInt("ar");
            String category = result.getString("kategoria");
            Etlap etelek = new Etlap(id, cost, name, description, category);
            menu.add(etelek);
        }
        return menu;
    }

}
