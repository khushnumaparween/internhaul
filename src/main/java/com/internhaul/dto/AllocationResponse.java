package com.internhaul.dto;

import lombok.*;

        import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllocationResponse {

    private Long id;

    private Long internId;
    private String internName;

    private Long projectId;
    private String projectName;

    private String status;

    private LocalDateTime allocatedAt;
}
