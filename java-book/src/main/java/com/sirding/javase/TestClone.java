package com.sirding.javase;


import org.apache.log4j.Logger;

public class TestClone implements Cloneable{

	private static final Logger logger = Logger.getLogger(TestClone.class);
	private String name;
	private Model model;
	public static void main(String[] args) throws CloneNotSupportedException {
		
		TestClone d = new TestClone();
		Model model = new Model();
		model.setName("aa");
		model.setPwd("aa");
		d.setModel(model);
		d.setName("1");
		TestClone d2 = (TestClone)d.clone();
		d2.getModel().setName("bb");
		logger.info(d.getModel().getName());
		logger.info(d2.getModel().getName());
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}
	
	@Override
	public Object clone() {  
		TestClone o = null;  
        try {
            o = (TestClone) super.clone();  
        } catch (CloneNotSupportedException e) {  
        	logger.info(e);
            logger.info(e.getMessage());
        }  
        return o;  
    } 
}
