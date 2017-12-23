package uk.co.carelesslabs.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

/**
 * Class to encode/compress and decode/un-compress JSON strings.
 * To be used for saving game states and other player preferences.
 * The compression will lower disk space required for saving and the encoding
 * will help stop the average player being able to edit the save data.
 */
public class Zipper { 
    /**
     * Compresses a string using GZIP and encodes it as Base64 
     *
     * @param input string to be compressed and encoded i.e JSON
     * @return      compressed and encoded string 
     * @throws IOException if an I/O error occurs
     */
    public static String compressString(String input)throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(outputStream);
        gzip.write(input.getBytes());
        gzip.close();             
        byte[] bytes = outputStream.toByteArray();
        byte[] encodedBytes = Base64.encodeBase64(bytes);
        
        return new String(encodedBytes, 0, encodedBytes.length, "UTF-8");
    }

    /**
     * Decodes a Base64 string and then un-compresses it 
     *
     * @param zippedBase64Str encoded and compressed string
     * @return      un-compressed string i.e JSON 
     * @throws IOException if an I/O error occurs
     */
    public static String uncompressString(String zippedBase64Str) throws IOException {
        byte[] bytes = Base64.decodeBase64(zippedBase64Str);
        GZIPInputStream inputStream = new GZIPInputStream(new ByteArrayInputStream(bytes));
        String result = IOUtils.toString(inputStream, Charset.defaultCharset());
        inputStream.close();
        return result;
     }
}
