package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import fabrique.objet.FabriqueObjet;
import fabrique.objet.FabriqueObjetAbstraite;
import strategie.comportementRecherche.RechercheAlbumStrategie;
import strategie.comportementRecherche.RechercheMusiqueStrategie;
import strategie.comportementRecherche.RecherchePlaylistStrategie;
import strategie.comportementRecherche.RechercheStrategie;
/**
 * Permet de d'instancier en une seule toutes les musiques de chaque playlist et
 * album de chaque genre
 * 
 * @author guill
 *
 */

public class EnsembleGenre {

    private  ArrayList<Genre>   tabGenre = null;
    private DatabaseConnection db       = null;
    protected RechercheStrategie rechercheStrategie;
    

    /**
     * Constructeur qui instancie la base et un tableau de genres
     */

    public EnsembleGenre() {
        this.tabGenre = new ArrayList<Genre>();
        rechercheStrategie = new RechercheMusiqueStrategie();
        try {
            db = DatabaseConnection.getInstance();
        } catch ( Exception e ) {
            e.getMessage();
        }

    }

    /**
     * M�me chose, sauf qu'on instancie tabGenre gr�ce � un param�tre
     * 
     * @param tabGenre
     *            tableau contenant les objets genres
     */

    public EnsembleGenre( ArrayList<Genre> tabGenre ) {
        this.tabGenre = tabGenre;
        rechercheStrategie = new RechercheMusiqueStrategie();
        try {
            db = DatabaseConnection.getInstance();
        } catch ( Exception e ) {
            e.getMessage();
        }

    }

    /**
     * Ajoute un genre � tabGenre
     * 
     * @param g  un genre
     */

    public void ajouter( Genre g ) {
        tabGenre.add( g );
    }

    
    /**
     * Permet de remplir tabGenre de genre qui eux m�me sont remplie de playlists/albums qui eux m�me contiendront
     * des objets Musique
     * 
     * @throws SQLException
     */

    public void remplir() throws SQLException {
    	
    	FabriqueObjetAbstraite fab = new FabriqueObjet();
    	String nomGenre = "";
        String nomPlay = "";
        String nomArtiste = "";
        Artiste artiste = null;
        Musique m = null;
        Playlist lp = null;
        Album la = null;
        Genre genre = null;
        int nbgenre = -1;//pour ins�rer l'album,la playlist et la musique au bonne endroit
        int nbplay = -1;
        int nbAlbum = -1;
        boolean estAlbum = false;// variable pour savoir si l'on est dans un album ou une playlist

        ResultSet reqGenre = null;
        try {
            reqGenre = db.getData(
                    "Select playlist.NomGenreMusical,genre_musical.image,playlist.NomPlaylist,playlist.Album, musique.NomMusique,playlist.Pseudo,musique.Duree,musique.Date,musique.URL,artiste.NomArtiste,playlist.Image,artiste.Descriptif from playlist natural join appartient natural join musique, composer, artiste,genre_musical where musique.NomMusique=composer.NomMusique AND composer.NomArtiste=artiste.NomArtiste AND genre_musical.NomGenreMusical=playlist.NomGenreMusical ORDER BY NomGenreMusical,NomPlaylist" );
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
        while ( reqGenre.next() ) {

            if ( !reqGenre.getString( "playlist.NomGenreMusical" ).equals( nomGenre ) ) {// si
                                                                                         // on,
                                                                                         // change
                                                                                         // de
                                                                                         // genre
                nomGenre = reqGenre.getString( "playlist.NomGenreMusical" );
                genre = fab.creerGenre( reqGenre.getString( "NomGenreMusical" ),
                        reqGenre.getString( "genre_musical.image" ) );
                this.tabGenre.add( genre );
                nbgenre++;
                nbplay = -1;
                nbAlbum = -1;
            }
            if ( !reqGenre.getString( "NomArtiste" ).equals( nomArtiste ) ) {// si
                                                                             // on
                                                                             // change
                                                                             // d'artiste
                nomArtiste = reqGenre.getString( "NomArtiste" );
                artiste = fab.creerArtiste( reqGenre.getString( "NomArtiste" ), reqGenre.getString( "playlist.Image" ),
                        reqGenre.getString( "Descriptif" ) );

            }
            if ( !reqGenre.getString( "NomPlaylist" ).equals( nomPlay ) ) {// si
                                                                           // on
                                                                           // change
                                                                           // de
                                                                           // playlist

                nomPlay = reqGenre.getString( "NomPlaylist" );
                if ( reqGenre.getString( "Album" ).equals( "1" ) ) {

                    la = fab.creerAlbum( reqGenre.getString( "NomPlaylist" ), reqGenre.getString( "Pseudo" ), artiste, reqGenre.getString( "playlist.Image" ) );
                    this.tabGenre.get( nbgenre ).addAlbum( la );
                    nbAlbum++;
                    estAlbum = true;
                } else {

                    lp = fab.creerPlaylist( reqGenre.getString( "NomPlaylist" ), reqGenre.getString( "Pseudo" ),
                            reqGenre.getString( "playlist.Image" ) );
                    this.tabGenre.get( nbgenre ).addPlaylist( lp );
                    nbplay++;
                    estAlbum = false;
                }

            }

            m = new Musique( reqGenre.getString( "NomMusique" ), reqGenre.getString( "Duree" ),
                    reqGenre.getString( "Date" ), reqGenre.getString( "URL" ), artiste );
            if ( estAlbum )//ont met la musique dans l'album
                this.tabGenre.get( nbgenre ).getTabAlbum().get( nbAlbum ).ajoutMusique( m );

            else//ont met la musique dans la playlist
                this.tabGenre.get( nbgenre ).getTabPlaylist().get( nbplay ).ajoutMusique( m );

        }
    }

    /**
     * Permet de r�cup�rer une liste d'objets Musique selon nomListe
     * 
     * @param nomListe
     *            correspond au nom de la playlist ou album
     * @return liste d'objets Musique
     */

    public List<Musique> getListeMusique( String nomListe ) {

        // On parcours chaque genre
        for ( int i = 0; i < this.getTabGenre().size(); i++ ) {

            // Chaque album
            for ( int j = 0; j < this.getTabGenre().get( i ).getTabAlbum().size(); j++ ) {

                // Si le nom de l'album correspond � nomListe
                if ( this.getTabGenre().get( i ).getTabAlbum().get( j ).getNomListe().equals( nomListe ) ) {
                    return this.getTabGenre().get( i ).getTabAlbum().get( j ).getListeMusique();
                }
            }

            // Chaque playlist
            for ( int j = 0; j < this.getTabGenre().get( i ).getTabPlaylist().size(); j++ ) {

                // Si le nom de la playlist correspond � nomListe
                if ( this.getTabGenre().get( i ).getTabPlaylist().get( j ).getNomListe().equals( nomListe ) ) {
                    return this.getTabGenre().get( i ).getTabPlaylist().get( j ).getListeMusique();
                }

            }

        }

        return new ArrayList<Musique>();

    }
    
    
    /**
     * Charge en sesion les musiques, playlists et albums par d�faut qui correspondent � la recheche faite sur explorer
     * @param request
     * @throws Exception
     */

    public void effectuerRecherche( HttpServletRequest request ) throws Exception {

    	//Session
    	HttpSession httpSession = request.getSession();
    	
    	//Base de donn�es
    	DatabaseConnection db = (DatabaseConnection) httpSession.getAttribute("database");
    	
    	//terme que l'utilisateur a rentr�
    	String pattern = (String) request.getParameter( "recherche" );
    	
    	//Changement du comportement
        rechercheStrategie = new RechercheMusiqueStrategie();
    	
        //Recherche Musique
        rechercheStrategie.recherche(httpSession, db, pattern);
        
        //Changement du comportement
        rechercheStrategie = new RecherchePlaylistStrategie();
        
        //Recherche Playlist
        rechercheStrategie.recherche(httpSession, db, pattern);
        
        //Changement du comportement
        rechercheStrategie = new RechercheAlbumStrategie();
        
        //Recherche Album
        rechercheStrategie.recherche(httpSession, db, pattern);
         
        db.deconnection();
    }
    

    /**
     * M�thodes permettant de tous afficher de l'attribut tabGenre
     */

    public void affiche() {

        for ( int i = 0; i < tabGenre.size(); i++ ) {

            System.out.println( tabGenre.get( i ).getNom() + "{" );

            for ( int j = 0; j < tabGenre.get( i ).getTabAlbum().size(); j++ ) {
                System.out.println( tabGenre.get( i ).getTabAlbum().get( j ).getNomListe() + " :" );
                for ( int k = 0; k < tabGenre.get( i ).getTabAlbum().get( j ).getListeMusique().size(); k++ ) {
                    System.out
                            .print( tabGenre.get( i ).getTabAlbum().get( j ).getListeMusique().get( k ).getNomMusique()
                                    + " /" );
                }
                System.out.println();
                System.out.println();
            }

            for ( int j = 0; j < tabGenre.get( i ).getTabPlaylist().size(); j++ ) {
                System.out.println( tabGenre.get( i ).getTabPlaylist().get( j ).getNomListe() + " :" );
                for ( int k = 0; k < tabGenre.get( i ).getTabPlaylist().get( j ).getListeMusique().size(); k++ ) {
                    System.out
                            .print( tabGenre.get( i ).getTabPlaylist().get( j ).getListeMusique().get( k ).getNomMusique()
                                    + " /" );
                }
                System.out.println();
                System.out.println();
            }

            System.out.println( "}" );
        }

    }

    // GETTERS et SETTERS
    // ---------------------------------------------------------------------------------------------------------

    /**
     * 
     * @return une liste d'objets genre
     */

    public ArrayList<Genre> getTabGenre() {
        return (ArrayList<Genre>) tabGenre;
    }

    /**
     * 
     * @param tabGenre
     */

    public void setTabGenre( ArrayList<Genre> tabGenre ) {
        this.tabGenre = tabGenre;
    }
    
    

    /**
     * Tests
     * 
     * @param args
     * @throws SQLException
     */

    public static void main( String[] args ) throws SQLException {

        EnsembleGenre e = new EnsembleGenre();
        e.remplir();
        e.affiche();

    }

}
