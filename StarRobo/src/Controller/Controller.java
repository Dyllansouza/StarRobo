package Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class Controller {
	public static void main(String[] args) throws FileNotFoundException, InterruptedException, IOException, ClassNotFoundException, SQLException {

		RadioButton.setRobo();

		while ((RadioButton.getExecucao() == null)) {
			Thread.sleep(1000);
		}

		switch (RadioButton.getExecucao()) {
		case "testarServicosTravada":
			testarServicosTravada.Robo.main(args);
			break;
		case "validarPortaAcesso":
			validarPortaAcesso.Robo.main(args);
			break;
		case "rfsComStatusInstalando":
			rfsComStatusInstalando.Robo.main(args);
			break;
		case "validarAcessoWaiting":
			validarAcessoWaiting.Robo.main(args);
			break;
		case "devolveAcesso":
			validarAcessoWaiting.Robo.main(args);
			break;
		case "aguardarCanceOp":
			validarAcessoWaiting.Robo.main(args);
			break;
			
		default:
			break;
			
		}
	}
}
