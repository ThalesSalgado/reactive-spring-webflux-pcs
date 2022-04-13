package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FluxAndMonoGeneratorServiceTest {

    FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

    @Test
    void namesFlux() {
        //given

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFLux();

        //then
        StepVerifier.create(namesFlux)
                //.expectNext("alex", "ben", "chloe")
                //.expectNextCount(3)
                .expectNext("alex")
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void namesFluxMap() {
        //given

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFLuxMap();

        //then
        StepVerifier.create(namesFlux)
                .expectNext("ALEX", "BEN", "CHLOE")
                .verifyComplete();
    }

    @Test
    void namesFluxImmutability() {
        //given

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFLuxImmutability();

        //then
        StepVerifier.create(namesFlux)
                //.expectNext("ALEX", "BEN", "CHLOE")
                .expectNext("alex", "ben", "chloe")
                .verifyComplete();
    }

    @Test
    void namesFluxFilterLength() {
        //given

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFLuxFilterLength(3);

        //then
        StepVerifier.create(namesFlux)
                .expectNext("ALEX", "CHLOE")
                .verifyComplete();
    }

    @Test
    void namesFluxFlatMap() {
        //given

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFLuxFlatMap(3);

        //then
        StepVerifier.create(namesFlux)
                .expectNext("A","L","E","X","C","H","L","O","E")
                .verifyComplete();
    }

    @Test
    void namesFluxFlatMapAsync() {
        //given
        int stringLength = 3;

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFLuxFlatMapAsync(stringLength);

        //then
        StepVerifier.create(namesFlux)
                //.expectNext("A","L","E","X","C","H","L","O","E")
                .expectNextCount(9)
                .verifyComplete();
    }

    @Test
    void namesFluxConcatMapAsync() {
        //given
        int stringLength = 3;

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFLuxConcatMapAsync(stringLength);

        //then
        StepVerifier.create(namesFlux)
                .expectNext("A","L","E","X","C","H","L","O","E")
                //.expectNextCount(9)
                .verifyComplete();
    }

    @Test
    void namesMonoFlatMap() {
        //given
        int stringLength = 3;

        //when
        var nameMono = fluxAndMonoGeneratorService.namesMonoFlatMap(stringLength);

        //then
        StepVerifier.create(nameMono)
                .expectNext(List.of("A","L","E","X"))
                .verifyComplete();
    }

    @Test
    void namesMonoFlatMapMany() {
        //given
        int stringLength = 3;

        //when
        var nameMono = fluxAndMonoGeneratorService.namesMonoFlatMapMany(stringLength);

        //then
        StepVerifier.create(nameMono)
                .expectNext("A","L","E","X")
                .verifyComplete();
    }

    @Test
    void namesFluxTransform() {
        //given
        int stringLength = 3;

        //when
        var nameMono = fluxAndMonoGeneratorService.namesFLuxTransform(stringLength);

        //then
        StepVerifier.create(nameMono)
                .expectNext("A","L","E","X","C","H","L","O","E")
                .verifyComplete();
    }

    @Test
    void namesFluxTransform2() {
        //given
        int stringLength = 6;

        //when
        var nameMono = fluxAndMonoGeneratorService.namesFLuxTransform(stringLength);

        //then
        StepVerifier.create(nameMono)
                //.expectNext("A","L","E","X","C","H","L","O","E")
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    void namesFluxTransformSwitchIfEmpty() {
        //given
        int stringLength = 6;

        //when
        var nameMono = fluxAndMonoGeneratorService.namesFLuxTransformSwitchIfEmpty(stringLength);

        //then
        StepVerifier.create(nameMono)
                //.expectNext("A","L","E","X","C","H","L","O","E")
                .expectNext("D","E","F","A","U","L","T")
                .verifyComplete();
    }

}