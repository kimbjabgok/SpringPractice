package com.hongikDB.api.service;

import com.hongikDB.api.model.Student;
import com.hongikDB.api.repository.StudentRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import java.io.File;

@Service
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private StudentRepository studentRepository;

    @Value("${student.data.filepath}")
    private String htmlFilePath;

    @Override
    public void run(String... args) throws Exception {
        
        File input = new File(htmlFilePath);
        if (!input.exists()) {
            System.err.println("No FilePath");
            return;
        }

        Document doc = Jsoup.parse(input, "UTF-8");

        parseAndSave(doc, "PhD Students", "PhD");
        parseAndSave(doc, "Master Students", "Master");
        parseAndSave(doc, "Undergraduate Students", "Undergraduate"); 

    }

    private void parseAndSave(Document doc, String headingText, String degree) {
    	Element heading = doc.select("h2:contains(" + headingText + ")").first();

        if (heading == null) {
            System.err.println("No headingText : " + headingText);
            return;
        }
        Element table = heading.nextElementSibling();

        if (table != null) {
            int count = 0;
            
            Elements rows = table.select("tr");
            
            for (int i = 1; i < rows.size(); i++) {
                Element row = rows.get(i);
                Elements tds = row.select("td"); 
                
                if (tds.size() >= 3) {
                    String name = tds.get(0).text().trim();
                    String email = tds.get(1).text().trim();
                    String yearStr = tds.get(2).text().trim();

                    Student student = new Student();
                    student.setName(name);
                    student.setEmail(email);
                    student.setDegree(degree);
                    try {
                        student.setGraduation(Integer.parseInt(yearStr));
                    }
                    catch (NumberFormatException e) {
                        System.err.println("No yearStr : " + yearStr);
                    }
                    
                    studentRepository.save(student); 
                    count++;
                }
            }
        }
    }
}