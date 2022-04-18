package com.reactivespring.routes;

import com.reactivespring.domain.Review;
import com.reactivespring.exceptionhandler.MovieReviewGlobalErrorHandler;
import com.reactivespring.handler.ReviewHandler;
import com.reactivespring.repository.ReviewReactiveRepository;
import com.reactivespring.router.ReviewRouter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;


@WebFluxTest
@ContextConfiguration(classes = { ReviewRouter.class, ReviewHandler.class, MovieReviewGlobalErrorHandler.class })
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class ReviewsUnitTest {

    @MockBean
    private ReviewReactiveRepository reviewReactiveRepository;

    @Autowired
    private WebTestClient webTestClient;

    static String REVIEWS_URL = "/v1/reviews";

    @Test
    void addReview() {
        var review = new Review(null, 1L, "Awesome movie", 9.2);

        when(reviewReactiveRepository.save(isA(Review.class)))
                .thenReturn(Mono.just(new Review("1a", 1L, "Awesome movie", 9.2)));

        webTestClient.post()
                .uri(REVIEWS_URL)
                .bodyValue(review)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(Review.class)
                .consumeWith(reviewEntityExchangeResult -> {
                    var savedReview = reviewEntityExchangeResult.getResponseBody();
                    assert savedReview != null;
                    assert savedReview.getReviewId() != null;
                });
    }

    @Test
    void addReview_validation() {
        var review = new Review(null, null, "Awesome movie", -9.2);

        when(reviewReactiveRepository.save(isA(Review.class)))
                .thenReturn(Mono.just(new Review("1a", 1L, "Awesome movie", 9.2)));

        webTestClient.post()
                .uri(REVIEWS_URL)
                .bodyValue(review)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(String.class)
                .isEqualTo("rating.movieInfoId : must not be null,rating.negative : please pass a non-negative value");
    }

    @Test
    void getReviews() {
        var reviews = List.of(
                new Review(null, 1L, "Awesome Movie", 9.0),
                new Review(null, 1L, "Awesome Movie 2", 9.0),
                new Review(null, 2L, "Excellent Movie", 8.0),
                new Review("1a", 3L, "Test Movie", 8.5));

        when(reviewReactiveRepository.findAll()).thenReturn(Flux.fromIterable(reviews));

        webTestClient.get()
                .uri(REVIEWS_URL)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Review.class)
                .consumeWith(listEntityExchangeResult -> {
                    var reviewsResponse = listEntityExchangeResult.getResponseBody();
                    assertNotNull(reviewsResponse);
                    assertEquals(4, reviewsResponse.size());
                });
    }

    @Test
    void updateReview() {
        var review = new Review("1a", 3L, "Test Movie", 8.5);
        var review2 = new Review("1a", 3L, "New Test Movie", 9.2);

        when(reviewReactiveRepository.findById(isA(String.class))).thenReturn(Mono.just(review));
        when(reviewReactiveRepository.save(isA(Review.class))).thenReturn(Mono.just(review2));

        webTestClient.put()
                .uri(REVIEWS_URL + "/{id}", review.getReviewId())
                .bodyValue(review2)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Review.class)
                .consumeWith(reviewEntityExchangeResult -> {
                    var updatedReview = reviewEntityExchangeResult.getResponseBody();
                    assertNotNull(updatedReview);
                    assertEquals("New Test Movie", updatedReview.getComment());
                    assertEquals(9.2, updatedReview.getRating());
                });
    }

    @Test
    void deleteReview() {
        var review = new Review("1a", 3L, "Test Movie", 8.5);

        when(reviewReactiveRepository.findById(isA(String.class))).thenReturn(Mono.just(review));
        when(reviewReactiveRepository.deleteById(isA(String.class))).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri(REVIEWS_URL + "/{id}", review.getReviewId())
                .exchange()
                .expectStatus()
                .isNoContent();
    }

}
