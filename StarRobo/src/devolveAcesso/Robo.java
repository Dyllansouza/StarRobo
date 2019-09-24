package devolveAcesso;

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

import devolveAcesso.BrowserRadioButton;
import devolveAcesso.Logs;

public class Robo {
	public static void main(String[] args) throws InterruptedException, FileNotFoundException, IOException {

		String user = System.getProperty("user.name");
		InetAddress host = InetAddress.getLocalHost();
		Logger logs = Logs.Logger();
		logs.info("[devolveAcesso] Inicializando logger...");
		logs.info("[devolveAcesso] Acessado por host " + host);
		logs.info("[devolveAcesso] Usuário " + user);


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
				logs.info("[devolveAcesso] Executando ID " + numero);
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
				driver.findElement(By.xpath("//*[@id=\"form:dtTasks\"]/option[67]")).click();  // Cancelar Agendamento
				driver.findElement(By.name("form:dtStatus")).click();
				driver.findElement(By.xpath("//*[@id=\"form:dtStatus\"]/option[8]")).click();	// WAITING_RESP					
				driver.findElement(By.name("form:j_idt136")).click();   						// Buscar
//				Thread.sleep(5000);
				driver.findElement(By.id("form:dtTarefas:j_idt138")).isDisplayed(); 	 // CheckAll Box
				System.out.println("ESTA DISPONIVEL");

				
				/**
				######################################################
				##				Clica em Aprovisionamento			##	
				##				e Agendar Equipamentos				##				
				######################################################						
				*/			
				driver.findElement(By.xpath("//*[@id=\"templateForm:j_idt31_menu\"]/li[2]/a")).click();
				driver.findElement(By.xpath("//*[@id=\"templateForm:j_idt31_menu\"]/li[2]/ul/li[6]")).click();
				
				/**
				######################################################
				##				Preenche o campo ID Acesso			##	
				##					e clica em Buscar.				##				
				######################################################						
				*/			
				driver.findElement(By.name("fGeneral:j_idt121")).sendKeys(numero);
				driver.findElement(By.id("fGeneral:j_idt143")).click();			//Buscar
				
				
				/**
				######################################################
				##		Clicar em Cancelar Agendamento (Cadeado) 	##				
				######################################################						
				*/
				driver.findElement(By.xpath("//*[@id=\"fGeneral:agen:0:j_idt264\"]")).click();
				
				
				/**
				######################################################
				##		Registra o sucesso e fecha o webdriver.	 	##				
				######################################################						
				*/
				devolveAcesso.Logs.sucesso.add(numero);
				driver.quit();
				logs.info("[devolveAcesso] Sucesso na execução do ID " + numero);
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
				devolveAcesso.Logs.erro.add(numero);
				logs.info("[devolveAcesso] Erro ao executar ID " + numero);
				Driver.getWebDriver().quit();
				continue;
			}
			
		}
		
		
		if (!devolveAcesso.Logs.erro.isEmpty()) {
			devolveAcesso.Logs.gravaErro();
		}
		if (!devolveAcesso.Logs.sucesso.isEmpty()) {
			devolveAcesso.Logs.gravaSucesso();
		}
		Logs.close();
	}
}
