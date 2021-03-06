package observateur.gestionMusique;

import java.util.ArrayList;
import java.util.List;

public class DonneesMusique implements Sujet {

	//Liste des observateurs qui seront notifi�s
	private List<Observateur> observateurs = new ArrayList();
	
	//pitch de la musique
    private float pitch;
    
    //Volume de la musique
    private float volume;

    //Appel�e pour mettre � jour le pitch et le volume en notifiant les observateurs
    public void setMesures(float pitch, float volume) {
        this.pitch = pitch;
        this.volume = volume;
        notifierObservateurs();
    }


	@Override
	public void supprimerObservateur(Observateur observateur) {
		// TODO Auto-generated method stub
		observateurs.remove(observateur);
	}


	@Override
	public void notifierObservateurs() {
		// TODO Auto-generated method stub
		for(Observateur observateur : observateurs)
		{
			observateur.actualiser(pitch, volume);
		}
		
	}


	@Override
	public void enregisterObservateur(Observateur observateur) {
		// TODO Auto-generated method stub
		this.observateurs.add(observateur);
	}
	
}
