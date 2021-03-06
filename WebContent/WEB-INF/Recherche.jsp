<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import = "java.util.ArrayList" 
import="java.util.HashMap" 
import="java.util.List" 
import="java.util.Map"
import="model.Musique"
import="model.Playlist"
import="model.Album"
  %>


<html>

    <style>
        <%@ include file="../style.css"%>
    </style>

    <head>
        <title> Recherche </title>
    </head>

    <body>
        <%@ include file="../Menu.jsp"%>
        
        
         <%
  
			ArrayList<Musique> tabMusique = new ArrayList<Musique>();
			ArrayList<Playlist> tabPlaylist = new ArrayList<Playlist>();
			ArrayList<Album> tabAlbum = new ArrayList<Album>();
			HttpSession httpSession = request.getSession();
			 
			tabMusique = (ArrayList<Musique>) httpSession.getAttribute( "tabMusiqueRechercheSession" );
			tabPlaylist = (ArrayList<Playlist>) httpSession.getAttribute( "tabPlaylistRechercheSession" );
			tabAlbum = (ArrayList<Album>) httpSession.getAttribute( "tabAlbumRechercheSession" );
            
        	// Si la recherche n'a donné aucun résultat, on en informe l'utilisateur
            if (tabMusique.isEmpty() && tabPlaylist.isEmpty() && tabAlbum.isEmpty() )
    		    out.print( "<h3> Aucun résultat trouvé </h3>" );  

       		// ------------------------------------ MUSIQUES --------------------------------------------------------------------------
	        
	        if (!tabMusique.isEmpty(  ))
	  		    	out.print( "<h2> Musiques </h2>" );
        %>
        
        <div class="box">
	
  		<%   
  		
  			Musique ligneActuelleMusique = null;		
  		 	for(int k = 0; k < tabMusique.size() ; k++ ){
  		     
  		    	ligneActuelleMusique = tabMusique.get( k );
  		     
  		    
  		    	/*Affichage des informations sur la musique*/
  		    
  				out.print("<div class = \"elemMusique\">");
  			
  				out.print("<form action=\"" + request.getSession(  ).getAttribute( "nomPage" ) + "?recherche=\"" + request.getParameter("recherche") + "\" method=\"post\">");
  			
  				// Nom de la musique
  				out.print(" <form id=\"conteneurLecteur\" method=\"post\" action=\"" + request.getSession(  ).getAttribute( "nomPage" ) + "\"> "+
  					"<input type=\"submit\" value=\"" + tabMusique.get(k).getNomMusique(  ) + "\" class=\"sousTitre\" name=\"music\" /></form>");
  			
  			
  			
  				// Nom artiste
 
  				out.print("<form action=/DeezifyWeb/Artiste>Artiste : <input name=\"artiste\" class=\"boutonArtiste\" type=submit value=\""+ligneActuelleMusique.getArtiste(  ).getNom(  )+"\" /> </form>");
  			
  			
  			
  				//Date de sortie
			
				out.print("Date de sortie : " + ligneActuelleMusique.getDate(  ));
			
			
				out.print("<br/>");
  			
  				//Duree
			
				out.print("Durée : " + ligneActuelleMusique.getDuree(  ));
  			
  			
				out.print("<br/>");
				out.print("</form>");
			
				out.print("</div>");
  		   
  		 	}
  		 	
  		 	
	 	%>
	 	</div>
	 	<%
  		 	
	  		// ------------------------------------ PLAYLISTS --------------------------------------------------------------------------
  		 	if (!tabPlaylist.isEmpty(  ))
  	  		    out.print( "<h2> Playlists </h2>" ); 
    	%>
  		 	<div class = "box">
	 	<%
	  		 
	  		Playlist ligneActuellePlaylist = null;	 
	  		for(int k = 0; k < tabPlaylist.size() ; k++ ){
	 		     
  		    	ligneActuellePlaylist = tabPlaylist.get( k );
  		     
  				out.print("<div class = \"elem\">");
  			
  				out.print("<form action=\"ListeMusique\" method=\"post\">");
  				out.print("<button type=\"submit\" name=\"nomListe\" value=\"" +  ligneActuellePlaylist.getNomListe(  )  + "\" class=\"sousTitre\">");
  				out.print(ligneActuellePlaylist.getNomListe(  ) );
  				out.print("</button>");
  				out.print("</form>");
  			
  				out.print("<br/>");
  			
  				out.print("<td>");
				out.print("<form action=\"ListeMusique\" method=\"post\">");
				out.print("<button type=\"submit\" name=\"nomListe\" value=\"" +  ligneActuellePlaylist.getNomListe(  )   + "\" class=\"sousTitre\">");
				
				out.print("<img src=\"" +ligneActuellePlaylist.getImage()+ "\" class=\"lienGenre\" ");
				out.print("</button>");
				out.print("</form>");
			
				out.print("</div>");
	  		   
	  		 }
        
  		 %> 
  	
		</div>
	
		<% 
	// ------------------------------------ ALBUMS --------------------------------------------------------------------------
			if (!tabAlbum.isEmpty(  ))
  	  		    out.print( "<h2> Albums </h2>" ); %>
  		 	<div class = "box">
  		 	<%
	  		 
	  		Album ligneActuelleAlbum = null;	 
	  		for(int k = 0; k < tabAlbum.size() ; k++ ){
	 		     
  		    	ligneActuelleAlbum = tabAlbum.get( k );
  		     
  				out.print("<div class = \"elem\">");
  			
  				out.print("<form action=\"ListeMusique\" method=\"post\">");
  				out.print("<button type=\"submit\" name=\"nomListe\" value=\"" +  ligneActuelleAlbum.getNomListe(  )  + "\" class=\"sousTitre\">");
  				out.print(ligneActuelleAlbum.getNomListe(  ) );
  				out.print("</button>");
  				out.print("</form>");
  			
  				out.print("<br/>");
  			
  				out.print("<td>");
				out.print("<form action=\"ListeMusique\" method=\"post\">");
				out.print("<button type=\"submit\" name=\"nomListe\" value=\"" +  ligneActuelleAlbum.getNomListe(  )   + "\" class=\"sousTitre\">");
				
				out.print("<img src=\"" +ligneActuelleAlbum.getImage()+ "\" class=\"lienGenre\" ");
				out.print("</button>");
				out.print("</form>");
			
				out.print("</div>");
	  		   
	  		 }
        
  		 %>
  		 
  		 </div>
	
		<%@ include file="Lecteur.jsp"%> 		 
        
	</body>

</html>
