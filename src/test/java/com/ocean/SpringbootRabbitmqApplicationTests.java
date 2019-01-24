package com.ocean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootRabbitmqApplicationTests {

	@Autowired
	private Messageproducer messageproducer;

	@Test
	public void contextLoads() {
	}

	@Test
	public void sendMsg(){
		messageproducer.sendMessage(new Employee("1101","ocean",26));
	}



}

