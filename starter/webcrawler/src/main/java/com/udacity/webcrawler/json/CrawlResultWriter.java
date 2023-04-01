package com.udacity.webcrawler.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;


/**
 * Utility class to write a {@link CrawlResult} to file.
 */
public final class CrawlResultWriter {
  private final CrawlResult result;

  /**
   * Creates a new {@link CrawlResultWriter} that will write the given {@link CrawlResult}.
   */
  public CrawlResultWriter(CrawlResult result) {
    this.result = Objects.requireNonNull(result);
  }
  public static String jsonFormatting(String string) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    String resultString =" ";
    try{
    JsonNode jsonNode = objectMapper.readTree(string);
    resultString =  objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
    }catch (Exception e){
    e.printStackTrace();
    }
    return resultString;

  }

  public void write(Path path) throws IOException {
    // This is here to get rid of the unused variable warning.
    Objects.requireNonNull(path);
    StringWriter stringWriter = new StringWriter();
    write(stringWriter);

    String jsonResult = stringWriter.toString().trim();
    try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE)) {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.writerWithDefaultPrettyPrinter().writeValue(writer, objectMapper.readTree(jsonResult));
    } catch (IOException e) {
      e.printStackTrace();
    }


    }


  /**
   * Formats the {@link CrawlResult} as JSON and writes it to the given {@link Writer}.
   *
   * @param writer the destination where the crawl result data should be written.

  */

  public void write(Writer writer) throws IOException {
    // This is here to get rid of the unused variable warning.
    Objects.requireNonNull(writer);
    // TODO: Fill in this method.

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.disable(JsonGenerator.Feature.AUTO_CLOSE_TARGET);
    try{
      writer.write(System.lineSeparator());
      objectMapper.writeValue(writer,result);
      writer.write(System.lineSeparator());
    }catch ( IOException e) {
      e.printStackTrace();
    }

  }


}
