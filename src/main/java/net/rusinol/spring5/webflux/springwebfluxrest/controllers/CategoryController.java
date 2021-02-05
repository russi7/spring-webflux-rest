package net.rusinol.spring5.webflux.springwebfluxrest.controllers;

import net.rusinol.spring5.webflux.springwebfluxrest.domain.Category;
import net.rusinol.spring5.webflux.springwebfluxrest.repositories.CategoryRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CategoryController {

    public final static String BASE_URL = "/api/v1/categories";
    public final static String ID = "/{id}";
    public final static String BASE_URL_ID = BASE_URL + ID;

    final private CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping(BASE_URL)
    public Flux<Category> list() {
        return categoryRepository.findAll();
    }

    @GetMapping(BASE_URL_ID)
    public Mono<Category> getById(@PathVariable String id) {
        return categoryRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(BASE_URL)
    public Mono<Void> create(@RequestBody Publisher<Category> categoryPublisher) {
        return categoryRepository.saveAll(categoryPublisher).then();
    }

    @PatchMapping(BASE_URL_ID)
    public Mono<Category> update(@PathVariable String id, @RequestBody Category category) {
        category.setId(id);
        return categoryRepository.save(category);
    }

    @PutMapping(BASE_URL_ID)
    public Mono<Category> patch(@PathVariable String id, @RequestBody Category category) {

        Category existingCategory = categoryRepository.findById(id).block();
        if (!existingCategory.getDescription().equals(category.getDescription())) {
            existingCategory.setDescription(category.getDescription());
            categoryRepository.save(existingCategory);
        }

        return Mono.just(existingCategory);
    }
}
