package org.davidescobarr.quizbackend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.davidescobarr.quizbackend.dto.entity.Question;

import java.util.List;

@Data
@Schema(description = "Запрос на создание шаблона теста")
public class CreateTemplateRequest {
    @Schema(description = "Имя шаблона", example = "Шаблон")
    @Size(min = 3, max = 50, message = "Название шаблона должно содержать от 3 до 50 символов")
    @NotBlank(message = "Название шаблона не может быть пустыми")
    private String name;

    @Schema(description = "Описание", example = "Jon")
    @Size(min = 1, max = 512, message = "Описание шаблона должно содержать от 1 до 512 символов")
    @NotBlank(message = "Описание шаблона не может быть пустыми")
    private String description;

    @Schema(description = "Публичный ли тест?")
    private boolean is_private = false;

    @Schema(description = "Вопросы")
    private List<Question> questions;
}
