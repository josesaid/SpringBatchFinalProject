package pe.edu.galaxy.training.java.sb.spring.batch.appspring.batchdemocollectionv1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsProcess implements Serializable {
    private String id;
    private String author;
    private String type;
    private String typeVersionNumber;
    private String feedTitle;
    private String category;
    private String entryTitle;

    public static String[] getNames() {
        return new String[]{"id", "author", "type", "typeVersionNumber", "feedTitle", "category", "entryTitle"};
    }

}
