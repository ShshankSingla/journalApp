package net.engineeringdigest.journalApp.entity;


import lombok.*;
import net.engineeringdigest.journalApp.enums.Sentiment;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "journal_entries")
@Data
@NoArgsConstructor
//@Getter
//@Setter
public class JournalEntry {
    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;
    private Sentiment sentiment;



//    public String getContent() {
//        return content;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public ObjectId getId() {
//        return id;
//    }
//
//    public LocalDateTime getDate() {
//        return date;
//    }
//
//    public void setId(ObjectId id) {
//        this.id = id;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public void setDate(LocalDateTime date) {
//        this.date = date;
//    }




}
