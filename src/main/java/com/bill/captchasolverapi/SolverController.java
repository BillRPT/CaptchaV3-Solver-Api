package com.bill.captchasolverapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.lang3.StringUtils;

@RestController
public class SolverController {
	
	private URL uneUrl;
	private HttpURLConnection uneConnexion;
	private BufferedReader in;
	private StringBuffer content;
	
	//Faire une requête GET et faire passer en paramètre le anchor du captcha à résoudre
	// L'url -> http://localhost:8080/SolveV3?anchor=
	@GetMapping("/SolveV3")
	public String captchav3(@RequestParam String anchor) {
		String rep = "";
		
		if (anchor.contains(("recaptcha/api2/anchor?"))) {
			//Appeler cette fonction qui va résoudre le captcha et retourner la réponse
			rep = this.solvecaptchaV3(anchor);
		}
		else {
			rep = "Bad captcha or not a V3";
		}
			
		return rep;
	}
	
	//Fonction qui va résoudre le captcha et retourner la réponse
	private String solvecaptchaV3(String anchor) {
		//Réponse qui va être return
		String rep = "";
		
		
		try {
			//Instancier une url
			this.uneUrl = new URL(anchor);
			//Lancer la requete
			this.uneConnexion = (HttpURLConnection) uneUrl.openConnection();
			this.uneConnexion.setRequestMethod("GET");
			
			//Lire le flux pour après récupérer la réponse et parse le token du captcha
			this.in = new BufferedReader(new InputStreamReader(uneConnexion.getInputStream()));
			String inputLine;
			this.content = new StringBuffer();
			//Afficher toute la réponse
			while ((inputLine = in.readLine()) != null) {
				this.content.append(inputLine);
			}
			this.in.close();
			
			//Fermer la connexion
			this.uneConnexion.disconnect();
			
			
			//Parse le token de l'url pour que ca donne quelque chose comme ca https://www.google.com/recaptcha/api2/reload?k=6Lf5DqUUAAAAAIHKVINTlK4DGAisCEIXM75KeUqT
			String debutUrl = "https://www.google.com/recaptcha/api2/reload?k=" + StringUtils.substringBetween(anchor, "?ar=1&k=", "&co=");
			
			//Instancier une nouvelle url pour faire une autre req
			this.uneUrl = new URL(debutUrl);
			this.uneConnexion = (HttpURLConnection) uneUrl.openConnection();
			this.uneConnexion.setRequestMethod("POST");
			//Pour envoyer des données
			uneConnexion.setDoOutput(true);
			
			//Le Post data
			String postData = "reason=q&c=" + this.parseToken();
			
			// Écriture des données dans le corps de la requête
	        try (OutputStream os = uneConnexion.getOutputStream()) {
	            byte[] input = postData.getBytes("utf-8");
	            os.write(input, 0, input.length);
	        }
			
	        this.in = new BufferedReader(new InputStreamReader(uneConnexion.getInputStream()));
			this.content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				this.content.append(inputLine);
			}
			
			this.in.close();
			
			//Fermer la connexion
			this.uneConnexion.disconnect();
	        
	        System.out.println(this.content.toString());
	        
	        if (this.content.toString().contains("[\"rresp\",null")) {
	        	rep = "ERROR CAPTCHA";
	        }
	        else {
	        	rep = StringUtils.substringBetween(this.content.toString(), "[\"rresp\",\"", "\"");
	        }
		}
		catch(MalformedURLException erreur) {
			System.out.println("Erreur url " + erreur);
		} catch (IOException erreur) {
			System.out.println("Erreur connexion " + erreur);
		}
		
		return rep;
			
	}
	
	//Fonction qui va parse le token et le return
	private String parseToken() {
		//Parse le token et le return
        String result = StringUtils.substringBetween(this.content.toString(), "id=\"recaptcha-token\" value=\"", "\"");
        return result;
	}
}
