package net.engineeringdigest.journalApp.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SentimentData {
    private String email; // key
    private String sentiment; // value pass to kafka
}
