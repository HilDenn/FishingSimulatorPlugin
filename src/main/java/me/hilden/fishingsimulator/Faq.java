package me.hilden.fishingsimulator;

import java.util.UUID;

public class Faq {

    private String title;
    private String name;
    private String answer;
    private String number;
    private String material;
    private UUID uuid = UUID.randomUUID();

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getAnswer() {
        return answer;
    }

    public String getNumber() {
        return number;
    }

    public String getMaterial() {
        return material;
    }

    public UUID getUuid() {
        return uuid;
    }
}
