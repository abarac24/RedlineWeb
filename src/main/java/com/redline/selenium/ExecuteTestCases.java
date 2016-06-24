package com.redline.selenium;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverLogLevel;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.journaldev.spring.model.Configuration;
import com.journaldev.spring.model.Constants;
import com.journaldev.spring.model.TestCase;
import com.journaldev.spring.model.TestStep;

@Test
public class ExecuteTestCases {

	PrintStream oldoutps = System.out;
	private SetupPropertyReader reader = new SetupPropertyReader();
	private List<UITestSuite> testSuites = new ArrayList<UITestSuite>();

	// String Dir = getoutputPath();

	TestFile testFile;
	FileWriter fstreamGlobal = null;
	BufferedWriter outGlobal = null;
	FileWriter fstream = null;
	BufferedWriter out = null;

	protected static final int MAX_WAIT_TIME_SEC = 60;

	static WebDriver webDriver;
	static Alert alert;
	static int i = 0;
	static int counter = 0;
	public static String assign_var = "";
	public static String var_value = "";
	static HashMap<String, String> dict = new HashMap<String, String>();
	static int hashCode=0;
	static ArrayList<WebDriver> driverArray = new ArrayList<WebDriver>();
	static int instanceCounter = 0;

	@Test
	public static SuiteResultCounters runSuite(TestCase testCase,
			Configuration configuration, List<TestStep> testList,BufferedWriter out)
			throws IOException, InterruptedException {
		
		
		long startTime = System.currentTimeMillis();
		int passCount = 0;
		int failCount = 0;
		int steps = 0;
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"servlet-context.xml");

		Constants constant = (Constants) context.getBean("constantsBean");
		SuiteResultCounters suiteCouters = new SuiteResultCounters();


		if (configuration.getExplorer().equals("Firefox")) {
			instanceCounter++;
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("network.http.phishy-userpass-length", 255);
			webDriver = new FirefoxDriver(profile);
			//driverArray.add(instanceCounter,webDriver);
			hashCode=webDriver.hashCode();

			//driverArray[2].getWindowHandles();
			webDriver.get(formulateHttpRequest(configuration.getUserName(),
					configuration.getPassword(), configuration.getDeviceIp()));
			if (testCase.getProject().equals("RDL3000")) {
				WebElement element = retrieveElement(webDriver,
						"username",testCase);
				element.sendKeys(Keys.CONTROL + "a");
				element.sendKeys(Keys.DELETE);
				element.sendKeys(configuration.getUserName());
				
				element = retrieveElement(webDriver,
						"password",testCase);
				element.sendKeys(Keys.CONTROL + "a");
				element.sendKeys(Keys.DELETE);
				element.sendKeys(configuration.getPassword());
				element = retrieveElement(webDriver,
						"xpath=//input[@value='Log On']",testCase);
				element.click();
			}
		} else if (configuration.getExplorer().equals("Internet Explorer")) {
			DesiredCapabilities capabilities = DesiredCapabilities
					.internetExplorer();
			// capabilities.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR,
			// "accept");
			capabilities
					.setCapability(
							InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
							true);
			UserActions obj = new UserActions();
			try {
				if (UserActions.isProcessRunging("IEDriverServer.exe"))
					obj.ieKiller();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				// e1.printStackTrace();
			}
			System.setProperty("webdriver.ie.driver", constant.getIE_PATH());
			InternetExplorerDriverService.Builder service = new InternetExplorerDriverService.Builder();
			service = service
					.withLogLevel(InternetExplorerDriverLogLevel.DEBUG);

			webDriver = new InternetExplorerDriver(service.build());
			webDriver.get(formulateHttpRequest(configuration.getUserName(),
					configuration.getPassword(), configuration.getDeviceIp()));
		}

		else if (configuration.getExplorer().equals("Chrome")) {
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			// capabilities.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR,
			// "accept");
			UserActions obj = new UserActions();
			try {
				if (UserActions.isProcessRunging("chromedriver.exe"))
					obj.ieKiller();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				// e1.printStackTrace();
			}
			System.setProperty("webdriver.chrome.driver",
					constant.getCHROME_PATH());
			webDriver = new InternetExplorerDriver(capabilities);
		}

		out.write("<br>");
		out.newLine();

		out.write("<pre>TestCase: " + testCase.getName());
		out.newLine();
		out.write("Browser Used: " + configuration.getExplorer());
		out.newLine();
		out.write("Device: " + configuration.getDeviceIp() + " </pre>");
		out.newLine();

		TestStep step = new TestStep();
		String header = step.toString();

		out.write("<table class=\"table table-bordered\">");
		out.newLine();
		out.write("<thead>");
		out.newLine();
		out.write("<tr class=info>");
		out.newLine();

		// create header
		for (String retval : header.split(",")) {
			out.write("<th>" + retval + "</th>");
			out.newLine();
		}
		out.write("</tr>");
		out.newLine();
		out.write("<thead>");
		out.newLine();

		// add steps to table
		// String stepDetail=step.getStepDetail();
		out.write("<tbody>");
		out.newLine();

		for (TestStep testStep : testList) {
			webDriver.getTitle();
			suiteCouters.incrementTests();

			System.out.println("Test Step: " + testStep.getStepDetail());
			if (testStep.getAction().equals("NAV")) {
				suiteCouters.incrementSteps();
				steps++;
				try {
					if (testStep.getFieldType().equals("Cancel")) {
						WebElement element = retrieveElement(webDriver,
								testStep.getFieldId(),testCase);
						long end = System.currentTimeMillis() + 5000;
						while (System.currentTimeMillis() < end) {
							// Browsers which render content (such as Firefox
							// and IE) return "RenderedWebElements"
							WebElement resultsDiv = element;

							// If results have been returned, the results are
							// displayed in a drop down.
							if (resultsDiv.isDisplayed()) {
								break;
							}
						}
						((JavascriptExecutor) webDriver)
								.executeScript("window.confirm = function(msg){return false;}");
						element.click();
						suiteCouters.incrementPasses();

						passCount++;
					} else if (testStep.getFieldType().equals("Ok")) {

						WebElement element = retrieveElement(webDriver,
								testStep.getFieldId(),testCase);
						addRowToTable(testStep, out, "success", "Pass");
						((JavascriptExecutor) webDriver)
								.executeScript("window.confirm = function(msg){return true;}");
						element.click();
						// sleep for 4 seconds
						Thread.sleep(6000);

						suiteCouters.incrementPasses();
						passCount++;

					} /*
					 * else if
					 * ((testFile.getPageTitle().equals(fireFoxdriver.getTitle
					 * ()))) {
					 * 
					 * suiteCouters.incrementPasses(); passCount++; }
					 */
					else {

						WebElement element = retrieveElement(webDriver,
								testStep.getFieldId(),testCase);
						addRowToTable(testStep, out, "success", "Pass");
						element.click();
						// sleep for 4 seconds
						Thread.sleep(6000);
						suiteCouters.incrementPasses();
						passCount++;

					}
					// fireFoxdriver.manage().timeouts().implicitlyWait(10,
					// TimeUnit.SECONDS);
				} catch (final Exception e) {
					System.err.println(e.getMessage());
					addRowToTable(testStep, out, "danger", "Fail: "
							+ e.getMessage().substring(0, 99));
					suiteCouters.incrementFails();
					failCount++;
				}
				try {
					alert = webDriver.switchTo().alert();
					alert.accept();
				} // try
				catch (NoAlertPresentException Ex) {

				}

			}
			if (testStep.getAction().equals("SET")) {
				suiteCouters.incrementSteps();

				steps++;
				try {
					if (testStep.getFieldType().equals("text")) {
						WebElement element = retrieveElement(webDriver,
								testStep.getFieldId(),testCase);
						element.clear();
						if (!testStep.getFieldId().equals("pRfFreq")) {
							element.sendKeys(Keys.CONTROL + "a");
							element.sendKeys(Keys.DELETE);
							element.sendKeys(testStep.getFieldValue());
						}

						suiteCouters.incrementPasses();
						passCount++;
						addRowToTable(testStep, out, "success", "Pass");

						try {
							alert = webDriver.switchTo().alert();
							alert.accept();
						} // try
						catch (NoAlertPresentException Ex) {

						}
					}

					if (testStep.getFieldType().equals("DDL")) {

						WebElement element = retrieveElement(webDriver,testStep.getFieldId(),testCase);
						Select select = new Select(element);
						int counter = 0;

						List<WebElement> options = select.getOptions();
						if (testStep.getFieldValue().startsWith("$"))
							testStep.setFieldValue(options.get(0).toString());
						for (WebElement we : options) {
							if (we.getText().trim().equals(testStep.getFieldValue().trim())) {

								// fireFoxdriver.switchTo().defaultContent().switchTo().frame("DynamicFrame");
								WebElement options2 = element.findElement(By.xpath("//option[contains(.,'"+ we.getText().trim() + "')]"));
								if (testStep.getFieldId().contains(
										"pmpElemCon802")
										|| testStep.getFieldId().contains(
												"pmpElemGrp802")) {
									select.selectByVisibleText(we.getText());
								} else {
									select.selectByValue(we
											.getAttribute(("value")));
								}

								System.out.println(options2
										.getAttribute(("value")));
								counter++;
							}

						}
						if (counter == 0) {
							addRowToTable(
									testStep,
									out,
									"danger",
									"Fail" + "This value "
											+ testStep.getFieldValue()
											+ " can not be applied to "
											+ (testStep.getFieldId()));
							suiteCouters.incrementFails();
							failCount++;
						}
						try {
							alert = webDriver.switchTo().alert();
							alert.accept();
						} // try
						catch (NoAlertPresentException Ex) {

						}
						// takeScreenshot();
						suiteCouters.incrementPasses();
						passCount++;
						addRowToTable(testStep, out, "success", "Pass");

					}

					if (testStep.getFieldType().equals("checkbox")) {
						// takeScreenshot();
						if (testStep.getFieldValue().equals("on")) {

							WebElement element = retrieveElement(webDriver,
									testStep.getFieldId(),testCase);
							if (!(element.isSelected())) {
								element.click();

							}

						} else if (testStep.getFieldValue().equals("off")) {

							WebElement element = retrieveElement(webDriver,
									testStep.getFieldId(),testCase);
							if (element.isSelected()) {
								element.click();

							}
						}

						suiteCouters.incrementPasses();
						passCount++;
						addRowToTable(testStep, out, "success", "Pass");

						try {
							alert = webDriver.switchTo().alert();
							alert.accept();
						} 
						catch (NoAlertPresentException Ex) {

						}

					}

				} catch (final Exception e) {
					suiteCouters.incrementFails();
					failCount++;
					addRowToTable(testStep, out, "danger", "Fail: "
							+ e.getMessage().substring(0, 99));

				}

			}
			if (testStep.getAction().equals("GET")) {
				suiteCouters.incrementSteps();
				steps++;
				WebElement element = null;
				boolean isChecked = true;
				boolean labelcheck = false;
				Select select = null;
				try {

					if (testStep.getFieldValue().startsWith("$")) {
						String var = testStep
								.getFieldValue()
								.substring(1, testStep.getFieldValue().length())
								.toString();
						for (String key : dict.keySet()) {
							System.out.println(key + ":" + dict.get(key));
							if (var.equals(key.toString())) {
								testStep.setFieldValue(dict.get(key));
							}
						}// testStep.setFieldValue(element.getAttribute("value"));
					}
					element = retrieveElement(webDriver, testStep.getFieldId(),testCase);
					if (testStep.getFieldType().equals("label"))
						labelcheck = true;
					if (testStep.getFieldType().equals("DDL")) {
						select = new Select(element);
						element = select.getFirstSelectedOption();

					}
					if (testStep.getFieldType().equals("checkbox")) {
						if (!element.isSelected())
							isChecked = false;
						boolean expectedToBeChecked = testStep.getFieldValue()
								.equals("on");
						if (isChecked != expectedToBeChecked) {
							if (testStep.getResult().equalsIgnoreCase("pass")) {
								suiteCouters.incrementFails();
								failCount++;
								addRowToTable(
										testStep,
										out,
										"danger",
										"Fail"
												+ "Step:"
												+ testStep.getId()
												+ " Field ID: "
												+ (testStep.getFieldId())
												+ " - Expected Value:  "
												+ (testStep.getFieldValue())
												+ " - UI Value:  "
												+ (element
														.getAttribute("value")));

							} else {
								suiteCouters.incrementPasses();
								passCount++;
								addRowToTable(testStep, out, "success", "Pass");

							}
						} else if (isChecked == expectedToBeChecked) {

							if (testStep.getResult().equalsIgnoreCase("pass")) {
								suiteCouters.incrementPasses();
								passCount++;
								addRowToTable(testStep, out, "success", "Pass");

							} else {
								suiteCouters.incrementFails();
								failCount++;
								addRowToTable(testStep, out, "danger", "Fail");

							}
						}
					} else {
						if (!(testStep.getFieldValue().equals(element
								.getAttribute("value")))
								&& element.getAttribute("value") != null) {
							if (testStep.getResult().equalsIgnoreCase("pass")) {
								suiteCouters.incrementFails();
								failCount++;
								addRowToTable(
										testStep,
										out,
										"danger",
										"Fail"
												+ "Step: "
												+ testStep.getId()
												+ " - Field ID: "
												+ (testStep.getFieldId())
												+ " - Expected Value:  "
												+ (testStep.getFieldValue())
												+ " - UI Value:  "
												+ (element
														.getAttribute("value")));

							} else {
								suiteCouters.incrementPasses();
								passCount++;
								addRowToTable(testStep, out, "success", "Pass");

							}
						} else if (!(testStep.getFieldValue().equals(element
								.getText()))
								&& !element.getText().isEmpty()
								&& labelcheck == true) {
							if (testStep.getResult().equalsIgnoreCase("pass")) {
								suiteCouters.incrementFails();
								failCount++;
								addRowToTable(
										testStep,
										out,
										"danger",
										"Fail" + "Step: " + "Step: "
												+ testStep.getId()
												+ " - Field ID: "
												+ (testStep.getFieldId())
												+ " - Expected Value:  "
												+ (testStep.getFieldValue())
												+ " - UI Value:  "
												+ (element.getText()));

							} else {
								suiteCouters.incrementPasses();
								passCount++;
								addRowToTable(testStep, out, "success", "Pass");

							}
						}

						else if ((testStep.getFieldValue().equals(element
								.getAttribute("value")))
								|| (testStep.getFieldValue().equals(element
										.getText()))) {

							if (testStep.getResult().equalsIgnoreCase("pass")) {
								suiteCouters.incrementPasses();
								passCount++;
								addRowToTable(testStep, out, "success", "Pass");

							} else {
								suiteCouters.incrementFails();
								failCount++;
								addRowToTable(testStep, out, "danger", "Fail");

							}
						}

					}

				} catch (final Exception e) {
					addRowToTable(testStep, out, "danger", "Fail: "
							+ e.getMessage().substring(0, 99));
					suiteCouters.incrementFails();
					failCount++;

				}
			}
			if (testStep.getAction().equals("CLICK")) {

				suiteCouters.incrementSteps();
				steps++;
				try {
					WebElement element = retrieveElement(webDriver,
							testStep.getFieldId(),testCase);
					if (testStep.getFieldType().equals("Ok")) {
						((JavascriptExecutor) webDriver).executeScript("window.alert = function(msg){return true;}"

								);
						((JavascriptExecutor) webDriver).executeScript("window.confirm = function(msg){return true;}"

								);

					} else if (testStep.getFieldType().equals("Cancel")) {
						((JavascriptExecutor) webDriver)
								.executeScript("window.alert = function(msg){return false;}");
						((JavascriptExecutor) webDriver)
								.executeScript("window.confirm = function(msg){return false;}"

								);
					}
					element.click();
					// sleep for 4 seconds
					Thread.sleep(6000);

					suiteCouters.incrementPasses();
					passCount++;
					addRowToTable(testStep, out, "success", "Pass");

				} catch (final Exception e) {
					addRowToTable(testStep, out, "danger", "Fail: "
							+ e.getMessage().substring(0, 99));

				}

			}

		}
		out.write("</tbody>");
		out.newLine();
		out.write("</table>");
		out.newLine();

		out.write("<br>");
		out.newLine();
		out.write("<b>Total Steps: </b>" + steps);
		out.newLine();
		out.write("<br>");
		out.newLine();
		out.write("<b>Passed: </b>" + passCount);
		out.newLine();
		out.write("<br>");
		out.newLine();
		if (failCount > 0) {
			suiteCouters.incrementFailedTests();
			out.write("<b> Failed: </b>" + failCount);
			out.newLine();
			out.write("<br>");
			out.newLine();
			out.write("<font size=\"5\" color=\"Red\"> Test Case Failed </font>");
			out.newLine();
			out.write("<br>");
			out.newLine();
			out.write("<br>");
			out.newLine();
			out.write("<a href=\"#top\">Return to Top</a>");
			out.newLine();

		} else if (failCount == 0) {
			out.write("<b> Failed: </b>" + failCount);
			out.newLine();
			out.write("<br>");
			out.newLine();
			out.write("<font size=\"5\" color=\"Green\"> Test Case Passed </font>");
			out.newLine();
			out.write("<br>");
			out.newLine();
			out.write("<br>");
			out.newLine();
			out.write("<a href=\"#top\">Return to Top</a>");
			out.newLine();
		}
		passCount = 0;
		failCount = 0;
		steps = 0;
		out.write("<hr color=\"blue\">");
		out.newLine();
		// fireFoxdriver.close();

		Date date = new Date();

		long endTime = System.currentTimeMillis();
		Long time = endTime - startTime;
		suiteCouters.setTime(time.intValue());
		out.write("</body>");
		out.newLine();
		out.write("</html>");
		out.newLine();
		out.write("</div>");
		out.newLine();
		//out.close();

		try {
			if (webDriver.hashCode()==hashCode)
				tearDown();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// webDriver.quit();
		// fireFoxdriver.close();

		return suiteCouters;
	}

	@AfterMethod
	public static void tearDown() throws Exception {
		webDriver.quit();
		// webDriver=null;
		// webDriver.close();
	}

	private static String formulateHttpRequest(String userName,
			String password, String ip) {
		return "http://" + userName + ":" + password + "@" + ip + "/";

	}

	public static WebElement retrieveElement(WebDriver fireFoxdriver,
			String field,TestCase testCase) {
		if (!field.contains("username") && !field.contains("password") && !field.contains("Log On")&& !testCase.getProject().equals("Connect"))
			fireFoxdriver.switchTo().defaultContent().switchTo().frame("DynamicFrame");
		// fireFoxdriver.switchTo().frame(0);
		List<WebElement> frameset = fireFoxdriver.findElements(By
				.tagName("frame"));
		if (field.indexOf("xpath=") > -1) {
			String s = field.substring(field.indexOf('=') + 1);
			long end = System.currentTimeMillis() + 10000;
			while (System.currentTimeMillis() < end) {
				// Browsers which render content (such as Firefox and IE) return
				// "RenderedWebElements"
				WebElement resultsDiv = null;
				try {
					resultsDiv = fireFoxdriver.findElement(By.xpath(s));

					// If results have been returned, the results are displayed
					// in a drop down.
					if (resultsDiv.isDisplayed()) {
						break;
						
					}
				} catch (Exception e) {
					System.out.println("Error retrieving element: " + field
							+ " ----  " + e.getMessage());
					// ignore for now
				}

			}
			return fireFoxdriver.findElement(By.xpath(s));
		} else if (field.indexOf("link=") > -1) {
			fireFoxdriver.switchTo().defaultContent().switchTo().frame("LeftPanelFrame");
			String s = field.substring(field.indexOf('=') + 1);
			long end = System.currentTimeMillis() + 10000;
			while (System.currentTimeMillis() < end) {
				// Browsers which render content (such as Firefox and IE) return
				// "RenderedWebElements"
				try {
					/*WebElement resultsDiv = fireFoxdriver.findElement(By
							.className(s));*/
					WebElement resultsDiv =  fireFoxdriver.findElement(By.linkText(s));

					// If results have been returned, the results are displayed
					// in a drop down.
					if (resultsDiv.isDisplayed()) {
						break;
					}
				} catch (Exception e) {
					System.out.println("Error retrieving element: " + field
							+ " ----  " + e.getMessage());
					// ignore for now
				}
			}
			return fireFoxdriver.findElement(By.linkText(s));
		}
		long end = System.currentTimeMillis() + 10000;
		while (System.currentTimeMillis() < end) {
			// Browsers which render content (such as Firefox and IE) return
			// "RenderedWebElements"
			try {
				WebElement resultsDiv = fireFoxdriver.findElement(By.name(field));
				

				// If results have been returned, the results are displayed in a
				// drop down.
				if (resultsDiv.isDisplayed()) {
					break;
				}
			} catch (Exception e) {
				
				try {
					WebElement resultsDiv = fireFoxdriver.findElement(By.id(field));
					

					// If results have been returned, the results are displayed in a
					// drop down.
					if (resultsDiv.isDisplayed()) {
						return fireFoxdriver.findElement(By.id(field));
					}
				} catch (Exception ex) {
					
					System.out.println("Error retrieving element by id: " + field
							+ " ----  " + ex.getMessage());
					// ignore for now
				}
				
				System.out.println("Error retrieving element: " + field
						+ " ----  " + e.getMessage());
				// ignore for now
			}
		}
		return fireFoxdriver.findElement(By.name(field));
	}

	private static void addRowToTable(TestStep testStep, BufferedWriter out,
			String tagColor, String result) throws IOException {
		out.write("<tr class=" + tagColor + ">");
		out.newLine();

		// create row

		out.write("<td>" + testStep.getId() + "</td>");
		out.newLine();
		out.write("<td>" + testStep.getAction() + "</td>");
		out.newLine();
		out.write("<td>" + testStep.getFieldId() + "</td>");
		out.newLine();
		out.write("<td>" + testStep.getFieldType() + "</td>");
		out.newLine();
		out.write("<td>" + testStep.getFieldValue() + "</td>");
		out.newLine();
		out.write("<td>" + result + "</td>");
		out.newLine();

		out.write("</tr>");
		out.newLine();

	}

	private static void createTableHeader(String line, BufferedWriter out)
			throws IOException {
		out.write("<thead>");
		out.write("<tr>");
		out.newLine();
		StringTokenizer st = new StringTokenizer(line, ",");
		while (st.hasMoreTokens()) {
			out.write("<th>");
			out.newLine();
			out.write(st.nextToken());
			out.newLine();
			out.write("</th>");
			out.newLine();
		}
		out.write("</tr>");
		out.write("</thead>");
		out.newLine();
	}

	private static String getTOCScript() {
		StringBuffer script = new StringBuffer();
		script.append("<script type=\"text/javascript\">");
		script.append("function hideTable(tableID, action) {\n"
				+ "var tableObject = document.getElementById(tableID);\n"
				+ "if (action == 'visibility'){\n"
				+ "tableObject.style.visibility = 'hidden';}\n"
				+ "else if (action == 'block'){\n"
				+ "tableObject.style.display = 'none';}\n"
				+ "tableObject.style.position = 'absolute';\n" + "}\n"
				+ "function showTable(tableID, action) {\n"
				+ "var tableObject = document.getElementById(tableID);\n"
				+ "if (action == 'visibility')\n"
				+ "tableObject.style.visibility = 'visible';\n"
				+ "else if (action == 'block'){\n"
				+ "tableObject.style.display = 'block';}\n"
				+ "tableObject.style.position = 'absolute';\n" + "}");
		script.append("if (document.getElementById) onload = function () {\n"
				+ "    var h2 = document.createElement ('H2');\n"
				+ "    h2.appendChild (document.createTextNode ('Contents'));\n"
				+ "    var ul = document.createElement ('UL');\n"
				+ "  \n"
				+ "    var e, i = 0,last=1;//assumes first header found is h1\n"
				+ "\t\n"
				+ "    // Return if there is no add toc command present\n"
				+ "\tif (document.getElementById('contents') == null)\n"
				+ "\t{\n"
				+ "\t\treturn;\n"
				+ "\t}\n"
				+ "    while (e = document.getElementsByTagName ('*')[i++]) {\n"
				+ "     if (e.id != \"\") {\n"
				+ "        if (/^h[123456]$/i.test (e.tagName)) {\n"
				+ "            //alert(last+\"--\"+e.tagName.substring(1,2));\n"
				+ "            var newl = document.createElement ('UL');\n"
				+ "            var li = document.createElement ('LI');\n"
				+ "            var a = document.createElement ('A');\n"
				+ "            a.appendChild (document.createTextNode (e.firstChild.data))\n"
				+ "            a.href = '#' + e.id;\n"
				+ "            li.appendChild (a);\n"
				+ "          \n"
				+ "            if(e.tagName.substring(1,2)>1){\n"
				+ "                newl.appendChild (li);\n"
				+ "                var n=2;\n"
				+ "                \n"
				+ "                while(n<e.tagName.substring(1,2)){\n"
				+ "                    var newl2 =document.createElement ('UL');\n"
				+ "                    newl2.appendChild (newl);\n"
				+ "                    var newl =newl2;\n"
				+ "                    var n=n+1;\n" + "                }\n"
				+ "                ul.appendChild (newl);\n"
				+ "            }else{\n"
				+ "                ul.appendChild (li);\n" + "            }\n"
				+ "            var last=e.tagName.substring(1,2);\n"
				+ "        }\n" + "      }\n" + "    }\n"
				+ "    document.getElementById('contents').appendChild (h2);\n"
				+ "    document.getElementById('contents').appendChild (ul);\n"
				+ "}\n" + "\n" + "</script>");

		return script.toString();
	}

}
