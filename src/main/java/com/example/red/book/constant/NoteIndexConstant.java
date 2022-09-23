package com.example.red.book.constant;

public class NoteIndexConstant {

    public static final String MAPPING_TEMPLATE = "{\n" +
            "  \"mappings\": {\n" +
            "    \"properties\": {\n" +
            "      \"title\":{\n" +
            "        \"type\":\"text\",\n" +
            "        \"analyzer\": \"ik_smart\"\n" +
            "      },\n" +
            "      \"content\":{\n" +
            "        \"type\":\"text\",\n" +
            "        \"analyzer\": \"ik_smart\"\n" +
            "      },\n" +
            "    }\n" +
            "  }\n" +
            "}";
}
