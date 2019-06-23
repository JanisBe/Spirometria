package janis;

import com.google.common.collect.ArrayListMultimap;
import janis.Model.Dane;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import static java.lang.Integer.parseInt;
import static java.nio.file.Files.readAllBytes;

public class Controller {
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public void click(ActionEvent event) throws IOException {
        byte[] fileBytes = readAllBytes(new File(getClass().getClassLoader().getResource("tet.bin").getFile()).toPath());
        String[] chunks = bytesToHex(fileBytes).split("0001010101");
        for (int i = 1; i < chunks.length; i++) {
            String chunk = chunks[i];
            int interval = parseInt(chunk.substring(0,2));
            ArrayListMultimap<LocalDateTime, Dane> results = ArrayListMultimap.create();
            LocalDateTime data = LocalDateTime.of(
                    toDec(chunk.substring(3,4)),
                    toDec(chunk.substring(5,6)),
                    toDec(chunk.substring(7,8)),
                    toDec(chunk.substring(9,10)),
                    toDec(chunk.substring(11,12)),
                    toDec(chunk.substring(13,14)));
            data = data.minusSeconds(interval);
            String[] dataSet = chunk.substring(14).split("0005");
            for (int j = 0; j < dataSet.length; j++) {
                results.put(data.plusSeconds(interval),new Dane(toDec(dataSet[i].substring(0,2)),toDec(dataSet[i].substring(2,4))));
            }
        }

    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static int toDec(String string){
        return parseInt(string,16);
    }
}
