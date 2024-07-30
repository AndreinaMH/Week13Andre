package pet.store.controller;

import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pet.store.controller.model.PetStoreData;
import pet.store.service.PetStoreService;

// localhost: 8080

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {

@Autowired
private PetStoreService  petStoreService;

@PostMapping
@ResponseStatus (code = HttpStatus.CREATED)
public PetStoreData insertPetStore (@RequestBody PetStoreData petStoreData) {
	log.info("Creating pet store { }", petStoreData);
	return petStoreService.savePetStore(petStoreData);
}

@PutMapping("/{petStoreId}")
public PetStoreData updatePetStore(@PathVariable Long petStoreId,
@RequestBody PetStoreData petStoreData) {
	petStoreData.setPetStoreId(petStoreId);
	log.info("Updating store with Id={}", petStoreId);
	return petStoreService.savePetStore(petStoreData);
	}

//Week 15
@PostMapping("/{petStoreId}/employee")
@ResponseStatus(code = HttpStatus.CREATED)
public PetStoreData.PetStoreEmployee insertEmployeeInPetStore(@PathVariable long petStoreId, @RequestBody PetStoreData.PetStoreEmployee employee){
	log.info("Adding employee: {} to store {}", employee, petStoreId);
	return petStoreService.saveEmployee(petStoreId, employee);
	}

@PostMapping("/{petStoreId}/customer")
@ResponseStatus(code = HttpStatus.CREATED)
public PetStoreData.PetStoreCustomer insertCustomerInPetStore(@PathVariable long petStoreId, @RequestBody PetStoreData.PetStoreCustomer customer){
	log.info("Adding customer: {} to store {}", customer, petStoreId);
	return petStoreService.saveCustomer(petStoreId, customer);
	}

@GetMapping
public List<PetStoreData> getAllPetStores() {
	log.info("Retrieving all pet store information without customer and employee data.");
	return petStoreService.retrieveAllPetStores();
	}
	
@GetMapping("/{petStoreId}")
public PetStoreData getPetStoreById(@PathVariable Long petStoreId) {
	log.info("Retrieving information for pet store with ID: '{}' ", petStoreId);
	return petStoreService.retrievePetStoreById(petStoreId);
	}

@DeleteMapping("/{petStoreId}")
public Map<String, String> deletePetStoreById (@PathVariable Long petStoreId){
	log.info("Deleting pet store with ID: '{}' ", petStoreId);
	petStoreService.deletePetStoreById(petStoreId);
	
	return Map.of( "message", "Deletion of pet store with Id: '" + petStoreId + "' was successful. ");
	}
}