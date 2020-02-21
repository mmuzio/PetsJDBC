package com.example.maindriver;

import com.example.dao.PetDaoImpl;
import com.example.model.Pet;

public class MainDriver {

	public static void main(String[] args) {
		
		PetDaoImpl pdi = new PetDaoImpl();
		
		Pet petOne = new Pet("Siri", "Doggo");
		
		Pet petTwo = new Pet("Zed", "Cat");
		
		Pet petThree = new Pet("Meow", "Catto");
		
		pdi.insertPet(petOne);
		
		pdi.insertPet(petTwo);
		
		System.out.println(pdi.selectPetByName("Siri"));
		
		System.out.println(pdi.selectAllPets());
		
		petOne.setType("cat");
		
		pdi.updatePet(petOne);
		
		pdi.deletePet(petThree);
		
		System.out.println(pdi.selectAllPets());

	}

}
