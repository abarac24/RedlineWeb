package com.journaldev.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.journaldev.spring.model.TestSuite;
import com.journaldev.spring.service.TestSuiteServiceInterface;

@Controller
public class AddTestSuite {
	
	private TestSuiteServiceInterface testSuiteService;
	
	@Autowired(required=true)
	@Qualifier(value="testSuiteService")
	public void setTestService(TestSuiteServiceInterface testService) {
		this.testSuiteService = testService;
	}

	@RequestMapping(value = "/testsuite", method = RequestMethod.GET)
	public String listTestSuites(Model model) {
		model.addAttribute("testsuite", new TestSuite());
		model.addAttribute("listTestSuites", this.testSuiteService.listTestSuites());
		return "testsuite";
		
	}


	@RequestMapping(value= "/testsuite/add", method = RequestMethod.POST)
	public String addTestSuite(@ModelAttribute("testsuite") TestSuite testsuite){
		
		if(testsuite.getId() == 0){
			//new TestSuite, add it
			this.testSuiteService.addTestSuite(testsuite);
		}
		
		else{
			//existing TestSuite, call update
			this.testSuiteService.updateTestSuite(testsuite);
		}
		
		return "redirect:/testsuite";
		
	}
	


	@RequestMapping("/removesuite/{id}")
    public String removeTestSuite(@PathVariable("id") int id){
		
        this.testSuiteService.removeTestSuite(id);
        return "redirect:/testsuite";
    }
 
    @RequestMapping("/editsuite/{id}")
    public String editTestSuite(@PathVariable("id") int id, Model model){
        model.addAttribute("testsuite", this.testSuiteService.getTestSuiteById(id));
        model.addAttribute("listTestSuites", this.testSuiteService.listTestSuites());
        
        return "testsuite";
    }

	
	
}
