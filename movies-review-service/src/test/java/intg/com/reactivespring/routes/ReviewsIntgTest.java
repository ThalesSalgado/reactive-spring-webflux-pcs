package com.reactivespring.routes;

import com.reactivespring.domain.Review;
import com.reactivespring.repository.ReviewReactiveRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
public class ReviewsIntgTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    ReviewReactiveRepository reviewReactiveRepository;

    static String REVIEWS_URL = "/v1/reviews";

    @BeforeEach
    void setUp() {
        var reviews = List.of(
                new Review(null, 1L, "Awesome Movie", 9.0),
                new Review(null, 1L, "Awesome Movie 2", 9.0),
                new Review(null, 2L, "Excellent Movie", 8.0),
                new Review("1a", 3L, "Test Movie", 8.5));

        reviewReactiveRepository.saveAll(reviews)
                .blockLast();
    }

    @AfterEach
    void tearDown() {
        reviewReactiveRepository.deleteAll().block();
    }

    @Test
    void addReview() {
        var review = new Review(null, 1L, "Awesome movie", 9.0);

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
    void getReviews() {
        webTestClient.get()
                .uri(REVIEWS_URL)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Review.class)
                .consumeWith(listEntityExchangeResult -> {
                    var reviews = listEntityExchangeResult.getResponseBody();
                    assertNotNull(reviews);
                    assertEquals(4, reviews.size());
                });
    }

    @Test
    void getReviews_ByMovieInfoId() {
        var movieInfoId = 1L;

        webTestClient.get()
                .uri(REVIEWS_URL + "?movieInfoId={id}", movieInfoId)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Review.class)
                .consumeWith(listEntityExchangeResult -> {
                    var reviews = listEntityExchangeResult.getResponseBody();
                    assertNotNull(reviews);
                    assertEquals(2, reviews.size());
                });
    }

    @Test
    void updateReview() {
        var reviewId = "1a";
        var updateReview = new Review(reviewId, 3L, "Update Test Movie 2", 8.8);

        webTestClient.put()
                .uri(REVIEWS_URL + "/{id}", reviewId)
                .bodyValue(updateReview)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Review.class)
                .consumeWith(reviewEntityExchangeResult -> {
                    var updatedReview = reviewEntityExchangeResult.getResponseBody();
                    assertNotNull(updatedReview);
                    assertEquals(updateReview.getComment(), updatedReview.getComment());
                    assertEquals(updateReview.getRating(), updatedReview.getRating());
                });
    }

    @Test
    void deleteReview() {
        var reviewId = "1a";

        webTestClient.delete()
                .uri(REVIEWS_URL + "/{id}", reviewId)
                .exchange()
                .expectStatus()
                .isNoContent();
    }

}
