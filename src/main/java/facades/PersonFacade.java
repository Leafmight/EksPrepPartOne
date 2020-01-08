/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import DTO.HobbyDTO;
import DTO.PersonDTO;
import entities.Address;
import entities.Hobby;
import entities.Person;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jacobfolkehildebrandt
 */
public class PersonFacade {
    
    private static PersonFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    public PersonFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public PersonDTO addPerson(PersonDTO pDTO) {
        EntityManager em = emf.createEntityManager();
        
        Person p = new Person(pDTO.getEmail(), pDTO.getPhone(), pDTO.getfName(), pDTO.getlName());
        
        Address a = new Address(pDTO.getAddress().getCity(),pDTO.getAddress().getStreet(),pDTO.getAddress().getZip());
        p.setAddress(a);
        
        
        for(HobbyDTO h : pDTO.getHobbies()){
            Hobby hobby = new Hobby();
            try{
            hobby = em.createQuery("select h from Hobby h where h.name = :name", Hobby.class).setParameter("name", h.getName()).getSingleResult();
            }catch(Exception e){
            hobby.setName(h.getName());
            hobby.setDescription(h.getDescription());
            }
            p.setHobby(hobby);
        }
        
        try {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return pDTO;
    }
    
    public static void main(String[] args) {
        PersonFacade pFacade = new PersonFacade();
        Address address = new Address("Englandsvej","Copenhagen","2300");
        Hobby h = new Hobby("Football","Sport");
        Person p = new Person("jacob@mail.com","00000000","Jacob","Hildebrandt");
        p.setAddress(address);
        p.setHobby(h);
        
        PersonDTO pDTO = new PersonDTO(p);
        pFacade.addPerson(pDTO);
        System.out.println(pDTO);
    }
}
