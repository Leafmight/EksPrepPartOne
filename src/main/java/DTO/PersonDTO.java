/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import entities.Hobby;
import entities.Person;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jacobfolkehildebrandt
 */
public class PersonDTO {
    private Long id;
    private String fName;
    private String lName;
    private String email;
    private AddressDTO address;
    private String phone;
    private List<HobbyDTO> hobbies = new ArrayList<>();

    public PersonDTO() {
    }

    public PersonDTO(Person person) {
        if (person.getId() != null) {
            this.id = person.getId();
        }
        this.fName = person.getFirstName();
        this.lName = person.getLastName();
        this.email = person.getEmail();
        
        if(person.getAddress() != null){
        this.address = new AddressDTO(person.getAddress());
        }
        
        if(person.getHobbies() != null){
        for (Hobby hobby : person.getHobbies()) {
        this.hobbies.add(new HobbyDTO(hobby));
        }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<HobbyDTO> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<HobbyDTO> hobbies) {
        this.hobbies = hobbies;
    }
    
    
    
}
