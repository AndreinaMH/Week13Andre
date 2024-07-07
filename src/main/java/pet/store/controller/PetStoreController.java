package pet.store.controller;

import lombok.extern.slf4j.Slf4j;
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
}