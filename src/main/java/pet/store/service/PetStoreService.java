package pet.store.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreData.PetStoreCustomer;
import pet.store.controller.model.PetStoreData.PetStoreEmployee;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;
import org.springframework.beans.factory.annotation.*;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.LinkedList;
//We added the import in java utilLinkedList in week 15. And we also imported pet.store.dao.customer.Dao and petstoredao.Employee.Dao

@Service
public class PetStoreService {

@Autowired
private PetStoreDao petStoreDao;
      
@Autowired 
private EmployeeDao employeeDao;
      
@Autowired
private CustomerDao customerDao;
//Another changes in week 15 is that we also added CustomerDao and EmployeeDao. WEEK 15------for VIDEO.

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

private PetStore findPetStoreById (Long petStoreId){
return petStoreDao.findById(petStoreId). orElseThrow (()-> new NoSuchElementException("pet store with Id: " + petStoreId + " Was not found"));
 }

//WEEK 15

@Transactional(readOnly = false)
public PetStoreData.PetStoreEmployee saveEmployee (Long petStoreId, PetStoreEmployee employee){
	PetStore petStore = findPetStoreById(petStoreId);
	Long employeeId = employee.getEmployeeId();
	Employee Employees = findOrCreateEmployee(petStoreId, employeeId);
	
	copyEmployeeFields(Employees, employee);
	Employees.setPetStore(petStore);
	petStore.getEmployees().add(Employees);
	
	return new PetStoreData.PetStoreEmployee(employeeDao.save(Employees));	
}

//WEEK 15
	@SuppressWarnings("null")
	private Employee findOrCreateEmployee (Long petStoreId, Long employeeId) {
		Employee employee;
	
		
	if(Objects.isNull(employeeId)){
		employee = new Employee();
		
	} else {
		
		employee = findEmployeeById(petStoreId, employeeId);
	}
	
	return employee;
}

// 	WEEK 15 
private Employee findEmployeeById (long petStoreId, long employeeId) {
	Employee employee = employeeDao.findById(employeeId).orElseThrow(
			() -> new NoSuchElementException("Employee with ID: " + employeeId + " does not exists.")
	);

	if (Objects.equals(employee.getPetStore().getPetStoreId(), petStoreId)){
			return employee;
	
	} else {
		
			throw new IllegalArgumentException ("Employee iwth ID " + employee + "Does not exists at:" + petStoreId);
	}
}

private void copyEmployeeFields(Employee employee, PetStoreData.PetStoreEmployee employees) {
	employee.setEmployeeFirstName(employees.getEmployeeFirstName());
	employee.setEmployeeLastName(employees.getEmployeeLastName());
	employee.setEmployeePhone(employees.getEmployeePhone());
	employee.setEmployeeJobTitle(employees.getEmployeeJobTitle());
}

@Transactional (readOnly = false)
public PetStoreData.PetStoreCustomer saveCustomer(Long petStoreId, PetStoreData.PetStoreCustomer petStoreCustomer){
		PetStore petStore = findPetStoreById(petStoreId);
		Long customerId = petStoreCustomer.getCustomerId();
		Customer customer = findOrCreateCustomer(petStoreId, customerId);

		
	copyCustomerFields(customer, petStoreCustomer);
	customer.getPetStores().add(petStore);
	petStore.getCustomers().add(customer);

	
	return new PetStoreData.PetStoreCustomer (customerDao.save(customer));
}
//WEEK 15

private Customer findOrCreateCustomer(Long petStoreId, Long customerId){
	Customer customer;
	
	if(Objects.isNull(customerId)) {
		customer = new Customer();
	
	} else {
		
		customer = findCustomerById(petStoreId, customerId);
	}
		
	return customer;
}
		
private Customer findCustomerById (Long petStoreId, Long customerId) {
	Customer customer = customerDao.findById(customerId).orElseThrow(
			() -> new NoSuchElementException ("Customer with ID: " + customerId + "Does not exists.")
	);
	
	boolean storeFound = false;
	
	for (PetStore petStore : customer.getPetStores()) {
		if(Objects.equals(petStore.getPetStoreId(), petStoreId)){
			storeFound = true;
		}
	}
	
	if(storeFound){
		return customer;
		
	} else {
		
		throw new IllegalArgumentException("Pet store " + petStoreId + " does not have a customer " + customerId);
			}
		}
//WEEK 15
	private void copyCustomerFields(Customer customer, PetStoreData.PetStoreCustomer petStoreCustomer){ 
		customer.setCustomerFirstName(petStoreCustomer.getCustomerFirstName());
		customer.setCustomerLastName(petStoreCustomer.getCustomerLastName());
		customer.setCustomerEmail(petStoreCustomer.getCustomerEmail());	
}

@Transactional(readOnly = true)
public List <PetStoreData> retrieveAllPetStores(){
		List<PetStore> petStores = petStoreDao.findAll();
		List<PetStoreData> result = new LinkedList<>();
		
		
		for(PetStore petStore : petStores){
			PetStoreData petStoreData = new PetStoreData(petStore);
			
				result.add(petStoreData);
			}
		
		return result;
	}
		
@Transactional(readOnly = true)
public PetStoreData retrievePetStoreById(Long petStoreId){
	return new PetStoreData(findPetStoreById(petStoreId));	
}

	public void deletePetStoreById(long petStoreId){
		PetStore petStore = findPetStoreById(petStoreId);
		petStoreDao.delete(petStore);
	}
}