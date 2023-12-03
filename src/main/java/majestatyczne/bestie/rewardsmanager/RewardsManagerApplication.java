package majestatyczne.bestie.rewardsmanager;

import javafx.application.Application;
import majestatyczne.bestie.rewardsmanager.view.UIApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RewardsManagerApplication{

	public static void main(String[] args) {
		Application.launch(UIApplication.class, args);
	}

}
