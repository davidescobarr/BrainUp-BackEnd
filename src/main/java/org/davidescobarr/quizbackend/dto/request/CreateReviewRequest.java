package org.davidescobarr.quizbackend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на создание отзыва о тесте")
public class CreateReviewRequest {
    @Schema(description = "Название отзыва", example = "Отзыв")
    @Size(min = 3, max = 50, message = "Название отзыва должно содержать от 3 до 50 символов")
    @NotBlank(message = "Название отзыва не может быть пустыми")
    private String name;

    @Schema(description = "Отзыв", example = "Отзыв")
    @Size(min = 1, max = 512, message = "Отзыв должен содержать от 1 до 512 символов")
    @NotBlank(message = "Отзыв не может быть пустыми")
    private String comment;

    @Schema(description = "Рейтинг", example = "5")
    @Size(min = 1, max = 5, message = "Рейтинг отзыва должен быть от 1 до 5")
    @NotBlank(message = "Рейтинг не может быть пустым")
    private int rating;
}
