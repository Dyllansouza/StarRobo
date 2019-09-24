package testarServicosTravada;

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
import testarServicosTravada.BrowserRadioButton;
import testarServicosTravada.Logs;

public class Robo {
	public static void main(String[] args) throws InterruptedException, FileNotFoundException, IOException {

		String user = System.getProperty("user.name");
		InetAddress host = InetAddress.getLocalHost();

		Logger logs = Logs.Logger();
		logs.info("[testarServicosTravada] Inicializando logger...");
		logs.info("[testarServicosTravada] Acessado por host " + host);
		logs.info("[testarServicosTravada] Usuário " + user);
		
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
				logs.info("[testarServicosTravada] Executando ID " + numero);
				Execucoes.efetuaLogin();
//				Thread.sleep(8000);
				
				
				/**
				######################################################
				##				Pesquisa o ID acesso.				##				
				######################################################						
				*/
				driver.findElement(By.name("templateForm:j_idt103")).sendKeys(numero);
				driver.findElement(By.xpath("//*[@id=\"templateForm:j_idt104\"]/span[1]")).click();
//				Thread.sleep(8000);
				
				
				/**
				######################################################
				##				Clica em 'Testar Serviços'.	 		##				
				######################################################						
				*/
				driver.findElement(By.linkText("Testar Serviços")).click();
//				Thread.sleep(8000);
				/**
				######################################################
				##				Verifica se os campos estão			##
				##					preenchidos.	 				##				
				######################################################						
				*/
				String c1 = driver.findElement(By.name("j_idt274:j_idt343")).getText();
				String c2 = driver.findElement(By.name("j_idt274:j_idt346")).getText();
				String c3 = driver.findElement(By.name("j_idt274:j_idt349")).getText();
				if(!c1.isEmpty() && !c2.isEmpty() && !c3.isEmpty()) {
				
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
					##		Pesquisa o ID por Testar Serviços 	 		##	
					##				e RUNNING e finaliza.				##				
					######################################################						
					*/					
					driver.findElement(By.name("form:j_idt120")).sendKeys(numero);
					driver.findElement(By.name("form:dtTasks")).click();                			 
					driver.findElement(By.xpath("//*[@id=\"form:dtTasks\"]/option[234]")).click();  // Testar Serviços
					driver.findElement(By.name("form:dtStatus")).click();
					driver.findElement(By.xpath("//*[@id=\"form:dtStatus\"]/option[5]")).click();	// RUNNING					
					driver.findElement(By.name("form:j_idt136")).click();   						// Buscar
//					Thread.sleep(8000);
					driver.findElement(By.xpath("//*[@id=\"form:dtTarefas:j_idt138\"]/input")).click(); // CheckAll Box
					driver.findElement(By.id("form:dtTarefas:j_idt148")).click();					// Finalizar
					
					
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
					##		Pesquisa o ID por Agendar equipamento 		##	
					##				e RUNNING e finaliza.				##				
					######################################################						
					*/					
					driver.findElement(By.name("form:j_idt120")).sendKeys(numero);
					driver.findElement(By.name("form:dtTasks")).click();                			 
					driver.findElement(By.xpath("//*[@id=\"form:dtTasks\"]/option[8]")).click();  // Testar Serviços
					driver.findElement(By.name("form:dtStatus")).click();
					driver.findElement(By.xpath("//*[@id=\"form:dtStatus\"]/option[5]")).click();	// RUNNING					
					driver.findElement(By.name("form:j_idt136")).click();   						// Buscar
//					Thread.sleep(8000);
					driver.findElement(By.xpath("//*[@id=\"form:dtTarefas:j_idt138\"]/input")).click(); // CheckAll Box
					driver.findElement(By.id("form:dtTarefas:j_idt148")).click();					// Finalizar
					
					/**
					######################################################
					##					Efetua o Logout.		 		##				
					######################################################						
					*/
					driver.findElement(By.id("templateForm:j_idt27")).click();
//					Thread.sleep(8000);
					
					/**
					######################################################
					##		Registra o sucesso e fecha o webdriver.	 	##				
					######################################################						
					*/
					testarServicosTravada.Logs.sucesso.add(numero);
					driver.quit();
					logs.info("[testarServicosTravada] Sucesso na execução do ID " + numero);
					
				}else {
					/**
					######################################################
					##					Efetua o Logout.		 		##				
					######################################################						
					*/
					driver.findElement(By.linkText("Sair")).click();
//					Thread.sleep(8000);
					
					/**
					######################################################
					##		Registra o erro e fecha o webdriver.		##				
					######################################################						
					*/
					testarServicosTravada.Logs.erro.add(numero);
					driver.quit();
					logs.info("[testarServicosTravada] Botão Salvar habilitado para o ID " + numero);
					continue;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				testarServicosTravada.Logs.erro.add(numero);
				logs.info("[RetrieveDados] Erro ao executar ID " + numero);
				Driver.getWebDriver().quit();
				continue;
			}

			if (!testarServicosTravada.Logs.erro.isEmpty()) {
				testarServicosTravada.Logs.gravaErro();
			}
			if (!testarServicosTravada.Logs.sucesso.isEmpty()) {
				testarServicosTravada.Logs.gravaSucesso();
			}
		}
		Logs.close();
	}
}
