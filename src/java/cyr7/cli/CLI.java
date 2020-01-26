package cyr7.cli;

import org.apache.commons.cli.Options;

import java.util.Arrays;

public class CLI {

    public static void main(String[] args) {
        Options options = new Options();

        System.out.println(Arrays.toString(args));
    }

}
