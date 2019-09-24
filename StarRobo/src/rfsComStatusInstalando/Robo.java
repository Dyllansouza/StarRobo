package rfsComStatusInstalando;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import devolveAcesso.Driver;

public class Robo {
	public static void main(String[] args)
			throws InterruptedException, FileNotFoundException, IOException, ClassNotFoundException, SQLException {

		
		String user = System.getProperty("user.name");
		InetAddress host = InetAddress.getLocalHost();

		Logger logs = Logs.Logger();
		logs.info("[rfsComStatusInstalando] Inicializando logger...");
		logs.info("[rfsComStatusInstalando] Acessado por host " + host);
		logs.info("[rfsComStatusInstalando] Usuário " + user);
		
		ArrayList<String> lista = Execucoes.readFile();
		BrowserRadioButton.setBrowser();
		while(BrowserRadioButton.getBrowser() == null) {
			Thread.sleep(1000);
		}
		
		try {
			try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		    Date date = new Date();
		    
			ArrayList<String> number = new ArrayList<String>();
			Date validaEndTs = null;
			
			for(String num : lista) {
			String document_id = "";
			String selectQuery = ("select O.END_TS\r\n" + "\r\n"
					+ "from LINK L, CIRC_PATH_INST CPI, DOCUMENT D, OS O\r\n" + "\r\n"
					+ "where CPI.CIRC_PATH_INST_ID = L.CIRC_PATH_INST_ID\r\n" + "\r\n" + "and L.LINK_ID = D.LINK_ID\r\n"
					+ "\r\n" + "and D.DOCUMENT_ID = O.DOCUMENT_ID\r\n" + "\r\n" + "and CPI.CUSTOMER_ID = '" + num
					+ "' ");

			String documentIdQuery = ("select d.document_id from LINK L, CIRC_PATH_INST CPI, DOCUMENT D, OS O where CPI.CIRC_PATH_INST_ID = L.CIRC_PATH_INST_ID\r\n"
					+ "\r\n" + "and L.LINK_ID = D.LINK_ID\r\n" + "\r\n" + "and D.DOCUMENT_ID = O.DOCUMENT_ID\r\n"
					+ "\r\n" + "and CPI.CUSTOMER_ID in ('" + num + "') ");

			
			validaDAO.acessaDB();

			ResultSet rs2 = validaDAO.runQuery(documentIdQuery);
			System.out.println("Resgatando todos os document_id");
			while (rs2.next())
				number.add(rs2.getString(1));

			for (int n=0; n < number.size(); n++) {
				ResultSet rs = validaDAO.runQuery(selectQuery);
				System.out.println("Resgatando os campos END_TS de cada document_id");
				while (rs.next()) {
					validaEndTs = rs.getDate(1);
					System.out.println("Aqui foi@@@@@");
				
				if (validaEndTs == null) {
					validaDAO.runQuery("update OS set END_TS='"+dateFormat.format(date)+"' where document_id ='"+ number.get(n) + "'");
					validaDAO.commit();
					System.out.println("COLOCOU A DATA");
					break;
				}else{
					System.out.println("O ID "+n+" já tem data final");
				}
				}
			}

			// step5 close the connection object
			validaDAO.close();

		}
			}catch (Exception e) {
			System.out.println(e);
		}
			
		
	
		
		
		
		for (String numero : lista) {
//			Thread.sleep(2000);
			try {

				/**
				######################################################
				## 			Instanciate the webdriver 				##
				## 			with the browser selected.				##
				###################################################### 
				 */
				WebDriver driver = Driver.webDriver(BrowserRadioButton.getBrowser());
				driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
				/**
				######################################################
				## 		Set the main URL used for this robot 		##
				###################################################### 
				 */
				Driver.setUrlBase();
				logs.info("[rfsComStatusInstalando] Executando ID " + numero);
				Execucoes.efetuaLogin();
//				Thread.sleep(8000);
					
				/**
				######################################################
				##				Clica em 'Administração' e	 		##	
				##					depois em 'Tarefas'.	 		##				
				######################################################						
				*/
				driver.findElement(By.xpath("//*[@id=\"templateForm:j_idt31_menu\"]/li[7]/a")).click();
				driver.findElement(By.xpath("//*[@id=\"templateForm:j_idt31_menu\"]/li[7]/ul/li[5]")).click();
				
				/**
				######################################################
				##				Pesquisa o ID por RFS				##	
				##					e finaliza.						##				
				######################################################						
				*/					
				driver.findElement(By.name("form:j_idt120")).sendKeys(numero);
				driver.findElement(By.name("form:dtTasks")).click();                			 
				driver.findElement(By.xpath("//*[@id=\"form:dtTasks\"]/option[210]")).click();  // RFS			
				driver.findElement(By.name("form:j_idt136")).click();   						// Buscar
//				Thread.sleep(8000);
				driver.findElement(By.xpath("//*[@id=\"form:dtTarefas:j_idt138\"]/input")).click(); // CheckAll Box
				driver.findElement(By.id("form:dtTarefas:j_idt146")).click();					// Executar
				
				/**
				######################################################
				##		Registra o sucesso e fecha o webdriver.	 	##				
				######################################################						
				*/
				rfsComStatusInstalando.Logs.sucesso.add(numero);
				driver.quit();
				logs.info("[rfsComStatusInstalando] Sucesso na execução do ID " + numero);
				
				
			} catch (Exception e) {
				e.printStackTrace();
				rfsComStatusInstalando.Logs.erro.add(numero);
				logs.info("[rfsComStatusInstalando] Erro ao executar ID " + numero);
				Driver.getWebDriver().quit();
				continue;
			}

			if (!rfsComStatusInstalando.Logs.erro.isEmpty()) {
				rfsComStatusInstalando.Logs.gravaErro();
			}
			if (!rfsComStatusInstalando.Logs.sucesso.isEmpty()) {
				rfsComStatusInstalando.Logs.gravaSucesso();
			}
		}
		Logs.close();
	}catch (Exception e) {
		
	}
}
}
