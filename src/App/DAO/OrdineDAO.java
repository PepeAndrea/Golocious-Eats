package App.DAO;

import App.Config.Database;
import App.Objects.Ordine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class OrdineDAO {

    /**********Attributi**********/

    private String table;
    private String fkTable;
    private ObservableList<Ordine> listaOrdini;
    private Database db;

    /**********Metodi**********/

    /**********Costruttori**********/

    public OrdineDAO() {
        this.db = new Database();
        this.table = "Ordine";
        this.fkTable = "Carrello";
    }

    /**********Metodi di funzionalità**********/

    public ObservableList<Ordine> getOrdini(Integer id) throws SQLException {
        this.listaOrdini = FXCollections.observableArrayList();
        this.db.setConnection();
        String sql = "SELECT * FROM "+this.table+" o inner join "+this.fkTable+" c on o.ordineid = c.carrelloid where clienteid = ?";
        PreparedStatement pstmt = this.db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){
            this.listaOrdini.add(new Ordine(rs.getInt("ordineid"),((Integer) rs.getInt("ristoranteid")).toString(),rs.getDate("dataordine").toString(), rs.getString("totale"), ((Integer) rs.getInt("riderid")).toString(),rs.getBoolean("consegnato")));
        }
        db.closeConnection();
        return this.listaOrdini;
    }

}
