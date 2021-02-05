package net.rusinol.spring5.webflux.springwebfluxrest.repositories;

import net.rusinol.spring5.webflux.springwebfluxrest.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {

}
