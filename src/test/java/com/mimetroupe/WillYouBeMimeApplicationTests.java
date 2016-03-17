package com.mimetroupe;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mimetroupe.entities.Mime;
import com.mimetroupe.services.MimeRepository;
import com.mimetroupe.utilities.PasswordStorage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WillYouBeMimeApplication.class)
@WebAppConfiguration
public class WillYouBeMimeApplicationTests {

	@Autowired
	MimeRepository mimeRepository;

	@Autowired
	WebApplicationContext wap;

	MockMvc mockMvc;

	@Before
	public void before() {
        mimeRepository.deleteAll();
		mockMvc = MockMvcBuilders.webAppContextSetup(wap).build();
	}

	@Test
	public void addUser() throws Exception {
		Mime mime = new Mime("mimeman", PasswordStorage.createHash("pass"), "mime mimer", 27, "url", "vid", "mimes", "mimeville", "mimekingdom", "mimes");

		//this is for creating JSON strings
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(mime);

		mockMvc.perform(
				MockMvcRequestBuilders.post("/mime")
						.content(json)
						.contentType("application/json")
		);


		Assert.assertTrue(mimeRepository.count() == 1);
	}

	@Test
	public void testDisplayAllMimesExceptUser() throws Exception {
		mimeRepository.save(new Mime("mimeman2", PasswordStorage.createHash("pass2"), "Mimer Mime", 30, "url2", "vid2", "miming", "mimelandia", "Mimeland", "chapland"));
        mimeRepository.save(new Mime("mimeman3", PasswordStorage.createHash("pass3"), "Mime Mimerson", 34, "url3", "vid3", "miming", "mimelandia", "Mimeland", "chapland"));
        mimeRepository.save(new Mime("mimeman4", PasswordStorage.createHash("pass4"), "Justa Mime", 50, "url4", "vid4", "miming", "mimelandia", "Mimeland", "chapland"));


		//should return just one mime out of the 2
		Assert.assertTrue(mimeRepository.findAllWhereUserNameNot("mimeman2").size() == 2);


	}

}
