package com.redline.selenium;


import java.io.FileInputStream;
import java.util.Properties;

public class UITestsFilePath {
	private Properties filepath = new Properties();
	public String Path;
	private static final String defltUItestsFile = "C:\\IxResources\\InternalResources\\UserInterface\\RDL_3_0\\properties\\UITestsFilePath.properties";
	public UITestsFilePath()
	  {
	    try
	    {
	      load();
	    }
	    catch(final Exception e)
	    {
	      e.printStackTrace();
	    }
	  }

	  private void load()
	    throws Exception
	  {
	    FileInputStream fiStream;
	    fiStream = new FileInputStream(defltUItestsFile);
	    filepath.load(fiStream);
	    Path = filepath.getProperty("Path");
	   
	  }

	  public String getPath()
	  {
	    return Path;
	  }


}
