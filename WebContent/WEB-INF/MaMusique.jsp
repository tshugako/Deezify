<%--
  Created by IntelliJ IDEA.
  User: mateo
  Date: 17/10/2019
  Time: 13:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<%@ page 
import="java.util.ArrayList"
import="model.Playlist" %>

    <style>
        <%@ include file="../style.css"%>
    </style>

    <head>
        <title> Ma Musique </title>
    </head>

    <body>
     <%@ include file="../Menu.jsp"%>
      <p class="sousTitre3"> Ma Musique </p>
      
    	<form class="CreerPlaylist" method="post" action="Musique">
        	<button type="submit" value="boutonPlaylist" class="boutonMusique" name="boutonPlaylist"> Créer une nouvelle playlist </button>      
    	</form>
          
     <%     
     	
    
	// Si on appuie sur le bouton 
	if (request.getParameter( "boutonPlaylist" ) != null){
     		   		
	    // Affichage formulaire
     	out.print("<div id=\"conteneurConnexion\">");
		out.print("<form id=\"conteneurConnexion2\" method=\"post\" action=\"Musique\">");
		out.print( "<p>" );
		out.print( "<label for=\"prenom\"> Nom de la playlist : </label>" );
		out.print( "<input  type=\"text\"class=\"field\"id=\"prenom\" name=\"nomPlaylist\"/>" );
		out.print( "</p>" );
		out.print( "<button type=\"submit\" name=\"boutonCreer\" > Créer la nouvelle playlist </button>" );			
		out.print( "</form>" );			
		out.print( "</div>" );		
		
	}
     
     
    //Affichage des playlists de l'utilisateur
     
    ArrayList<Playlist> tabPlaylist = (ArrayList<Playlist>) session.getAttribute( "tabPlaylistUtilisateur" );
    Playlist ligneActuelle = null;
    
    if ( tabPlaylist != null  && !tabPlaylist.isEmpty(  ) ){
    	
 		   
 		 out.print("<div class=\"box\">");
 		 for(int k = 0; k < tabPlaylist.size() ; k++ ){
 		     
 		    ligneActuelle = tabPlaylist.get( k );
 		     
 			out.print("<div class = \"elem\">");
 			
 			out.print("<form action=\"ListeMusiqueMaMusique\" method=\"post\">");
 			out.print("<button type=\"submit\" name=\"nomListeMaMusique\" value=\"" +  ligneActuelle.getNomListe(  )  + "\" class=\"sousTitre\">");
 			out.print(ligneActuelle.getNomListe(  ) );
 			out.print("</button>");
 			out.print("</form>");
 			
 			
			out.print("<form action=\"EditerPlaylist\" method=\"post\">");		
			out.print("<button type=\"submit\" name=\"editerPlaylist\" value=\""+ ligneActuelle.getNomListe(  ) +"\" class=\"sousTitre\">éditer playlist</button>");
			out.print("</form>");
			
			out.print("<form action=\"Musique\" method=\"post\">");		
			out.print("<button type=\"submit\" name=\"supprimerPlaylist\" value=\""+ ligneActuelle.getNomListe(  ) +"\" class=\"sousTitre\">Supprimer</button>");
			out.print("</form>");
			
			out.print("</div>");
 		   
 		 }	 
 		 out.print("</div>");
 		 
    }
    
    
    
   
 		 %> 
 	
			 
        
    
    
	
     		
     		
     
     	
     
     
        
        
        
        
     <%@ include file="Lecteur.jsp"%>
    </body>

</html>
