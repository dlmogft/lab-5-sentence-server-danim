package server;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

public class SentenceControllerTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private SentenceController sentenceController;

    public SentenceControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    // Helper method to mock RestTemplate responses
    private void mockRestTemplateResponse(String service, String response) {
        when(restTemplate.getForObject("http://" + service, Word.class)).thenReturn(new Word(response));
    }

    @Test
    public void getSentence_ShouldReturnFormattedSentences() {
        // Arrange
        mockRestTemplateResponse("SUBJECT", "The cat");
        mockRestTemplateResponse("VERB", "jumps");
        mockRestTemplateResponse("ARTICLE", "over");
        mockRestTemplateResponse("ADJECTIVE", "lazy");
        mockRestTemplateResponse("NOUN", "dog");

        // Act
        String result = sentenceController.getSentence();

        // Assert
        assertNotNull(result, "The result should not be null.");
        assertTrue(result.contains("<h3>Some Sentences</h3>"), "The result should contain the header.");
        assertTrue(result.contains("The cat jumps over lazy dog."), "The result should contain the assembled sentence.");
    }

    @Test
    public void buildSentence_ShouldReturnValidSentence() {
        // Arrange
        mockRestTemplateResponse("SUBJECT", "The cat");
        mockRestTemplateResponse("VERB", "jumps");
        mockRestTemplateResponse("ARTICLE", "over");
        mockRestTemplateResponse("ADJECTIVE", "lazy");
        mockRestTemplateResponse("NOUN", "dog");

        // Act
        String result = sentenceController.buildSentence();

        // Assert
        assertEquals("The cat jumps over lazy dog.", result, "The sentence should be correctly assembled.");
    }

    @Test
    public void buildSentence_ShouldHandleExceptionGracefully() {
        // Arrange
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenThrow(new RuntimeException("Service unavailable"));

        // Act
        String result = sentenceController.buildSentence();

        // Assert
        assertEquals("(Error retrieving SUBJECT) (Error retrieving VERB) (Error retrieving ARTICLE) (Error retrieving ADJECTIVE) (Error retrieving NOUN).",
                result, "The sentence should indicate an error.");
    }

    @Test
    public void getWord_ShouldReturnWordFromService() {
        // Arrange
        mockRestTemplateResponse("SUBJECT", "The cat");

        // Act
        String result = sentenceController.getWord("SUBJECT");

        // Assert
        assertEquals("The cat", result, "The word should be retrieved from the service.");
    }

    @Test
    public void getWord_ShouldHandleExceptionGracefully() {
        // Arrange
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenThrow(new RuntimeException("Service unavailable"));

        // Act
        String result = sentenceController.getWord("SUBJECT");

        // Assert
        assertEquals("(Error retrieving SUBJECT)", result, "The method should handle the exception gracefully.");
    }
}
