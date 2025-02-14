package org.davidescobarr.quizbackend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Запрос с id временнего пользователя")
public class PassedTestUserRequest {
    @Schema(description = "Id временного пользователя", example = "2422")
    @NotBlank(message = "Id временного пользователя не должно быть пустым")
    private Long idPassedTestUser;
}
