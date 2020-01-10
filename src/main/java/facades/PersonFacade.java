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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author jacobfolkehildebrandt
 */
public class PersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    public PersonFacade() {
    }

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

        Address a = new Address(pDTO.getAddress().getCity(), pDTO.getAddress().getStreet(), pDTO.getAddress().getZip());
        p.setAddress(a);

        for (HobbyDTO h : pDTO.getHobbies()) {
            Hobby hobby = new Hobby();
            try {
                hobby = em.createQuery("select h from Hobby h where h.name = :name", Hobby.class).setParameter("name", h.getName()).getSingleResult();
            } catch (Exception e) {
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

    public PersonDTO getPersonByPhone(String phonenumber) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery(
                    "SELECT p FROM Person p JOIN p.hobbies h JOIN p.address a WHERE p.phone = :phoneNumber", Person.class);

            Person p = query.setParameter("phoneNumber", phonenumber).getSingleResult();

            return new PersonDTO(p);
        } finally {
            em.close();
        }
    }

    public PersonDTO getPersonById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery(
                    "SELECT p FROM Person p JOIN p.hobbies h JOIN p.address a WHERE p.id = :id", Person.class);

            Person p = query.setParameter("id", id).getSingleResult();

            return new PersonDTO(p);
        } finally {
            em.close();
        }
    }

    public PersonDTO getPersonByEmail(String email) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery(
                    "SELECT p FROM Person p JOIN p.hobbies h JOIN p.address a WHERE p.email = :email", Person.class);

            Person p = query.setParameter("email", email).getSingleResult();

            return new PersonDTO(p);
        } finally {
            em.close();
        }

    }

    public List<PersonDTO> getAllPersonsByHobby(String name) {
        EntityManager em = emf.createEntityManager();
        List<PersonDTO> listDTO = new ArrayList();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p JOIN p.address a JOIN p.hobbies h WHERE h.name = :hobbyName", Person.class);

            List<Person> list = query.setParameter("hobbyName", name).getResultList();

            for (Person person : list) {
                listDTO.add(new PersonDTO(person));
            }
            return listDTO;
        } finally {
            em.close();
        }
    }

   

    public PersonDTO deletePerson(int id) {
        EntityManager em = emf.createEntityManager();
        Person p = em.find(Person.class, id);
        try {
            em.getTransaction().begin();
            long personCount = (long) em.createQuery("SELECT COUNT(r) FROM Person p JOIN p.address a WHERE a.id = :ID").setParameter("ID", p.getAddress().getId()).getSingleResult();
            if (personCount == 1) {
                em.remove(em.find(Address.class, p.getAddress().getId()));
            }

            em.remove(p);
            em.close();
        } finally {
            em.close();
        }
        return new PersonDTO(p);
    }

    public PersonDTO editPerson(int id, PersonDTO pDTO) {
        EntityManager em = emf.createEntityManager();
        Person person = em.find(Person.class, id);
        person.setFirstName(pDTO.getfName());
        person.setLastName(pDTO.getfName());
        person.setPhone(pDTO.getPhone());
        person.setEmail(pDTO.getEmail());

        Address address = new Address(pDTO.getAddress().getStreet(), pDTO.getAddress().getCity(), pDTO.getAddress().getZip());
        person.setAddress(address);

        for (HobbyDTO h : pDTO.getHobbies()) {
            Hobby hobby = new Hobby(h.getName(), h.getDescription());
            person.setHobby(hobby);
        }

        try {
            em.getTransaction().begin();
            em.merge(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return pDTO;
    }

    public List<HobbyDTO> getAllHobbies() {
        EntityManager em = emf.createEntityManager();
        List<HobbyDTO> listDTO = new ArrayList();
        try {
            List<Hobby> list = em.createQuery("SELECT h FROM Hobby h", Hobby.class).getResultList();

            for (Hobby hobby : list) {
                listDTO.add(new HobbyDTO(hobby));
            }
        } finally {
            em.close();
        }
        return listDTO;
    }

    public HobbyDTO addHubby(HobbyDTO h) {
        EntityManager em = emf.createEntityManager();
        Hobby hobby = new Hobby(h.getName(), h.getDescription());
        try {
            em.getTransaction().begin();
            em.persist(hobby);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new HobbyDTO(hobby);
    }

    public HobbyDTO editHobby(HobbyDTO h) {
        EntityManager em = emf.createEntityManager();
        Hobby hobby = new Hobby(h.getName(), h.getDescription());
        try {
            em.getTransaction().begin();
            em.merge(hobby);
            em.getTransaction().commit();

        } finally {
            em.close();
        }
        return new HobbyDTO(hobby);
    }

    public HobbyDTO removeHobby(String name) {
        EntityManager em = emf.createEntityManager();
        Hobby hobby = em.find(Hobby.class, name);
        try {
            em.getTransaction().begin();
            em.remove(hobby);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new HobbyDTO(hobby);
    }

}
