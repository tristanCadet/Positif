package Controller;


import Action.Action;
import Action.CommencerVoyanceAction;
import Action.DivinationAction;
import Action.GenererPredictionAction;
import Action.GetHistoryAction;
import Action.GetMediumListAction;
import Action.LoginAction;
import Action.LoginEmployeAction;
import Action.NombreVoyancesParEmployeAction;
import Action.NombreVoyancesParMediumAction;
import Action.ObtenirVoyanceDemandeeAction;
import Action.RegisterClientAction;
import Action.RepartitionVoyancesParEmployeAction;
import Action.TerminerVoyanceAction;
import Action.ValiderVoyanceAction;
import Action.VoyanceGetClientAction;
import Action.VoyanceGetClientHistoryAction;
import Action.VoyanceGetMediumAction;
import View.Serialization;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.insalyon.dasi.positif.dao.JpaUtil;
import fr.insalyon.dasi.positif.modele.Client;
import fr.insalyon.dasi.positif.modele.Employe;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tcadet
 */
@WebServlet(name = "ActionServlet", urlPatterns = {"/ActionServlet"})
public class ActionServlet extends HttpServlet {
    public static final SimpleDateFormat HOUR = new SimpleDateFormat("HH:mm:ss");
    public static final SimpleDateFormat DATE = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat DATE_HOUR = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final Gson gson = new GsonBuilder().create();
    
    @Override
    public void init() throws ServletException {
        super.init();
        JpaUtil.init();
    }

    @Override
    public void destroy() {
        super.destroy();
        JpaUtil.destroy();
    }
    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String todo = request.getParameter("todo");
        System.out.println("service HTTPServletRequest");
        System.out.println(todo);
        
        HttpSession session = request.getSession(true);
                
        switch(todo) {
            case "registerClient" : {
                Action action = new RegisterClientAction();
                action.execute(request);
                Serialization.outputResponse(request, response);
                break;
            }
            case "login" : {
                Action action = new LoginAction();
                action.execute(request);
                Serialization.outputResponse(request, response);
                Client c = (Client)request.getAttribute("data");
                session.setAttribute("user", c);
                
                break;
            }
            case "deconnexion" : {
                session.invalidate();
                break;
            }
            case "session" : {
                request.setAttribute("data", session.getAttribute("user"));
                Serialization.outputResponse(request, response);
                
                break;
            }
            case "historiqueClient" : {
                Action action = new GetHistoryAction((Client)session.getAttribute("user"));
                action.execute(request);
                Serialization.outputResponse(request, response);
                
                break;
            }
            case "getMediumList" : {
                Action action = new GetMediumListAction();
                action.execute(request);
                Serialization.outputResponse(request, response);
                break;
            }
            case "divination" : {
                Action action = new DivinationAction(session);
                action.execute(request);
                Serialization.outputResponse(request, response);
                break;
            }
            case "loginEmploye" : {
                Action action = new LoginEmployeAction();
                action.execute(request);
                Serialization.outputResponse(request, response);
                Employe e = (Employe)request.getAttribute("data");
                session.setAttribute("user", e);
            }
            case "obtenirVoyanceDemandee" : {
                Action a = new ObtenirVoyanceDemandeeAction(session);
                a.execute(request);
                Serialization.outputResponse(request, response);                
                break;
            }
            case "voyanceGetMedium" : {
                Action a = new VoyanceGetMediumAction(session);
                a.execute(request);
                Serialization.outputResponse(request, response);                
                break;
            }
            case "voyanceGetClient" : {
                Action a = new VoyanceGetClientAction(session);
                a.execute(request);
                Serialization.outputResponse(request, response);                
                break;
            }
            case "voyanceGetClientHistory" : {
                Action a = new VoyanceGetClientHistoryAction(session);
                a.execute(request);
                Serialization.outputResponse(request, response);                
                break;
            }
            case "prediction" : {
                Action a = new GenererPredictionAction(session);
                a.execute(request);
                Serialization.outputResponse(request, response);                
                break;
            }
            case "commencerVoyance" : {
                Action a = new CommencerVoyanceAction(session);
                a.execute(request);
                Serialization.outputResponse(request, response);                
                break;
            }
            case "terminerVoyance" : {
                Action a = new TerminerVoyanceAction(session);
                a.execute(request);
                Serialization.outputResponse(request, response);                
                break;
            }
            case "validerVoyance" : {
                Action a = new ValiderVoyanceAction(session);
                a.execute(request);
                Serialization.outputResponse(request, response);                
                break;
            }
            case "nombreVoyancesParMedium" : {
                Action a = new NombreVoyancesParMediumAction();
                a.execute(request);
                Serialization.outputResponse(request, response);                
                break; 
            }
            case "nombreVoyancesParEmploye" : {
                Action a = new NombreVoyancesParEmployeAction();
                a.execute(request);
                Serialization.outputResponse(request, response);                
                break; 
            }
            case "repartitionVoyancesParEmploye" : {
                Action a = new RepartitionVoyancesParEmployeAction();
                a.execute(request);
                System.out.println("dddddddddd");
                System.out.println(request.getAttribute("data").toString());
                Serialization.outputResponse(request, response);   
                break; 
            }
            default: {
                System.err.println("La requête " + request.toString() + " (todo = " + todo + " n'est pas une requête valide.");
                break;
            }
        }
        System.out.println("Fin de service");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("POST");
        service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("GET");
        service(req, resp);
    }

    public ActionServlet() {
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        super.service(req, res); //To chan;ge body of generated methods, choose Tools | Templates.
        System.out.println("service ServletRequest");
    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doTrace(req, resp); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doOptions(req, resp); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doHead(req, resp); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected long getLastModified(HttpServletRequest req) {
        return super.getLastModified(req); //To change body of generated methods, choose Tools | Templates.
    }
    
}
