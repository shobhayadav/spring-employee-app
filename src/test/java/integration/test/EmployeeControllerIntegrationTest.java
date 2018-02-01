package integration.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.configuration.EmployeeAppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmployeeAppConfig.class})
@WebAppConfiguration
public class EmployeeControllerIntegrationTest {

	    @Autowired
	    private WebApplicationContext webAppContext;
	    private MockMvc mockMvc;

	    @Before
	    public void setup() {
	        MockitoAnnotations.initMocks(this);
	        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
	    }

	    @Test
	    public void givenEmployeeGETisPerformed__whenMockMVC_thenRetrievedStatusAndViewNameAndAttributeAreCorrect() throws Exception {
	        mockMvc.perform(get("/list"))
	       .andExpect(status().isOk())
	       .andExpect(view().name("index"))
	       .andExpect(model().attributeExists("employees"))
	       .andExpect(model().hasNoErrors())
	       .andDo(print());
	    }

	    
	    @Test
	    public void givenEmployeeURIWithPathVariable_whenMockMVC_thenResponseOK() throws Exception {
	    	
	        this.mockMvc
	          .perform(get("/employees/{ssn}", "11345"))
	          .andDo(print()).andExpect(status().isOk())
	          .andExpect(content().contentType("application/json;charset=UTF-8"))
	          .andExpect(jsonPath("$.name").value("Lana Lang"))
	          .andExpect(jsonPath("$.id").value(3))
	          .andExpect(jsonPath("$.ssn").value("11345"))
	          .andExpect(jsonPath("$.salary").value(29000.00))
	          .andExpect(jsonPath("$.joiningDate").value("2014-01-11"));
	       
	    }
	    
	    @Test
	    public void givenEmployeeURIWithParamVariable_whenMockMVC_thenResponseOK() throws Exception {
	    	
	        this.mockMvc
	          .perform(get("/employees/params?ssn=11345"))
	          .andDo(print()).andExpect(status().isOk())
	           
	          .andExpect(content().contentType("application/json;charset=UTF-8"))
	          .andExpect(jsonPath("$.name").value("Lana Lang"))
	          .andExpect(jsonPath("$.id").value(3))
	          .andExpect(jsonPath("$.ssn").value("11345"))
	          .andExpect(jsonPath("$.salary").value(29000.00))
	          .andExpect(jsonPath("$.joiningDate").value("2014-01-11"));
	       
	    }
	    
	    @Test
	    public void givenEmployeeURIWithPost_whenMockMVC_thenVerifyResponseEmployeeCreated() /*  Positive scenario */throws Exception {
	    	String postJson ="{\"name\":\"TestPost\",\"joiningDate\":\"2014-01-11\",\"salary\":29000.00,\"ssn\":\"11346\"}";
	        this.mockMvc.perform(MockMvcRequestBuilders.post("/employees").content(postJson).contentType(MediaType.APPLICATION_JSON))
	        .andDo(print())
	        .andExpect(MockMvcResultMatchers.status().isOk())
	        .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));
	    }
	    
	    @Test
	    public void givenEmolyeeURIWithPostWithInvalidReq_whenMockMVC_thenVerifyBadRequestIsTheResponse() /*  Negative scenario */throws Exception {
	    	String postJson ="{\"name\":\"T\",\"joiningDate\":\"2014-01-11\",\"salary\":29000.00,\"ssn\":\"11346\"}";
	        this.mockMvc.perform(MockMvcRequestBuilders.post("/employees").content(postJson).contentType(MediaType.APPLICATION_JSON))
	        .andDo(print())
	        .andExpect(MockMvcResultMatchers.status().isBadRequest())
	        .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));
	      
	    }
}
