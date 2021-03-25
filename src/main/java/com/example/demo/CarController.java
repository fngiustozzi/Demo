package com.example.demo;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// tag::constructor[]
@RestController
class CarController {

	private final CarsRepository repository;

	private final CarModelAssembler assembler;

	CarController(CarsRepository repository, CarModelAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;
	}
	// end::constructor[]

	// Aggregate root

	// tag::get-aggregate-root[]
	@GetMapping("/cars")
	CollectionModel<EntityModel<Car>> all() {
		List<EntityModel<Car>> cars = repository.findAll().stream() //
				.map(assembler::toModel) //
				.collect(Collectors.toList());

		return CollectionModel.of(cars, linkTo(methodOn(CarController.class).all()).withSelfRel());
	}
	// end::get-aggregate-root[]

	// tag::post[]
	@PostMapping("/cars")
	ResponseEntity<?> newCar(@RequestBody Car newCar) {
		EntityModel<Car> entityModel = assembler.toModel(repository.save(newCar));

		return ResponseEntity //
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
				.body(entityModel);
	}
	// end::post[]

	// Single item

	// tag::get-single-item[]
	@GetMapping("/cars/{id}")
	EntityModel<Car> one(@PathVariable Long id) {

		Car c = repository.findById(id) //
				.orElseThrow(() -> new CarNotFoundException(id));

		return assembler.toModel(c);
	}
	// end::get-single-item[]

	// tag::put[]
	@PutMapping("/cars/{id}")
	ResponseEntity<?> replaceCar(@RequestBody Car newCar, @PathVariable Long id) {

		Car updatedCar = repository.findById(id) //
				.map(c -> {
					c.setName(newCar.getName());
					c.setHorsePower(newCar.getHorsePower());
					return repository.save(c);
				}) //
				.orElseGet(() -> {
					newCar.setId(id);
					return repository.save(newCar);
				});

		EntityModel<Car> entityModel = assembler.toModel(updatedCar);

		return ResponseEntity //
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
				.body(entityModel);
	}
	// end::put[]

	// tag::delete[]
	@DeleteMapping("/cars/{id}")
	ResponseEntity<?> deleteCar(@PathVariable Long id) {
		repository.deleteById(id);

		return ResponseEntity.noContent().build();
	}
	// end::delete[]
}

