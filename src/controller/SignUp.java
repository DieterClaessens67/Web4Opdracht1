package controller;

import domain.Person;
import domain.PersonService;
import domain.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SignUp extends RequestHandler {
    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> errors = new ArrayList<>();
        String naam = request.getParameter("naam");
        String voorNaam = request.getParameter("voorNaam");
        String email = request.getParameter("emailAdres");
        String geslacht = request.getParameter("geslacht");
        String paswoord = request.getParameter("paswoord");
        String hpaswoord = request.getParameter("herhaalPaswoord");
        int leeftijd = Integer.parseInt(request.getParameter("leeftijd"));
        Person person = new Person();
        try {
            person.setFirstName(voorNaam);
            person.setLastName(naam);
            person.setUserId(email);
            person.setGenderWithString(geslacht);
            if(paswoord.equalsIgnoreCase(hpaswoord)){
                throw new IllegalArgumentException("Passwords do not match.");
            }
            person.setHashedPassword(paswoord);
            person.setAge(leeftijd);
            person.setFriends(new HashMap<>());
            person.setRole(Role.valueOf("LID"));
        }catch (IllegalArgumentException e){
            errors.add(e.getMessage());
        }
        if(errors.isEmpty()){
            PersonService personService = super.getPersonService();
            personService.addPerson(person);
            errors.add("Please login");
            request.setAttribute("errors",errors);
            return "index.jsp";
        } else {
            request.setAttribute("errors",errors);
            return "signUp.jsp";
        }
    }
}
