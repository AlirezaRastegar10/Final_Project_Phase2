package ir.maktab.service;

import ir.maktab.entity.Customer;
import ir.maktab.exceptions.UserNotFoundException;
import ir.maktab.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findById(long id) {
        return customerRepository.findById(id).orElseThrow(() -> new UserNotFoundException("no customer found with this ID"));
    }
}
