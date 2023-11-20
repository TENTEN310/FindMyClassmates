package com.hfad.classmates;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

// testing getFileType from ClassDetail
public class FileTypeTest {

    // mocking the function here as original requires AppCompatActivity
    private String getFileType(String contentType) {
        if (contentType != null) {
            String[] parts = contentType.split("/");
            if (parts.length > 1) {
                return parts[parts.length-1].toUpperCase();
            }
        }

        // if there is no associated file type
        return "Unknown";
    }

    @Test
    public void testValidCasedContentType() {
        assertEquals("JPEG", getFileType("image/jpeg"));
        assertEquals("JPEG", getFileType("image/JPEG"));
        assertEquals("JPEG", getFileType("image/JpEg"));
    }

    @Test
    public void testSingleSlashContentType() {
        assertEquals("Unknown", getFileType("/"));
    }

    @Test
    public void testValidContentTypeWithMultipleSlashes() {
        assertEquals("MP3", getFileType("audio/mpeg/mp3"));
    }

    @Test
    public void testInvalidContentType() {
        assertEquals("Unknown", getFileType("invalid_content_type"));
    }

    @Test
    public void testNullOrEmptyContentType() {
        assertEquals("Unknown", getFileType(null));
        assertEquals("Unknown", getFileType(""));
    }
}
