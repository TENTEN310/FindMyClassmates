package com.hfad.classmates;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

// testing getFileType from ClassDetail
public class FileTypeUnitTest {

    // mocking the function here as original requires AppCompatActivity
    private String getFileType(String contentType) {
        if (contentType != null) {
            String[] parts = contentType.split("/");
            if (parts.length > 1) {
                return parts[1].toUpperCase();
            }
        }

        // if there is no associated file type
        return "Unknown";
    }

    @Test
    public void testValidContentTypeWithLowercase() { assertEquals("JPEG", getFileType("image/jpeg")); }

    @Test
    public void testValidContentTypeWithUppercase() { assertEquals("JPEG", getFileType("image/JPEG")); }

    @Test
    public void testValidContentTypeWithMultipleSlashes() { assertEquals("MPEG", getFileType("audio/mpeg/mp3")); }

    @Test
    public void testInvalidContentType() { assertEquals("Unknown", getFileType("invalid_content_type")); }

    @Test
    public void testNullContentType() {
        assertEquals("Unknown", getFileType(null));
    }

    @Test
    public void testEmptyContentType() {
        assertEquals("Unknown", getFileType(""));
    }
}
