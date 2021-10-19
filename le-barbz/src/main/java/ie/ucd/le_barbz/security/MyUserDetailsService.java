package ie.ucd.le_barbz.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ie.ucd.le_barbz.model.Customer;
import ie.ucd.le_barbz.model.MyUserDetails;
import ie.ucd.le_barbz.repository.CustomerRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

  @Autowired
  CustomerRepository customerRepository;

  @Override
   public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    Optional<Customer> user = customerRepository.findByUserName(userName);

    user.orElseThrow(() -> new UsernameNotFoundException("User not found"));

    return user.map(MyUserDetails::new).get();
  }
    
}
