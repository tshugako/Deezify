package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AudioMaster;
import model.EnsembleGenre;

/**
 * Controlleur de la page Explorer.jsp
 * 
 * @author guill
 *
 */

@WebServlet( name = "ServletExplorer" )
public class ServletExplorer extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void service( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        session.setAttribute( "nomPage", "Explorer" );

        // Cr�ation de l'objet ensembleGenre en session si il n'existe pas
        if ( session.getAttribute( "ensembleGenre" ) == null ) {
            EnsembleGenre e = new EnsembleGenre();
            try {
                e.remplir();
            } catch ( SQLException e1 ) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            session.setAttribute( "ensembleGenre", e );
        }

        // Gestion Pause/Lecture
        if ( request.getParameter( "boutonPlay" ) != null && session.getAttribute( "audio" ) != null ) {
            if ( (boolean) ( session.getAttribute( "count" ) ) == false ) {
                ( (AudioMaster) session.getAttribute( "audio" ) ).pause();
                session.setAttribute( "count", true );
            } else {
                ( (AudioMaster) session.getAttribute( "audio" ) ).continuer();
                session.setAttribute( "count", false );
            }
        }
        /*
         * Barre de recherche
         */
        String recherche = (String) request.getParameter( "recherche" );
        if ( request.getParameter( "recherche" ) != null )
            System.out.println( recherche );

        /*
         * Affichage des images 'genre' depuis la base de donnees
         */

        request.setAttribute( "tabGenre", session.getAttribute( "ensembleGenre" ) );

        /*
         * Redirection vers Explorer.jsp
         */
        this.getServletContext().getRequestDispatcher( "/WEB-INF/Explorer.jsp" ).forward( request, response );

    }

}
