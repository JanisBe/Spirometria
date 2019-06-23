package janis.Model;

import lombok.Data;

@Data
public class Dane {
    private int PR;
    private int saturacja;

    public Dane(int PR, int saturacja) {
    }
}
