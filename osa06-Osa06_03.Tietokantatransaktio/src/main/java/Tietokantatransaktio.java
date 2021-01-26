
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tietokantatransaktio {

    private Connection connect() {

        String url = "jdbc:sqlite:test.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void addInventory(List<String> materiaalit, List<String> varastot, List<Integer> maarat) throws SQLException {

        Connection conn = this.connect();
        for (int i = 0; i < materiaalit.size(); i++) {
            String sqlMaterial = "INSERT INTO materiaalit(kuvaus) VALUES(?)";

            String sqlInventory = "INSERT INTO inventaario(materiaali_id,varasto,maara)"
                    + "VALUES(?,?,?)";

            ResultSet rs = null;

            PreparedStatement stmt1 = null, stmt2 = null;

            try {

                conn = this.connect();
                if (conn == null) {
                    return;
                }

                conn.setAutoCommit(false);

                stmt1 = conn.prepareStatement(sqlMaterial,
                        Statement.RETURN_GENERATED_KEYS);

                stmt1.setString(1, materiaalit.get(i));
                int rowAffected = stmt1.executeUpdate();

                rs = stmt1.getGeneratedKeys();
                int materialId = 0;
                if (rs.next()) {
                    materialId = rs.getInt(1);
                }

                if (rowAffected != 1) {
                    conn.rollback();
                }

                stmt2 = conn.prepareStatement(sqlInventory);
                stmt2.setInt(1, materialId);
                stmt2.setString(2, varastot.get(i));
                stmt2.setInt(3, maarat.get(i));

                stmt2.executeUpdate();

            } catch (SQLException e1) {
                try {
                    if (conn != null) {
                        conn.rollback();
                    }
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
                System.out.println(e1.getMessage());
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (stmt1 != null) {
                        stmt1.close();
                    }
                    if (stmt2 != null) {
                        stmt2.close();
                    }
                    if (conn != null) {
                        conn.commit();
                        conn.close();
                    }
                } catch (SQLException e3) {
                    System.out.println(e3.getMessage());
                }
            }
        }

    }

    public static void main(String[] args) throws SQLException {
        Tietokantatransaktio app = new Tietokantatransaktio();
        List<String> materiaalit = new ArrayList<>();
        List<String> varastot = new ArrayList<>();
        List<Integer> maarat = new ArrayList<>();
        materiaalit = Arrays.asList("lenovo", "huawei", "hp", "acer", "asus");
        varastot = Arrays.asList("helsinki", "espoo", "vantaa", "sipoo", "muonio");
        maarat = Arrays.asList(1, 5, 7, 2, 6);

        app.addInventory(materiaalit, varastot, maarat);

    }

}
