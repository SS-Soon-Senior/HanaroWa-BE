package com.ss.hanarowa.domain.lesson.dto.request;

import java.util.List;

import com.ss.hanarowa.domain.lesson.entity.Category;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateLessonRequestDTO {
    
    @NotBlank(message = "강좌명은 필수입니다")
    private String lessonName;
    
    @NotBlank(message = "강사명은 필수입니다")
    private String instructor;
    
    @NotBlank(message = "지시사항은 필수입니다")
    private String instruction;
    
    @NotBlank(message = "설명은 필수입니다")
    private String description;
    
    @NotNull(message = "카테고리는 필수입니다")
    private Category category;
    
    private String lessonImg;
    
    @NotNull(message = "지점 ID는 필수입니다")
    private Long branchId;
    
    @Valid
    @NotEmpty(message = "최소 하나의 기수는 필요합니다")
    private List<CreateLessonGisuRequestDTO> lessonGisus;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateLessonGisuRequestDTO {
        
        @NotNull(message = "정원은 필수입니다")
        private Integer capacity;
        
        @NotNull(message = "수강료는 필수입니다")
        private Integer lessonFee;
        
        @NotBlank(message = "수업 기간은 필수입니다")
        private String duration;
        
        @NotNull(message = "강의실 ID는 필수입니다")
        private Long lessonRoomId;
        
        @Valid
        @NotEmpty(message = "최소 하나의 커리큘럼은 필요합니다")
        private List<CreateCurriculumRequestDTO> curriculums;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateCurriculumRequestDTO {
        
        @NotBlank(message = "커리큘럼 내용은 필수입니다")
        private String content;
    }
}