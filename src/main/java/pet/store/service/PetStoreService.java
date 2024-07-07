package pet.store.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pet.store.controller.model.PetStoreData;
import pet.store.dao.PetStoreDao;
import pet.store.entity.PetStore;
import org.springframework.beans.factory.annotation.*;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class PetStoreService {

      @Autowired
      private  PetStoreDao petStoreDao;

     @Transactional (readOnly = false)
public PetStoreData savePetStore (PetStoreData petStoreData){
Long petStoreId = petStoreData.getPetStoreId( );
PetStore petStore = findOrCreatePetStore(petStoreId);
copyPetStoreFields(petStore, petStoreData);
return new PetStoreData(petStoreDao.save(petStore));
}

private void copyPetStoreFields (PetStore petStore, PetStoreData petStoreData){petStore.setPetStoreName(petStoreData.getPetStoreName());
petStore.setPetStoreName(petStoreData.getPetStoreName());
petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
petStore.setPetStoreCity(petStoreData.getPetStoreCity());
petStore.setPetStoreState(petStoreData.getPetStoreState());
petStore.setPetStoreZip(petStoreData.getPetStoreZip());
petStore.setPetStorePhone(petStoreData.getPetStorePhone());
petStore.setPetStoreId(petStoreData.getPetStoreId());
}

private PetStore findOrCreatePetStore(Long petStoreId){
 PetStore petStore = new PetStore();

if (!Objects.isNull(petStoreId)){
      petStore = findPetStoreById(petStoreId);
}

return petStore;
}

private PetStore findPetStoreById (Long petStoreId) {
return petStoreDao.findById(petStoreId). orElseThrow (()-> new NoSuchElementException("pet store with Id: " + petStoreId + " Was not found"));}
}