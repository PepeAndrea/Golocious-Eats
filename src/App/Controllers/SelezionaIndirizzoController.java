package App.Controllers;

import App.Objects.Cliente;
import App.Objects.Indirizzo;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public class SelezionaIndirizzoController {

    Indirizzo indirizzo;
    Cliente cliente;


    public SelezionaIndirizzoController() throws SQLException {
        this.indirizzo = new Indirizzo();
        this.cliente = Cliente.getInstance();
    }

    public ObservableList<Indirizzo> getIndirizzi() throws SQLException {
        return this.indirizzo.getIndirizzi();
    }

    public String setIndirizzoAttivo(Integer indirizzoid) throws SQLException {
        String messaggio = this.cliente.aggiornaIndirizzoAttivo(indirizzoid);
        if(messaggio.equals("indirizzo_aggiornato")) {
             cliente.setIndirizzoAttivo(indirizzoid);
        }
        return messaggio;
    }

}