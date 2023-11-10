package com.wf.br;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

@SpringBootTest
class BourseReactiveApplicationTests {

	@Test
	void text_element_without_error() {
		List<String> list=List.of("Java","Standard","Edition");
		Flux<String> stringFlux= Flux.fromIterable(list);
		StepVerifier.create(stringFlux)
				.expectNext("Java")
				.expectNext("Standard")
				.expectNext("Edition")
		.verifyComplete();

	}

	@Test
	void text_element_with_error() {
		List<String> list=List.of("Java","Standard","Edition");
		Flux<String> stringFlux= Flux.fromIterable(list)
				.concatWith(Flux.error(new RuntimeException("Une erreur est survenue !!!")))
				.concatWith(Flux.just("après erreur"))   // n'execute pas cette partie
				.log();
		StepVerifier.create(stringFlux)
				.expectNext("Java")
				.expectNext("Standard")
				.expectNext("Edition")
//				.expectError(RuntimeException.class)
				.expectErrorMessage("Une erreur est survenue !!!")
				.verify();

	}

	@Test
	void text_element_count_with_error() {
		List<String> list=List.of("Java","Standard","Edition");
		Flux<String> stringFlux= Flux.fromIterable(list)
				.concatWith(Flux.error(new RuntimeException("Une erreur est survenue !!!")))
				.log();
		StepVerifier.create(stringFlux)
				.expectNextCount(3)
				.expectErrorMessage("Une erreur est survenue !!!")
				.verify();

	}

	@Test
	void text_element__with_error1() {
		List<String> list=List.of("Java","Standard","Edition");
		Flux<String> stringFlux= Flux.fromIterable(list)
				.concatWith(Flux.error(new RuntimeException("Une erreur est survenue !!!")))
				.log();
		StepVerifier.create(stringFlux)
				.expectNext("Java","Standard","Edition")
				.expectErrorMessage("Une erreur est survenue !!!")
				.verify();

	}

	@Test
	void text_mono() {
		Mono<String> java = Mono.just("Java");
		StepVerifier.create(java.log())
				.expectNext("Java")
				.verifyComplete();
	}

	@Test
	void text_mono_error() {
		StepVerifier.create(Mono.error(new RuntimeException("Error occur")).log())
				.expectError(RuntimeException.class)
				.verify();
	}
}
