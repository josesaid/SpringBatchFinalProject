package pe.edu.galaxy.training.java.sb.spring.batch.appspring.batchdemocollectionv1.callback;

import org.springframework.batch.item.file.FlatFileHeaderCallback;
import java.io.IOException;
import java.io.Writer;

public class NewsCsvFlatFileHeaderCallback implements FlatFileHeaderCallback {
    @Override
    public void writeHeader(Writer writer) throws IOException {
        writer.write("id,author,type,typeVersionNumber,feedTitle,category,entryTitle");
    }

}
