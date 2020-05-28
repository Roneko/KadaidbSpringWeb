package com.kadaidbspring.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.kadaidbspring.controller.form.PersonForm;

@Controller

public class IndexController {

    @SuppressWarnings("unchecked")
	@RequestMapping("/index")
        public String index(Model model) {
    		//↓URL格納
    		final String url ="http://localhost:8083/api/selectAll";
    		RestTemplate template = new RestTemplate();
	    	List<Map<String, Object>> personList = new ArrayList<Map<String, Object>>();
	        personList = template.getForObject(url, List.class);
	        model.addAttribute("personList",personList);
         return "index";
     }
    @SuppressWarnings("unchecked")
	@RequestMapping(value="/show",method=RequestMethod.GET)
    public String show(@RequestParam String id,Model model) {
		final String url ="http://localhost:8083/api/select/"+id;
		RestTemplate template = new RestTemplate();
    	List<Map<String, Object>> personList = new ArrayList<Map<String, Object>>();
        personList = template.getForObject(url, List.class);
        String resultId = personList.get(0).get("id").toString();
        String resultName = personList.get(0).get("Name").toString();
        String resultAge = personList.get(0).get("Age").toString();
        PersonForm personForm = new PersonForm();
        personForm.setId(Integer.parseInt(resultId));
        personForm.setName(resultName);
        personForm.setAge(Integer.parseInt(resultAge));
        model.addAttribute("personForm",personForm);
     return "show";
 }
    @RequestMapping(value="/update",method=RequestMethod.POST)
    public String update(PersonForm form,Model model) {
		final String url ="http://localhost:8083/api/update";
		RestTemplate template = new RestTemplate();
		template.put(url, form);
		return "redirect:/index";
 }
    @RequestMapping(value="/delete",method=RequestMethod.GET)
    public String delete(@RequestParam String id,Model model) {
		final String url ="http://localhost:8083/api/delete/"+id;
		RestTemplate template = new RestTemplate();
		template.delete(url);
        return "redirect:/index";
 }
    @RequestMapping(value="/insert",method=RequestMethod.POST)
    public String insert(PersonForm form,Model model) {
		final String url ="http://localhost:8083/api/insert";
		RestTemplate template = new RestTemplate();
		template.postForObject(url, form, Integer.class);
        return "redirect:/index";
 }
    @RequestMapping("/insIndex")
    public String insIndex(Model model) {
        model.addAttribute("personForm",new PersonForm());
     return "insert";
 }
}
