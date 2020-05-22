package com.testspring.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.testspring.controller.form.BooksForm;

@Controller

public class IndexController {
	//Autowiredは初期化、簡略化してくれる
	@Autowired
	private JdbcTemplate jdbcTemplate;

    @RequestMapping("/index")
        public String index(Model model) {
	    	List<Map<String, Object>> bookList = new ArrayList<Map<String, Object>>();
	        bookList = jdbcTemplate.queryForList("SELECT * FROM books");
	        model.addAttribute("bookList",bookList);
         return "index";
     }
    @RequestMapping(value="/show",method=RequestMethod.GET)
    public String show(@RequestParam String id,Model model) {
    	List<Map<String, Object>> bookList = new ArrayList<Map<String, Object>>();
        bookList = jdbcTemplate.queryForList("SELECT * FROM books where id="+id);
        String resultId = bookList.get(0).get("id").toString();
        String resultTitle = bookList.get(0).get("title").toString();
        String resultPrice = bookList.get(0).get("price").toString();
        BooksForm booksForm = new BooksForm();
        booksForm.setId(Integer.parseInt(resultId));
        booksForm.setTitle(resultTitle);
        booksForm.setPrice(Integer.parseInt(resultPrice));
        model.addAttribute("booksForm",booksForm);
     return "show";
 }
    @RequestMapping(value="/update",method=RequestMethod.POST)
    public String update(BooksForm form,Model model) {
        jdbcTemplate.update("UPDATE books SET title='"+form.getTitle()+"', price="+form.getPrice() +" where id="+form.getId());
        return "redirect:/index";
 }
    @RequestMapping(value="/delete",method=RequestMethod.GET)
    public String delete(@RequestParam String id,Model model) {
        jdbcTemplate.update("DELETE FROM books where id ="+id);
        return "redirect:/index";
 }
    @RequestMapping(value="/insert",method=RequestMethod.POST)
    public String insert(BooksForm form,Model model) {
        jdbcTemplate.update("INSERT INTO books (title, price) VALUES ('"+form.getTitle()+"', "+form.getPrice()+")");
        return "redirect:/index";
 }
    @RequestMapping("/insIndex")
    public String insIndex(Model model) {
        model.addAttribute("booksForm",new BooksForm());
     return "insert";
 }
}
