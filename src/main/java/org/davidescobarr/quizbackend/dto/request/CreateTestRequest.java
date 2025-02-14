package org.davidescobarr.quizbackend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Запрос на создание теста")
public class CreateTestRequest {
    @Schema(description = "Id шаблона теста", example = "12499539")
    @NotBlank(message = "Id шаблона теста не должно быть пустым")
    private Long idTemplate;
}
