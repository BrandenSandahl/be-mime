package com.mimetroupe;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mimetroupe.entities.Admimerer;
import com.mimetroupe.entities.Mime;
import com.mimetroupe.services.AdmimererRepository;
import com.mimetroupe.services.MimeRepository;
import com.mimetroupe.utilities.PasswordStorage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WillYouBeMimeApplication.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING) //have to keep tests in alpha order
public class WillYouBeMimeApplicationTests {

	@Autowired
	MimeRepository mimeRepository;
    @Autowired
    AdmimererRepository admimererRepository;

	@Autowired
	WebApplicationContext wap;

	MockMvc mockMvc;

	@Before
	public void before() {
        //mimeRepository.deleteAll();
		mockMvc = MockMvcBuilders.webAppContextSetup(wap).build();
	}


    //test inserting a new Mime
    // /mime Post route method: addMime
	@Test
	public void testA() throws Exception {
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

    //testing SQL query to return all mimes but logged in mime.
    // /mime GET route method: displayAllMimesExceptUser
	@Test
	public void testB() throws Exception {
		mimeRepository.save(new Mime("mimeman2", PasswordStorage.createHash("pass2"), "Mimer Mime", 30, "url2", "vid2", "miming", "mimelandia", "Mimeland", "chapland"));
        mimeRepository.save(new Mime("mimeman3", PasswordStorage.createHash("pass3"), "Mime Mimerson", 34, "url3", "vid3", "miming", "mimelandia", "Mimeland", "chapland"));
        mimeRepository.save(new Mime("mimeman4", PasswordStorage.createHash("pass4"), "Justa Mime", 50, "url4", "vid4", "miming", "mimelandia", "Mimeland", "chapland"));

		//should return just one mime out of the 2
		Assert.assertTrue(mimeRepository.findAllWhereUserNameNot("mimeman2").size() == 3);
	}

    //testing adding an admimerer IE "liking"
    //  /admimerer Post route  method: addAdmimerer
    @Test
    public void testC() throws  Exception {
        //mime 1
        Mime userMime = mimeRepository.findOne(1);
        //mime 2
        Mime admimeredMime = mimeRepository.findOne(2);

        admimererRepository.save(new Admimerer(userMime, admimeredMime));

        Assert.assertTrue(admimererRepository.count() == 1);
    }

    //testing returning all admimerers for a specific mime
    // /admimerer GET route method: viewAdmimerer
    @Test
    public void testD() throws  Exception {
        //add another admimerer situation.
        //1 likes 2
        //3 likes 4
        Admimerer admimererSituation = new Admimerer(mimeRepository.findOne(1), mimeRepository.findOne(4));

        admimererRepository.save(admimererSituation);

        Mime mime = mimeRepository.findOne(1);

        List<Mime> admimerer = admimererRepository.findAdmimererByMime(mime);

        Assert.assertTrue(admimerer.size() == 2);
    }

    //testing returning all mimes that admimerer a specific mime
    @Test
    public void testE() throws Exception {


    }

}
