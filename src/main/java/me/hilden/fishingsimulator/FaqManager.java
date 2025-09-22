package me.hilden.fishingsimulator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

public class FaqManager {

    private File faqFile = Paths.get("C:\\Users\\denis\\IdeaProjects\\FishingSimulator\\src\\main\\resources\\json\\faq.json").toFile();

    public FaqManager() {
    }

    public Faq getFaq() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Faq[] faqs = objectMapper.readValue(faqFile, Faq[].class);
        return null;
    }



}
