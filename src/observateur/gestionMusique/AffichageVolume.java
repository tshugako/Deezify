package observateur.gestionMusique;

import com.mysql.cj.Session;

import model.AudioMaster;
import javax.servlet.http.HttpSession;

public class AffichageVolume implements Observateur {
	
	private float volume;
	private Sujet donneesMusique;
	private HttpSession session;

	public AffichageVolume(Sujet donneesMusique, HttpSession session) {
        this.donneesMusique = donneesMusique;
        this.session = session;
    }
	
	@Override
	public void actualiser(float pitch, float volume) {
		// TODO Auto-generated method stub
		this.volume = volume;
		this.session.setAttribute( "vol", volume);
		AudioMaster audioMaster = new AudioMaster();
		audioMaster.setVolume(volume);
	}
	

}
