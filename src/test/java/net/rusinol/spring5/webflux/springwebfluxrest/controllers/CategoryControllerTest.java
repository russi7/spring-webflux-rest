package net.rusinol.spring5.webflux.springwebfluxrest.controllers;

import net.rusinol.spring5.webflux.springwebfluxrest.domain.Category;
import net.rusinol.spring5.webflux.springwebfluxrest.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

class CategoryControllerTest {

    private WebTestClient webTestClient;
    private CategoryRepository categoryRepository;
    private CategoryController toTest;

    @BeforeEach
    void setUp() {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        toTest = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(toTest).build();
    }

    @Test
    void list() {
        BDDMockito.given(categoryRepository.findAll())
                .willReturn(Flux.just(Category.builder().description("Category 1").build()));

        webTestClient.get()
                .uri("/api/v1/categories")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(1);
    }

    @Test
    void getById() {
        BDDMockito.given(categoryRepository.findById(Mockito.anyString()))
                .willReturn(Mono.just(Category.builder().description("Category 1").build()));

        webTestClient.get()
                .uri("/api/v1/categories/1")
                .exchange()
                .expectBody(Category.class);
    }

    @Test
    public void createCategory() {
        BDDMockito.given(categoryRepository.saveAll(Mockito.any(Publisher.class)))
                .willReturn(Flux.just(new Category()));

        Mono<Category> categoryMono = Mono.just(Category.builder().description("Category created").build());

        webTestClient.post()
                .uri("/api/v1/categories")
                .body(categoryMono, Category.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void update() {
        BDDMockito.given(categoryRepository.save(Mockito.any(Category.class)))
                .willReturn(Mono.just(new Category()));

        Mono<Category> categoryMono = Mono.just(Category.builder().description("Category updated").build());

        webTestClient.put()
                .uri("/api/v1/categories/1")
                .body(categoryMono, Category.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void patch() {
        BDDMockito.given(categoryRepository.findById(Mockito.anyString()))
                .willReturn(Mono.just(new Category()));
        BDDMockito.given(categoryRepository.save(Mockito.any(Category.class)))
                .willReturn(Mono.just(new Category()));

        Mono<Category> categoryMono = Mono.just(Category.builder().description("Category updated").build());

        webTestClient.patch()
                .uri("/api/v1/categories/1")
                .body(categoryMono, Category.class)
                .exchange()
                .expectStatus()
                .isOk();
    }
}