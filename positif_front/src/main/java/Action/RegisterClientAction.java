package Action;


import Controller.ActionServlet;
import fr.insalyon.dasi.positif.service.Services;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tcadet
 */
public class RegisterClientAction extends Action {

    @Override
    public void execute(HttpServletRequest request) {

        /*Map<String, String[]> m = request.getParameterMap();
        m.forEach((k,v) -> System.out.println("key: "+k));*/
        
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String strDate = request.getParameter("birthDate");
        Date birthDate = null;
        if(strDate!=null) {
            try {
                birthDate = ActionServlet.DATE.parse(strDate);
            } catch (ParseException ex) {
                Logger.getLogger(RegisterClientAction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }    
        String honorific = request.getParameter("honorific");
        String address = request.getParameter("address");
        String tel = request.getParameter("tel");
        String email = request.getParameter("email");       
        
        if(Services.verifierInformationsInscriptionClient(firstName, lastName, birthDate, honorific, address, tel, email)) {
            Services.inscrireClient(firstName, lastName, birthDate, honorific, address, tel, email);
            request.setAttribute("success", true);
        }
        else {
            request.setAttribute("success", false);
        }
    }
    
}
