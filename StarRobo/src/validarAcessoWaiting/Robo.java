package validarAcessoWaiting;

import java.awt.print.Printable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import validarAcessoWaiting.BrowserRadioButton;
import validarAcessoWaiting.Logs;

public class Robo {
	public static void main(String[] args) throws InterruptedException, FileNotFoundException, IOException {

		String user = System.getProperty("user.name");
		InetAddress host = InetAddress.getLocalHost();
		Logger logs = Logs.Logger();
		logs.info("[validarAcessoWaiting] Inicializando logger...");
		logs.info("[validarAcessoWaiting] Acessado por host " + host);
		logs.info("[validarAcessoWaiting] Usuário " + user);

		
		ArrayList<String> lista = Execucoes.readFile();
		BrowserRadioButton.setBrowser();
		while(BrowserRadioButton.getBrowser() == null) {
			Thread.sleep(1000);
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
				logs.info("[validarAcessoWaiting] Executando ID " + numero);
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
				##		Pesquisa o ID por Valida Porta Acesso 		##	
				##					e finaliza.						##				
				######################################################						
				*/					
				driver.findElement(By.name("form:j_idt120")).sendKeys(numero);
				driver.findElement(By.name("form:dtTasks")).click();                			 
				driver.findElement(By.xpath("//*[@id=\"form:dtTasks\"]/option[237]")).click();  // Validar Acesso
				driver.findElement(By.name("form:dtStatus")).click();
				driver.findElement(By.xpath("//*[@id=\"form:dtStatus\"]/option[7]")).click();	// WAITING				
				driver.findElement(By.name("form:j_idt136")).click();   						// Buscar
//				Thread.sleep(5000);
				driver.findElement(By.id("form:dtTarefas:j_idt138")).click(); 	 // CheckAll Box
				driver.findElement(By.id("form:dtTarefas:j_idt146")).click();	// Executar
				
				
				/**
				######################################################
				##		Registra o sucesso e fecha o webdriver.	 	##				
				######################################################						
				*/
				validarAcessoWaiting.Logs.sucesso.add(numero);
				driver.quit();
				logs.info("[validarAcessoWaiting] Sucesso na execução do ID " + numero);				
				
				
			} catch (Exception e) {
				e.printStackTrace();
				validarAcessoWaiting.Logs.erro.add(numero);
				logs.info("[validarAcessoWaiting] Erro ao executar ID " + numero);
				Driver.getWebDriver().quit();
				continue;
			}
			
		}
		
		
		if (!validarAcessoWaiting.Logs.erro.isEmpty()) {
			validarAcessoWaiting.Logs.gravaErro();
		}
		if (!validarAcessoWaiting.Logs.sucesso.isEmpty()) {
			validarAcessoWaiting.Logs.gravaSucesso();
		}
		Logs.close();
	}
}
