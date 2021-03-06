package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import adaptateur.AdaptateurFormat;
import adaptateur.AudioMasterInterface;
import adaptateur.GestionFormat;
import model.AudioMaster;
import model.EnsembleGenre;
import model.Musique;
import model.Playlist;
import model.Utilisateur;
/**
 * Controlleur pour la page &diter playlist
 * @author Antonin
 *
 */
public class ServletEditerPlaylist extends HttpServlet {

    private static final long  serialVersionUID    = 1L;
    boolean                    count               = false;
    public static float        volume;
    private List<Musique>      tabMusiqueAjouter   = new ArrayList<Musique>();
    private List<Musique>      tabMusiqueSupprimer = new ArrayList<Musique>();

    public static final String CHAMP_LISTE         = "nomListe";
    public static AudioMaster  am                  = new AudioMaster();

    boolean                    firstClick          = false;
    public EnsembleGenre       ensembleGenre       = null;
    public static boolean      isPlaying           = false;
    public static float        pitch;

    protected void service( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        if ( session.getAttribute( "utilisateur" ) == null && request.getParameter( "editerPlaylist" ) == null ) {
            response.sendRedirect( request.getContextPath() + "/Accueil" );
        } else {

            // Actualisation de la page
            session.setAttribute( "nomPage", "EditerPlaylist" );

            // Instancation du son si il n'existe pas
            if ( session.getAttribute( "vol" ) == null )
                session.setAttribute( "vol", firstClick );

            // Instancation du click si il n'existe pas
            if ( session.getAttribute( "click" ) == null )
                session.setAttribute( "click", firstClick );

            // Instanaction de l'audio si il n'existe pas
            if ( session.getAttribute( "audio" ) == null )
                session.setAttribute( "audio", am );

            // Instancation du volume si il n'existe pas
            if ( session.getAttribute( "vol" ) == null )
                session.setAttribute( "vol", volume );

            // Instancation du pitch si il n'existe pas
            if ( session.getAttribute( "pitch" ) == null )
                session.setAttribute( "pitch", pitch );

            // On r�cup�re la liste souhait� � �tre modifi�e
            if ( request.getParameter( "editerPlaylist" ) != null ) {
                System.out.println( "editerPlaylist = " + (String) request.getParameter( "editerPlaylist" ) );
                session.setAttribute( "editerPlaylist", request.getParameter( "editerPlaylist" ) );
            }

            // Bouton ajouter

            if ( request.getParameter( "boutonAjouter" ) != null ) {
                try {
                    Playlist.ajouterMusique( (String) session.getAttribute( "editerPlaylist" ),
                            (String) request.getParameter( "boutonAjouter" ) );
                } catch ( Exception e ) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            // Bouton supprimer

            if ( request.getParameter( "boutonSupprimer" ) != null ) {
                try {
                    Playlist.supprimerMusique( (String) session.getAttribute( "editerPlaylist" ),
                            (String) request.getParameter( "boutonSupprimer" ) );
                } catch ( Exception e ) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            // On r�cup�re les musiques dont l'utilisateur ne poss�de pas dans
            // sa playlist

            try {
                tabMusiqueAjouter = Playlist.getMusiqueDisponible( (String) session.getAttribute( "editerPlaylist" ),
                        ( (Utilisateur) session.getAttribute( "utilisateur" ) ).getPseudo() );
            } catch ( Exception e ) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // On r�cup�re les musiques dont l'utilisateur poss�de dans sa
            // playlist

            try {
                tabMusiqueSupprimer = Playlist.getMusiqueActuel( (String) session.getAttribute( "editerPlaylist" ),
                        ( (Utilisateur) session.getAttribute( "utilisateur" ) ).getPseudo() );
            } catch ( Exception e ) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            session.setAttribute( "tabMusiqueMaMusique", tabMusiqueSupprimer );

            // Gestion de la musique
            GestionFormat.gererMusique(request, session);

            // Pr�paration des attributs
            request.setAttribute( "tabMusiqueAjouter", tabMusiqueAjouter );
            request.setAttribute( "tabMusiqueSupprimer", tabMusiqueSupprimer );
            request.setAttribute( "nomListe", (String) session.getAttribute( "editerPlaylist" ) );

            this.getServletContext().getRequestDispatcher( "/WEB-INF/EditerPlaylist.jsp" ).forward( request, response );
        }
    }
}
