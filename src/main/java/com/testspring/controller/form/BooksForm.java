package com.testspring.controller.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BooksForm {
	private int id;
	private String title;
	private int price;
	private String created_at;

}
