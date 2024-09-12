package com.antonio.curso.springboot.jpa.springboot_jpa_relationship;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.antonio.curso.springboot.jpa.springboot_jpa_relationship.entities.Address;
import com.antonio.curso.springboot.jpa.springboot_jpa_relationship.entities.Client;
import com.antonio.curso.springboot.jpa.springboot_jpa_relationship.entities.Invoice;
import com.antonio.curso.springboot.jpa.springboot_jpa_relationship.repositories.ClientRepository;
import com.antonio.curso.springboot.jpa.springboot_jpa_relationship.repositories.InvoiceRepository;

@SpringBootApplication
public class SpringbootJpaRelationshipApplication implements CommandLineRunner{
	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private InvoiceRepository InvoiceRepository;	

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaRelationshipApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		oneToMany();
	}

	@Transactional
	public void oneToMany() {
		Client client = new Client("Fran", "moras");

		Address address1 = new Address("El verjel", 1234);
		Address address2 = new Address("Vasco de Gama", 9875);

		client.getAddresses().add(address1);
		client.getAddresses().add(address2);

		clientRepository.save(client);

		System.out.println(client);

	}



	@Transactional
	public void manyToOne() {
		Client client = new Client("John", "Doe");
		clientRepository.save(client);

		Invoice invoice = new Invoice("compras de oficina", 2000L);
		invoice.setClient(client);

		Invoice invoiceDb = InvoiceRepository.save(invoice);

		System.out.println(invoiceDb);
	}

	@Transactional
	public void manyToOneFindByIdClient() {
		
		Optional<Client> optionalClient = clientRepository.findById(1L);
		
		if(optionalClient.isPresent()) {
			Client client = optionalClient.orElseThrow();

			Invoice invoice = new Invoice("compras de oficina", 2000L);
			invoice.setClient(client);
			Invoice invoiceDb = InvoiceRepository.save(invoice);
			System.out.println(invoiceDb);
		}
	}
}
