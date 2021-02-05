package net.rusinol.spring5.webflux.springwebfluxrest.bootstrap;

import net.rusinol.spring5.webflux.springwebfluxrest.domain.Category;
import net.rusinol.spring5.webflux.springwebfluxrest.domain.Vendor;
import net.rusinol.spring5.webflux.springwebfluxrest.repositories.CategoryRepository;
import net.rusinol.spring5.webflux.springwebfluxrest.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private final VendorRepository vendorRepository;
    private final CategoryRepository categoryRepository;

    public Bootstrap(VendorRepository vendorRepository, CategoryRepository categoryRepository) {
        this.vendorRepository = vendorRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        categoryRepository.deleteAll().block();
        vendorRepository.deleteAll().block();

//        if(categoryRepository.count().block() == 0) {
            System.out.println("Loading data");

            categoryRepository.save(Category.builder().description("Fruits").build()).block();
            categoryRepository.save(Category.builder().description("Nuts").build()).block();
            categoryRepository.save(Category.builder().description("Breads").build()).block();
            categoryRepository.save(Category.builder().description("Meats").build()).block();
            categoryRepository.save(Category.builder().description("Eggs").build()).block();

            System.out.println("Categories loaded " + categoryRepository.count().block());

            vendorRepository.save(Vendor.builder().firstName("Joe").lastName("Buck").build()).block();
            vendorRepository.save(Vendor.builder().firstName("Michael").lastName("Weston").build()).block();
            vendorRepository.save(Vendor.builder().firstName("Jessie").lastName("Waters").build()).block();
            vendorRepository.save(Vendor.builder().firstName("Bill").lastName("Nershi").build()).block();
            vendorRepository.save(Vendor.builder().firstName("Jimmy").lastName("Buffet").build()).block();

            System.out.println("Loaded Vendors " + vendorRepository.count().block());
//        }
    }
}
