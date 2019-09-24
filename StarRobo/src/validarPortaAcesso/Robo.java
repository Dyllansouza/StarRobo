package validarPortaAcesso;

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

import devolveAcesso.Driver;
import validarPortaAcesso.BrowserRadioButton;
import validarPortaAcesso.Logs;

public class Robo {
	public static void main(String[] args) throws InterruptedException, FileNotFoundException, IOException {

		String user = System.getProperty("user.name");
		InetAddress host = InetAddress.getLocalHost();

		Logger logs = Logs.Logger();
		logs.info("[validarPortaAcesso] Inicializando logger...");
		logs.info("[validarPortaAcesso] Acessado por host " + host);
		logs.info("[validarPortaAcesso] Usuário " + user);
		
		ArrayList<String> lista = Execucoes.readFile();
		BrowserRadioButton.setBrowser();
		while(BrowserRadioButton.getBrowser() == null) {
			Thread.sleep(1000);
		}
		
		for (String numero : lista) {
			Thread.sleep(2000);
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
				logs.info("[validarPortaAcesso] Executando ID " + numero);
				
				driver.findElement(By.name("form1:j_idt10")).sendKeys("DYLLAN");
				driver.findElement(By.name("form1:j_idt12")).sendKeys("STAR01!");
				driver.findElement(By.xpath("//*[@id=\"form1:j_idt14\"]/span")).click();
				
//				Execucoes.efetuaLogin();
				Thread.sleep(8000);
				
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
				driver.findElement(By.xpath("//*[@id=\"form:dtTasks\"]/option[242]")).click();  // validarPortaAcesso
				driver.findElement(By.name("form:dtStatus")).click();
				driver.findElement(By.xpath("//*[@id=\"form:dtStatus\"]/option[7]")).click();	// WAITING					
				driver.findElement(By.name("form:j_idt136")).click();   						// Buscar
//				Thread.sleep(5000);
				driver.findElement(By.id("form:dtTarefas:j_idt138")).click(); // CheckAll Box
				driver.findElement(By.id("form:dtTarefas:j_idt147")).click();					// Executa/Finalizar
				
				
				/**
				######################################################
				##		Registra o sucesso e fecha o webdriver.	 	##				
				######################################################						
				*/
				validarPortaAcesso.Logs.sucesso.add(numero);
				driver.quit();
				logs.info("[validarPortaAcesso] Sucesso na execução do ID " + numero);
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
				validarPortaAcesso.Logs.erro.add(numero);
				logs.info("[validarPortaAcesso] Erro ao executar ID " + numero);
				Driver.getWebDriver().quit();
				continue;
			}
			
		}
		
		
		if (!validarPortaAcesso.Logs.erro.isEmpty()) {
			validarPortaAcesso.Logs.gravaErro();
		}
		if (!validarPortaAcesso.Logs.sucesso.isEmpty()) {
			validarPortaAcesso.Logs.gravaSucesso();
		}
		Logs.close();
	}
}
