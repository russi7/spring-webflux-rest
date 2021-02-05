package net.rusinol.spring5.webflux.springwebfluxrest.repositories;

import net.rusinol.spring5.webflux.springwebfluxrest.domain.Vendor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface VendorRepository extends ReactiveMongoRepository<Vendor, String> {
}
