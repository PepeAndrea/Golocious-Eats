package App.Scenes.Controller;

import App.Controller.AggiungiGestoreController;
import App.Controller.AggiungiRistoranteController;
import App.Controller.GestisciArticoliController;
import App.Controller.InserisciArticoloController;
import App.Objects.Articolo;
import App.Objects.Cliente;
import App.Objects.Gestore;
import App.Objects.Ristorante;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class GestisciRistoranteController extends BaseSceneController implements Initializable {

    /**********Attributi**********/

    Ristorante ristoranteAttivo;
    InserisciArticoloController inserisciArticoloController;
    GestisciArticoliController gestisciArticoliController;
    AggiungiRistoranteController aggiungiRistoranteController;
    AggiungiGestoreController aggiungiGestoreController;
    Gestore gestore;
    @FXML ComboBox<Ristorante> selezionaRistoranteBox;

    /**********Metodi**********/

    /**********Costruttori**********/

    public GestisciRistoranteController() throws SQLException {
        this.gestore = new Gestore(Cliente.getInstance());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selezionaRistoranteBox.setItems(this.gestore.getRistoranti());
    }

    /**********Metodi di bottoni**********/

    public void inserisciArticoloBtn(ActionEvent e) {
        if (selezionaRistoranteBox.getSelectionModel().getSelectedItem() != null) {
            ((ComboBox) getElementById("inserisciArticoloBox")).getItems().clear();
            try {
                setInserisciArticoliBox();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            resetBtnColor();
            resetVHBoxManagedAndVisible();
            sceneController.setVisibile("inserisciArticoloHBox", true);
            sceneController.setCliccatoBtn("inserisciArticoloBtn");
        } else {
            e.consume();
            setErroriSelezionaRistorante();
        }
    }

    public void gestisciArticoloBtn(ActionEvent e) {
        if (selezionaRistoranteBox.getSelectionModel().getSelectedItem() != null) {
            resetErroriGestisciArticoli();
            try {
                setGestisciArticoliBox();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            resetBtnColor();
            resetVHBoxManagedAndVisible();
            sceneController.setVisibile("gestisciArticoliVBox", true);
            sceneController.setCliccatoBtn("gestisciArticoliBtn");
        } else {
            e.consume();
            setErroriSelezionaRistorante();
        }
    }

    public void aggiungiRistoranteBtn() {
        resetErroriAggiungiRistorante();
        resetBtnColor();
        resetVHBoxManagedAndVisible();
        sceneController.setVisibile("aggiungiRistoranteHBox", true);
        sceneController.setCliccatoBtn("aggiungiRistoranteBtn");
    }

    public void rendiGestoreBtn(ActionEvent e) {
        if (selezionaRistoranteBox.getSelectionModel().getSelectedItem() != null) {
            resetErroriRendiGestore();
            resetBtnColor();
            resetVHBoxManagedAndVisible();
            sceneController.setVisibile("rendiGestoreVBox", true);
            sceneController.setCliccatoBtn("rendiGestoreBtn");
        } else {
            e.consume();
            setErroriSelezionaRistorante();
        }
    }

    public void selezionaRistoranteBtn() {
        resetErroriSelezionaRistorante();
        resetBtnColor();
        resetVHBoxManagedAndVisible();
        sceneController.setVisibile("selezionaRistoranteVBox", true);
        sceneController.setCliccatoBtn("selezionaRistoranteBtn");
    }



    public void aggiungiEsistenteBtn() {
    }

    public void aggiungiManualmenteBtn() {
    }

    public void abilitaBtn() throws SQLException {
        resetErroriGestisciArticoli();
        ComboBox<Articolo> gestisciArticolo = (ComboBox<Articolo>) getElementById("gestisciarticoloField");
        this.gestisciArticoliController = new GestisciArticoliController();
        Articolo articolo = gestisciArticolo.getSelectionModel().getSelectedItem();
        if(articolo != null) {
            int index = gestisciArticolo.getSelectionModel().getSelectedIndex();
            this.gestisciArticoliController.switchDisponibilitaArticolo(true, this.ristoranteAttivo, articolo);
            gestisciArticolo.setItems(gestisciArticoliController.getArticoliRistorante(this.ristoranteAttivo));
            gestisciArticolo.getSelectionModel().select(index);
        } else {
            setErroriGestisciArticoli();
        }
    }

    public void disabilitaBtn() throws SQLException {
        resetErroriGestisciArticoli();
        ComboBox<Articolo> gestisciArticolo = (ComboBox<Articolo>) getElementById("gestisciarticoloField");
        this.gestisciArticoliController = new GestisciArticoliController();
        Articolo articolo = gestisciArticolo.getSelectionModel().getSelectedItem();
        if(articolo != null) {
            int index = gestisciArticolo.getSelectionModel().getSelectedIndex();
            this.gestisciArticoliController.switchDisponibilitaArticolo(false, this.ristoranteAttivo, articolo);
            gestisciArticolo.setItems(gestisciArticoliController.getArticoliRistorante(this.ristoranteAttivo));
            gestisciArticolo.getSelectionModel().select(index);
        } else {
            setErroriGestisciArticoli();
        }
    }

    public void eliminaBtn() throws SQLException {
        resetErroriGestisciArticoli();
        this.gestisciArticoliController = new GestisciArticoliController();
        Articolo articolo = ((ComboBox<Articolo>) getElementById("gestisciarticoloField")).getSelectionModel().getSelectedItem();
        if(articolo != null) {
            String messaggio = this.gestisciArticoliController.eliminaArticoloDaMenu(this.ristoranteAttivo, articolo);
            if(messaggio.equals("eliminazione_articolo_fallita")) {
                errore("erroreGestisciarticoloLabel", "L'eliminazione non è riuscita", true);
            }
        } else {
            setErroriGestisciArticoli();
        }
    }

    public void aggiungiNuovoRistoranteBtn() throws Exception {
        resetErroriAggiungiRistorante();
        String nome = ((TextField) getElementById("nomeristoranteField")).getText();
        String indirizzo = ((TextField) getElementById("indirizzoristoranteField")).getText();
        String telefono = ((TextField) getElementById("telefonoristoranteField")).getText();
        LocalDate dataApertura = ((DatePicker) getElementById("dataaperturaristoranteField")).getValue();
        if(nome.length() > 0 && indirizzo.length() > 0 && telefono.length() > 0 && dataApertura != null && dataApertura.isBefore(LocalDate.now())) {
            this.aggiungiRistoranteController = new AggiungiRistoranteController();
            String messaggio = this.aggiungiRistoranteController.aggiungiRistorante(new Ristorante(nome, indirizzo, telefono, dataApertura));
            if(messaggio.equals("gestore_aggiunto")) {
                ((Label) getElementById("erroreAggiungiRistoranteLabel")).setText("Ristorante aggiunto con successo");
            } else {
                setErroriDB(messaggio);
            }
        } else {
            setErroriAggiungiRistorante(nome, indirizzo, telefono, dataApertura);
        }
    }

    public void aggiungiGestoreBtn() throws SQLException {
        resetErroriRendiGestore();
        this.aggiungiGestoreController = new AggiungiGestoreController();
        String email = ((TextField) getElementById("gestoreField")).getText();
        if(email.length() > 0) {
            String messaggio = this.aggiungiGestoreController.rendiGestore(email, ristoranteAttivo);
            if (messaggio.equals("gestore_aggiunto")) {
                resetCampiRendiGestore();
                ((Label) getElementById("erroreGestoreLabel")).setText("Gestore aggiunto con successo");
            } else {
                setErroriRendiGestore(messaggio);
                setErroriDB(messaggio);
            }
        } else {
            setErroriRendiGestore("email_vuota");
        }
    }

    public void selezionaRistoranteBox(){
        this.ristoranteAttivo = ((ComboBox<Ristorante>) getElementById("selezionaristoranteField")).getSelectionModel().getSelectedItem();
    }

    /**********Metodi di supporto**********/

    private void setInserisciArticoliBox() throws SQLException{
        this.inserisciArticoloController = new InserisciArticoloController();
        ((ComboBox) getElementById("inserisciarticoloField")).setItems(inserisciArticoloController.getArticoliAltriRistoranti(this.ristoranteAttivo));
    }

    private void setGestisciArticoliBox() throws SQLException {
        this.gestisciArticoliController = new GestisciArticoliController();
        ((ComboBox) getElementById("gestisciarticoloField")).setItems(gestisciArticoliController.getArticoliRistorante(this.ristoranteAttivo));
    }

    /**********Metodi di ripristino e di errori**********/

    private void resetVHBoxManagedAndVisible() {
        sceneController.setVisibile("inserisciArticoloHBox", false);
        sceneController.setVisibile("gestisciArticoliVBox", false);
        sceneController.setVisibile("aggiungiRistoranteHBox", false);
        sceneController.setVisibile("rendiGestoreVBox", false);
        sceneController.setVisibile("selezionaRistoranteVBox", false);
    }

    private void resetBtnColor() {
        if(getElementById("inserisciArticoloHBox").isVisible()) {
            getElementById("inserisciArticoloBtn").setStyle("-fx-background-color: #fab338; -fx-hovered-cursor: pointer");
        } else if(getElementById("gestisciArticoliVBox").isVisible()) {
            getElementById("gestisciArticoliBtn").setStyle("-fx-background-color: #fab338; -fx-hovered-cursor: pointer");
        } else if(getElementById("aggiungiRistoranteHBox").isVisible()) {
            getElementById("aggiungiRistoranteBtn").setStyle("-fx-background-color: #fab338; -fx-hovered-cursor: pointer");
        } else if(getElementById("rendiGestoreVBox").isVisible()) {
            getElementById("rendiGestoreBtn").setStyle("-fx-background-color: #fab338; -fx-hovered-cursor: pointer");
        } else if(getElementById("selezionaRistoranteVBox").isVisible()) {
            getElementById("selezionaRistoranteBtn").setStyle("-fx-background-color: #fab338; -fx-hovered-cursor: pointer");
        }
    }

    private void setErroriGestisciArticoli() {
        errore("erroreGestisciarticoloLabel", "Seleziona un articolo", true);
    }

    private void resetErroriGestisciArticoli() {
        inizializzaLabel("erroreGestisciarticoloLabel", true);
    }

    private void setErroriAggiungiRistorante(String nome, String indirizzo, String telefono, LocalDate dataApertura) {
        if(nome.length()==0){
            errore("erroreNomeristoranteLabel", "Inserisci un nome", true);
        }
        if(indirizzo.length()==0){
            errore("erroreIndirizzoristoranteLabel", "Inserisci un indirizzo", true);
        }
        if(telefono.length()==0){
            errore("erroreTelefonoristoranteLabel", "Inserisci un numero di telefono", true);
        }
        if(dataApertura==null) {
            errore("erroreDataaperturaristoranteLabel", "Seleziona una data di apertura", true);
        } else if(dataApertura.isAfter(LocalDate.now())) {
            errore("erroreDataaperturaristoranteLabel", "La data deve essere passata", true);
        }
    }

    private void resetErroriAggiungiRistorante() {
        inizializzaLabel("erroreNomeristoranteLabel", true);
        inizializzaLabel("erroreIndirizzoristoranteLabel", true);
        inizializzaLabel("erroreTelefonoristoranteLabel", true);
        inizializzaLabel("erroreDataaperturaristoranteLabel", true);
    }

    private void setErroriRendiGestore(String messaggio) {
        switch (messaggio) {
            case "email_vuota" -> errore("erroreGestoreLabel", "Inserisci un'email", true);
            case "errore_inserimento_gestore" -> errore("erroreGestoreLabel", "Si è verificato un errore", true);
            case "utente_non_trovato" -> errore("erroreGestoreLabel", "L'utente non è stato trovato", true);
        }
    }

    private void resetErroriRendiGestore() {
        inizializzaLabel("erroreGestoreLabel", true);
    }

    private void resetCampiRendiGestore() {
        ((TextField) getElementById("gestoreField")).setText("");
    }

    private void setErroriSelezionaRistorante() {
        errore("erroreSelezionaristoranteLabel", "Prima di procedere seleziona un ristorante", true);
    }

    private void resetErroriSelezionaRistorante() {
        inizializzaLabel("erroreSelezionaRistoranteLabel", false);
        getElementById("selezionaRistoranteBox").setStyle("-fx-border-color: transparent");
    }

    private void setErroriDB(String messaggio) {
        switch (messaggio) {
            case "uq_gestore" -> errore("erroreGestoreLabel", "L'utente è già un gestore del ristorante", true);
            case "ristorante_non_aggiunto" -> errore("erroreAggiungiRistoranteLabel", "Il ristorante non è stato aggiunto", false);
            case "ristorante_nome_key" -> errore("erroreAggiungiRistoranteLabel", "Il nome è già esistente", false);
            case "uq_telefono_ristorante" -> errore("erroreAggiungiRistoranteLabel", "Il telefono è già esistente", false);
            case "uq_menu" -> errore("", "", false);//FIXME
        }
    }

}