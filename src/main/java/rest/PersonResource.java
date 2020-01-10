/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import DTO.HobbyDTO;
import DTO.PersonDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Address;
import entities.Hobby;
import entities.Person;
import facades.PersonFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import utils.EMF_Creator;

/**
 *
 * @author jacobfolkehildebrandt
 */

@Path("person")
public class PersonResource {
    private static PersonFacade pFacade = new PersonFacade();
    
     private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/ExamPrep",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);
    private static final PersonFacade FACADE = PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    
     @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("setup")
    public String setUp() {
        Address address = new Address("Englandsvej","Copenhagen","2300");
        Hobby h = new Hobby("Football","Sport");
        Person p = new Person("jacob@mail.com","89898989","Jacob","Hildebrandt");
        p.setAddress(address);
        p.setHobby(h);
        
        PersonDTO pDTO = new PersonDTO(p);
        FACADE.addPerson(pDTO);
    return "Setup Complete!";
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("addPerson")
    public PersonDTO addPerson(PersonDTO pDTO) {
      
    return FACADE.addPerson(pDTO);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"user", "admin"})
    @Path("personByPhone/{phone}")
    public PersonDTO getPersonByPhone(@PathParam("phone") String phonenumber) {
      
    return FACADE.getPersonByPhone(phonenumber);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("personById/{id}")
    public PersonDTO getPersonById(@PathParam("id") int id) {
      
    return FACADE.getPersonById(id);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("personByEmail/{email}")
    public PersonDTO getPersonById(@PathParam("email")String email) {
      
    return FACADE.getPersonByEmail(email);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("personByHobby/{hobby}")
    public List<PersonDTO> getPersonsByHobby(@PathParam("hobby") String hobbyname) {
      
    return FACADE.getAllPersonsByHobby(hobbyname);
    }
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("deletePerson/{id}")
    public PersonDTO deletePerson(@PathParam("id") int id) {
      
    return FACADE.deletePerson(id);
    }
    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("editPerson/{id}")
    public PersonDTO editPerson(@PathParam("id") int id, PersonDTO pDTO) {
      
    return FACADE.editPerson(id, pDTO);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("allHobbies")
    public List<HobbyDTO> getAllHobbies() {
      
    return FACADE.getAllHobbies();
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("addHobby")
    public HobbyDTO addHobby(HobbyDTO hDTO) {
      
    return FACADE.addHubby(hDTO);
    }
    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("editHobby")
    public HobbyDTO editHobby(HobbyDTO hDTO) {
      
    return FACADE.editHobby(hDTO);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("removeHobby/{name}")
    public HobbyDTO removeHobby(@PathParam("name") String name) {
      
    return FACADE.removeHobby(name);
    }
    
    
}
