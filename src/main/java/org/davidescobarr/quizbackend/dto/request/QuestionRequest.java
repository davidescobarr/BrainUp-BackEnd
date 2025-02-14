package org.davidescobarr.quizbackend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.davidescobarr.quizbackend.dto.entity.Answer;
import org.davidescobarr.quizbackend.enums.TypeQuestionEnum;

import java.util.List;

@Data
@Schema(description = "Запрос на создание/редактирование вопроса")
public class QuestionRequest {
    @Schema(description = "Название вопроса", example = "Вопрос №1")
    @Size(min = 3, max = 50, message = "Название вопроса должно содержать от 3 до 50 символов")
    @NotBlank(message = "Название отзыва не может быть пустыми")
    private String name;

    @Schema(description = "Тип вопроса", example = "one_variant")
    @NotBlank(message = "Тип вопроса не может быть пустыми")
    private TypeQuestionEnum typeQuestion;

    @Schema(description = "Описание", example = "Описание")
    @Size(min = 1, max = 512, message = "Описание должно содержать от 1 до 512 символов")
    @NotBlank(message = "Описание не может быть пустыми")
    private String description;

    @Schema(description = "Ответы", example = "")
    @NotBlank(message = "Ответы не может быть пустыми")
    private List<Answer> answers;

    @Schema(description = "Позиция", example = "2")
    @Size(min = 1, max = 512, message = "Позиция должна содержать от 1 до 512 символов")
    @NotBlank(message = "Позиция не может быть пустыми")
    private int order = 0;

    @Schema(description = "Показ правильного ответа", example = "true")
    private boolean show_correctly_answer = false;
}
