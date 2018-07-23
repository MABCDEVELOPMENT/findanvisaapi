package com.anvisa.rest;

import java.util.ArrayList;

public class RootObject {
	private ArrayList<Object> content = new ArrayList<Object>();

	public ArrayList<Object> getContent() {
		return this.content;
	}

	public void setContent(ArrayList<Object> content) {
		this.content = content;
	}

	private int totalElements;

	public int getTotalElements() {
		return this.totalElements;
	}

	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
	}

	private int totalPages;

	public int getTotalPages() {
		return this.totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	private boolean last;

	public boolean getLast() {
		return this.last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	private int numberOfElements;

	public int getNumberOfElements() {
		return this.numberOfElements;
	}

	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	private String sort;

	public String getSort() {
		return this.sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	private boolean first;

	public boolean getFirst() {
		return this.first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	private int size;

	public int getSize() {
		return this.size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	private int number;

	public int getNumber() {
		return this.number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
}