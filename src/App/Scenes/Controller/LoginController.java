package App.Scenes.Controller;

import App.Controllers.AuthController;
import java.sql.SQLException;
import java.time.LocalDate;

public class LoginController extends BaseSceneController {

    AuthController auth;


    public LoginController() throws SQLException {
        this.auth = new AuthController();
    }

    public void loginBtnClick() throws Exception {
        if(getVisibility("nomeField")){
            setVisibility("nomeField", false);
            setVisibility("cognomeField", false);
            setVisibility("telefonoField", false);
            setVisibility("dataNascitaPicker", false);
        } else {
            String email = getValue("emailField", "textfield");
            String password = getValue("passwordField", "passwordfield");
            if(auth.Login(email,password)) {
                this.sceneController.setScene("HomeCliente");
            } else {
                System.out.println("errore di login");
            }
        }
    }

    public void creaAccBtnClick() throws Exception {
        if (!getVisibility("nomeField")) {
            setVisibility("nomeField", true);
            setVisibility("cognomeField", true);
            setVisibility("telefonoField", true);
            setVisibility("dataNascitaPicker", true);
        } else {
            String nome = getValue("nomeField", "textfield");
            String cognome = getValue("cognomeField", "textfield");
            String email = getValue("emailField", "textfield");
            String password = getValue("passwordField", "passwordfield");
            String telefono = getValue("telefonoField", "textfield");
            LocalDate dataNascita = getValue("dataNascitaPicker");
            if(auth.Register(nome, cognome, email, password, telefono, dataNascita)){
                this.sceneController.setScene("HomeCliente");
            } else {
                System.out.println("errore di registrazione");
            }
        }
    }

}