package cyr7.integration;

import cyr7.integration.Run.RunConfiguration;

public class TestWeirdInductionVariables extends TestProgram {
    @Override
    protected String filename() {
        return "weirdInductionVariables";
    }

    @Override
    protected String expected() {
        return "TEST 1:\n" +
            "(0, 1)\n" +
            "(1, 1)\n" +
            "(2, 2)\n" +
            "(2, 3)\n" +
            "(3, 3)\n" +
            "(4, 4)\n" +
            "(4, 5)\n" +
            "(5, 5)\n" +
            "(6, 6)\n" +
            "(6, 7)\n" +
            "(7, 7)\n" +
            "(8, 8)\n" +
            "(8, 9)\n" +
            "(9, 9)\n" +
            "(10, 10)\n" +
            "TEST 2:\n" +
            "2\n" +
            "4\n" +
            "8\n" +
            "16\n" +
            "32\n" +
            "TEST 3:\n" +
            "0\n" +
            "1\n" +
            "2\n" +
            "3\n" +
            "5\n" +
            "7\n" +
            "11\n" +
            "13\n" +
            "17\n" +
            "19\n" +
            "23\n" +
            "29\n" +
            "31\n" +
            "37\n" +
            "41\n" +
            "43\n" +
            "47\n" +
            "53\n" +
            "59\n" +
            "61\n" +
            "67\n" +
            "71\n" +
            "73\n" +
            "79\n" +
            "83\n" +
            "89\n" +
            "97\n" +
            "101\n" +
            "103\n" +
            "107\n" +
            "109\n" +
            "113\n" +
            "127\n" +
            "131\n" +
            "137\n" +
            "139\n" +
            "149\n" +
            "151\n" +
            "157\n" +
            "163\n" +
            "167\n" +
            "173\n" +
            "179\n" +
            "181\n" +
            "191\n" +
            "193\n" +
            "197\n" +
            "199\n";
    }

    @Override
    protected RunConfiguration configuration() {
        return new RunConfiguration().bigHeap(true);
    }
}
