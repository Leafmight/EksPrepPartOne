/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import DTO.PersonDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Address;
import entities.Hobby;
import entities.Person;
import facades.PersonFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("setup")
    public String setUp() {
        Address address = new Address("Englandsvej","Copenhagen","2300");
        Hobby h = new Hobby("Football","Sport");
        Person p = new Person("jacob@mail.com","00000000","Jacob","Hildebrandt");
        p.setAddress(address);
        p.setHobby(h);
        
        PersonDTO pDTO = new PersonDTO(p);
        FACADE.addPerson(pDTO);
    return "Setup Complete!";
    }
}
