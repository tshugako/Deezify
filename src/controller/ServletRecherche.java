package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import adaptateur.AdaptateurFormat;
import adaptateur.AudioMasterInterface;
import adaptateur.GestionFormat;
import model.AudioMaster;
import model.EnsembleGenre;
import model.ListeMusique;

/**
 * Controlleur de la page Recherche.jsp
 * 
 * @author guill
 *
 */



@WebServlet( name = "ServletRecherche" )
public class ServletRecherche extends HttpServlet {
    
    protected void service( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
    	
        
        HttpSession session = request.getSession();
        EnsembleGenre ensembleGenre = (EnsembleGenre) session.getAttribute("ensembleGenre");
        
        // Gestion de la musique
        GestionFormat.gererMusique(request, session);
        
    	session.setAttribute( "nomPage", "Recherche" );
    
		try {
			ensembleGenre.effectuerRecherche( request );
			this.getServletContext().getRequestDispatcher( "/WEB-INF/Recherche.jsp" ).forward( request, response );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

        
    }

}